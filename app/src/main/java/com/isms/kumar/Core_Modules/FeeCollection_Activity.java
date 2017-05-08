package com.isms.kumar.Core_Modules;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.isms.kumar.Adapters.FeeCollectionAdapter;
import com.isms.kumar.Adapters.Getter_Setter;
import com.isms.kumar.Json_Webservices.RestClientHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Vidhya-Android on 5/6/2017.
 */

public class FeeCollection_Activity extends Fragment
{

    // Variable creation

    TextView tot_collection,bill_count,cancel,cons_count,cons_amt;
    TextView graph,grid;

    BarChart student_chart;
    //WebView student_webvw;

    RecyclerView recyclerView;

    //*********************************************************************************************

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.activity_fee_collection, container, false);

        try
        {

            Task_Navigation.Title_TextVw.setText(R.string.fees_collection);


            GetInitialize(v);



        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

        return v;
    }


    //**********************************************************************************************

    String SESSION_DATABASE;

    private void GetInitialize(View v)
    {
        //Initialize

        tot_collection = (TextView)v.findViewById(R.id.txt_collection);
        bill_count = (TextView)v.findViewById(R.id.txt_bill);
        cancel = (TextView)v.findViewById(R.id.txt_cancel);
        cons_count = (TextView)v.findViewById(R.id.txt_cons_count);
        cons_amt = (TextView)v.findViewById(R.id.txt_cons_amt);

        graph = (TextView)v.findViewById(R.id.txt_graph);
        grid = (TextView)v.findViewById(R.id.txt_grid);

        student_chart = (BarChart) v.findViewById(R.id.stu_chart);
        student_chart.setDescription("");
       /* student_webvw = (WebView) v.findViewById(R.id.std_webview);
        student_webvw.getSettings().setJavaScriptEnabled(true);
        student_webvw.setWebChromeClient(new WebChromeClient());
*/
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(layoutManager);


        Bundle b = getArguments();
        SESSION_DATABASE = b.getString("SESSION_DB_NAME");



        if (Baseconfig.CheckNW(getActivity())) {

            LoadAllView();

        } else {
            Baseconfig.SweetDialgos(3, getActivity(), getString(R.string.str_information), getString(R.string.no_connection), getString(R.string.str_ok));
        }



    }




    //**********************************************************************************************

    /**
     * Load all values to the UI
     * Get data from JSON through API
     * Muthukumar & Vidhya
     * 07/05/2017
     */

    String webview_values;
    ArrayList<Getter_Setter.FeeCollection> Dataitems1;

    public void LoadAllView() {


        String FROMDATE="",TODATE="";

        //Original URL
        String URL = "http://api.ispidersolutions.com/schoolapi/api/App/GetBillingDetails?DatabaseName="+SESSION_DATABASE+"&FromDate="+FROMDATE+"&ToDate="+TODATE+"";


        //Static Testing URL
        URL = "http://api.ispidersolutions.com/schoolapi/api/App/GetBillingDetails?DatabaseName=iSMSReddiar&FromDate=01/01/2016&ToDate=01/01/2018";

        Log.e("PASSING URL: ", URL);

        //Get Request
        RestClientHelper.getInstance(getActivity()).get(URL, new RestClientHelper.RestClientListener() {


            @Override
            public void onSuccess(String response) {

                try {
                    String Invalid = "{\"ds\":{\"Table\":[{\"Status\":\"Invalid\"}]}}";

                    if (!response.substring(1, response.length() - 1).replace("\\", "").toString().equalsIgnoreCase(Invalid)) {

                        String FinalResponse_Str = response.substring(1, response.length() - 1).replace("\\", "").toString();

                        JSONObject jsonObject = new JSONObject(FinalResponse_Str);
                        jsonObject = jsonObject.getJSONObject("ds");
                        JSONArray jsonArray = jsonObject.getJSONArray("Table");//Fee Count
                        JSONArray jsonArray1 = jsonObject.getJSONArray("Table1");//Fee Category


                        //**************************************************************************
                        /**
                         * To get male and female students count
                         * @Muthukumar
                         */
                        String Amount = "-",BillCount = "-",CancelledCount = "-";
                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            jsonObject = jsonArray.getJSONObject(i);
                            Amount = jsonObject.getString("Amount");
                            BillCount = jsonObject.getString("BillCount");
                            CancelledCount = jsonObject.getString("CancelCount");


                        }

                        tot_collection.setText("₹  "+ConvertToCurrency(Integer.parseInt(Amount)));
                        bill_count.setText(BillCount);
                        cancel.setText(CancelledCount);

                        //LoadAnimatedTextView(Integer.parseInt(Amount), tot_collection);
                        //LoadAnimatedTextView(Integer.parseInt(BillCount), bill_count);
                        //LoadAnimatedTextView(Integer.parseInt(CancelledCount), cancel);


                        //**************************************************************************

                        StringBuilder categorywise=new StringBuilder();

                        /**
                         * Fee category Info
                         */
                        int Total_amount=0;
                         Dataitems1 = new ArrayList<>();
                        for (int i = 0; i < jsonArray1.length(); i++)
                        {
                            jsonObject = jsonArray1.getJSONObject(i);
                            String Fee_Amount = jsonObject.getString("Amount");
                            String Fee_Category = jsonObject.getString("FeesCategory");

                            v1 = new BarEntry(Integer.parseInt(Fee_Amount),i); // Jan
                            valueSet1.add(v1);

                            Total_amount+=Integer.parseInt(Fee_Amount);

                            xAxis1.add(Fee_Category);

                           /* categorywise.append( "  <tr>\n" +
                                    "\t<td>"+checkNullEmpty(Fee_Category).toString()+"</td>\n" +
                                    "\t<td>"+checkNullEmpty(ConvertToCurrency(Integer.parseInt(Fee_Amount)))+"</td>\n" +
                                    "\n");*/

                            Getter_Setter.FeeCollection obj = new Getter_Setter.FeeCollection();
                            obj.setFeeAmount(Fee_Amount);
                            obj.setFeeCategory(Fee_Category);
                            Dataitems1.add(obj);


                        }



                        /**
                         * Loading Bar Chart as Default
                         */
                        BarData data = new BarData(getXAxisValues(xAxis1), getDataSet(valueSet1));
                        if (data != null)
                        {
                            student_chart.setData(data);
                            student_chart.animateXY(2000, 2000);
                            student_chart.invalidate();
                        }



                        //**************************************************************************
                        //Loading Webview



                        /*webview_values = "<!DOCTYPE html>\n" +
                                "\n" +
                                "<html lang=\"en\">\n" +
                                "<head>\n" +
                                "\n" +
                                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"/>\n" +
                                "<link rel=\"stylesheet\"  type=\"text/css\" href=\"file:///android_asset/Student_Profile/css/english.css\"/>\n" +
                                "\n" +
                                "<link rel=\"stylesheet\" href=\"file:///android_asset/Student_Profile/css/bootstrap.min.css\" />\n" +
                                "<link rel=\"stylesheet\" href=\"file:///android_asset/Student_Profile/css/bootstrap-theme.min.css\" />\n" +
                                "\n" +
                                "<link rel=\"stylesheet\" href=\"file:///android_asset/Student_Profile/css/font-awesome.min.css\" type=\"text/css\" />\n" +
                                "\n" +
                                "<script src=\"file:///android_asset/Student_Profile/css/jquery.min.js\"></script>\n" +
                                "<script src=\"file:///android_asset/Student_Profile/css/bootstrap.min.js\" ></script>\n" +
                                "\n" +
                                "</head>\n" +
                                "<body>  \n" +
                                " \n" +
                                "\n" +
                                "\n" +
                                "\n" +
                                "\n" +
                                "<font class=\"sub\"><i class=\"fa fa-address-card-o fa-2x\" aria-hidden=\"true\"></i> Fee category</font>\n" +
                                "\n" +
                                "<div class=\"table-responsive\">          \n" +
                                "<table class=\"table table-bordered\">\n" +
                                "  <tr>\n" +
                                "    <th bgcolor=\"#16A085\"><font color=\"#fff\">Fees Category</font></th>\n" +
                                "    <th bgcolor=\"#16A085\"><font color=\"#fff\">Amount</font></th>\n" +
                                "\n" +
                                "  </tr>\n" +

                                categorywise+
                                "\t</tr><tr>" +
                                "<td bgcolor=\"#16A085\"><font color=\"#fff\">Total Amount</td>\n" +
                                "<td bgcolor=\"#16A085\"><font color=\"#fff\">"+checkNullEmpty(ConvertToCurrency(Total_amount))+"</td>\n" +
                                "  </tr>" +
                                "\n" +
                                "</table>\n" +
                                "\n" +
                                "\n" +
                                "</div>\n" +
                                "\n" +
                                "<!----------------------------------------------------------------->\n" +
                                "\n" +
                                "<br/>\n" +
                                "<!----------------------------------------------------------------->\n" +
                                "\n" +
                                "<!----------------------------------------------------------------->\n" +
                                "</body>\n" +
                                "</html>                                      ";
*/

                        //LoadWebview(webview_values);


                    } else {
                        Baseconfig.SweetDialgos(3, getActivity(), "Information", "No data found!", "Ok");

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(String error) {
                // TODO: with error
                Log.e("Error: ", error);

                Baseconfig.SweetDialgos(3, getActivity(), "Information", "Failed to get data..try again", "Ok");
            }
        });



        Controlistener();


    }

    //
    public void LoadGrid()
    {
        ArrayList<Getter_Setter.FeeCollection> DataItems = Dataitems1;
        FeeCollectionAdapter adapter = new FeeCollectionAdapter(getActivity(), DataItems,SESSION_DATABASE);
        recyclerView.setAdapter(adapter);

    }

    //**********************************************************************************************

    /**
     * Convert number to currency
     * @param Value
     * @return
     */
    public String ConvertToCurrency(int Value)
    {

        String return_str="0";

        return_str=  NumberFormat.getNumberInstance(Locale.US).format(Value);

        return return_str;
    }

    //**********************************************************************************************

    private void Controlistener() {

        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                graph.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_bg_selected));
                graph.setTextColor(getResources().getColor(R.color.white));

                grid.setBackgroundColor(getResources().getColor(R.color.white));
                grid.setTextColor(getResources().getColor(R.color.black));

                //student_webvw.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                student_chart.setVisibility(View.VISIBLE);


                BarData data = new BarData(getXAxisValues(xAxis1), getDataSet(valueSet1));


                if (data != null)
                {
                    student_chart.setData(data);
                    student_chart.animateXY(2000, 2000);
                    student_chart.invalidate();
                }


            }
        });


        //******************************************************************************************


        grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                grid.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_bg_selected));
                grid.setTextColor(getResources().getColor(R.color.white));


                graph.setBackgroundColor(getResources().getColor(R.color.white));
                graph.setTextColor(getResources().getColor(R.color.black));


                recyclerView.setVisibility(View.VISIBLE);
                student_chart.setVisibility(View.GONE);

               // LoadWebview(webview_values);
                LoadGrid();


            }
        });

        //******************************************************************************************

    }


    /*


    public void LoadWebview(String values)
    {

        student_webvw.getSettings().setJavaScriptEnabled(true);
        student_webvw.setLayerType(View.LAYER_TYPE_NONE, null);
        student_webvw.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        student_webvw.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        student_webvw.getSettings().setDefaultTextEncodingName("utf-8");

        student_webvw.setWebChromeClient(new WebChromeClient() {
        });

        student_webvw.setBackgroundColor(0x00000000);
        student_webvw.setVerticalScrollBarEnabled(true);
        student_webvw.setHorizontalScrollBarEnabled(true);

        MyDynamicToast.informationMessage(getActivity(), "Please wait Fee Collection Information loading..");


        student_webvw.getSettings().setJavaScriptEnabled(true);

        student_webvw.getSettings().setAllowContentAccess(true);


        student_webvw.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });

        student_webvw.setLongClickable(false);


        student_webvw.addJavascriptInterface(new WebAppInterface(getActivity()), "android");
        try {

            student_webvw.loadDataWithBaseURL("file:///android_asset/", values, "text/html", "utf-8", null);

            MyDynamicToast.successMessage(getActivity(), "Fee Collection Information Loaded Successfully...");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    */

    //**********************************************************************************************

    public class WebAppInterface {
        Context mContext;

        /**
         * Instantiate the interface and set the context
         */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /**
         * Show a toast from the web page
         */
        @JavascriptInterface
        public void showToast(String toast) {
            //Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();


        }
    }


    //**********************************************************************************************
    public String checkNullEmpty(String val)
    {
        if (val == null || val.isEmpty() || val.equalsIgnoreCase("null"))
        {
            return " - ";
        }
        else
        {
            return val;
        }
    }

    //**********************************************************************************************
    int counter = 0;

    public void LoadAnimatedTextView(final int total, final TextView t) {

//...
//when you want to start the counting start the thread bellow.
        new Thread(new Runnable() {

            public void run() {
                while (counter < total) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    t.post(new Runnable() {

                        public void run() {
                            t.setText(String.valueOf(counter));

                        }

                    });
                    counter++;
                }

            }

        }).start();
    }


//******************************************************************************************
    /**
     * Loading fee bar chart
     */

    BarEntry v1, v2, v3;
    ArrayList<BarEntry> valueSet1 = new ArrayList<>();


    public ArrayList<BarDataSet> getDataSet(ArrayList<BarEntry> v1) {

        ArrayList<BarDataSet> dataSets = new ArrayList<>();

        valueSet1 = v1;


        BarDataSet barDataSet1 = null;

        dataSets = new ArrayList<>();

        barDataSet1 = new BarDataSet(valueSet1, "Amount");
        barDataSet1.setColor(Color.rgb(0, 155, 0));




        dataSets.add(barDataSet1);

        return dataSets;


    }


    //**********************************************************************************************
    ArrayList<String> xAxis1 = new ArrayList<>();

    public ArrayList<String> getXAxisValues( ArrayList<String> xAxis1)
    {


        this.xAxis1=xAxis1;

        return xAxis1;
    }

    //**********************************************************************************************







//End
//**********************************************************************************************

}