package com.isms.kumar.Core_Modules;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.desai.vatsal.mydynamictoast.MyDynamicToast;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.isms.kumar.Json_Webservices.RestClientHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Muthukumar & Vidhya
 */

public class StudentDetails_Activity extends Fragment {

    // Variable creation

    TextView male, female, total_Stu;
    TextView graph, grid;

    BarChart student_chart;
    WebView student_webvw;



    //*********************************************************************************************

    /**
     * Initialization
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_student_details, container, false);

        try {

            Task_Navigation.Title_TextVw.setText(R.string.student_details);

            GetInitialize(v);

        } catch (Exception e) {
            e.printStackTrace();

        }

        return v;
    }


    //**********************************************************************************************

    String SESSION_DATABASE;

    private void GetInitialize(View v) {

        male = (TextView) v.findViewById(R.id.male_count);
        female = (TextView) v.findViewById(R.id.female_count);
        total_Stu = (TextView) v.findViewById(R.id.stu_total);

        graph = (TextView) v.findViewById(R.id.txt_graph);
        grid = (TextView) v.findViewById(R.id.txt_grid);


        student_chart = (BarChart) v.findViewById(R.id.stu_chart);
        student_chart.setDescription("");
        student_webvw = (WebView) v.findViewById(R.id.std_webview);
        student_webvw.getSettings().setJavaScriptEnabled(true);
        student_webvw.setWebChromeClient(new WebChromeClient());


        Bundle b = getArguments();
        SESSION_DATABASE = b.getString("SESSION_DB_NAME");


        if (Baseconfig.CheckNW(getActivity())) {

            LoadAllView();

        } else {
            Baseconfig.SweetDialgos(3, getActivity(), getString(R.string.str_information), getString(R.string.no_connection), getString(R.string.str_ok));
        }

        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_dark, android.R.color.holo_red_dark, android.R.color.holo_blue_bright);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Your refresh code here

                Toast.makeText(getActivity(), "refreshing...", Toast.LENGTH_SHORT).show();

                if (Baseconfig.CheckNW(getActivity())) {

                    LoadAllView();

                    swipeRefreshLayout.setRefreshing(false);

                } else {
                    Baseconfig.SweetDialgos(3, getActivity(), getString(R.string.str_information), getString(R.string.no_connection), getString(R.string.str_ok));
                }

            }
        });

    }


    //**********************************************************************************************

    /**
     * Load all values to the UI
     * Get data from JSON through API
     * Muthukumar & Vidhya
     * 07/05/2017
     */

    String webview_values;

    public void LoadAllView() {

        //Original URL
        String URL = "http://api.ispidersolutions.com/schoolapi/api/App/GetStudentDetails?DatabaseName=" + SESSION_DATABASE + "";


        //Static Testing URL
        URL = "http://api.ispidersolutions.com/schoolapi/api/App/GetStudentDetails?DatabaseName=iSMSReddiar";

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
                        JSONArray jsonArray = jsonObject.getJSONArray("Table");//Students Count
                        JSONArray jsonArray1 = jsonObject.getJSONArray("Table1");//Standard Info
                        JSONArray jsonArray2 = jsonObject.getJSONArray("Table2");// Religion Wise
                        JSONArray jsonArray3 = jsonObject.getJSONArray("Table3");//Caste Wise


                        //**************************************************************************
                        /**
                         * To get male and female students count
                         * @Muthukumar
                         */

                        String MALE_STUDENTS_COUNT = "0", FEMALE_STUDENTS_COUNT = "0";

                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            jsonObject = jsonArray.getJSONObject(i);
                            String Category = jsonObject.getString("Catgory");
                            String Nos = jsonObject.getString("Nos");
                            Log.e("Category + Nos: ", Category + " / " + Nos);

                            if (Category.toString().equals("Male")) {
                                MALE_STUDENTS_COUNT = Nos;
                            } else if(Category.toString().equals("Female")){
                                FEMALE_STUDENTS_COUNT = Nos;
                            }

                           // Toast.makeText(getActivity(), MALE_STUDENTS_COUNT+" - "+Category+" - "+ Nos, Toast.LENGTH_SHORT).show();
                           // Toast.makeText(getActivity(), FEMALE_STUDENTS_COUNT+" - "+Category+" - "+ Nos, Toast.LENGTH_SHORT).show();

                        }



                       // LoadAnimatedTextView(Integer.parseInt(MALE_STUDENTS_COUNT), male);
                       // LoadAnimatedTextView(Integer.parseInt(FEMALE_STUDENTS_COUNT), female);

                        male.setText(MALE_STUDENTS_COUNT);
                        female.setText(FEMALE_STUDENTS_COUNT);

                        int TotalStrength = Integer.parseInt(MALE_STUDENTS_COUNT) + Integer.parseInt(FEMALE_STUDENTS_COUNT);
                        //LoadAnimatedTextView(TotalStrength, total_Stu); // Uncomment this line animate textview - for total
                        total_Stu.setText(String.valueOf(TotalStrength));


                        //**************************************************************************

                        StringBuilder standardwise=new StringBuilder();
                        StringBuilder religionwise=new StringBuilder();
                        StringBuilder communitywise=new StringBuilder();

                        int Total_Male=0;
                        int Total_Female=0;
                        int Total_AllTotal=0;

                        /**
                         * Standard Info
                         */

                        for (int i = 0; i < jsonArray1.length(); i++) {
                            jsonObject = jsonArray1.getJSONObject(i);
                            String StandardId = jsonObject.getString("StandarID");
                            String Department = jsonObject.getString("Department");
                            String Male = jsonObject.getString("Male");
                            String Female = jsonObject.getString("Female");
                            String Total = jsonObject.getString("Total");

                            v1 = new BarEntry(Integer.parseInt(Male),i); // Jan
                            valueSet1.add(v1);

                            v2 = new BarEntry(Integer.parseInt(Female), i); // Jan
                            valueSet2.add(v2);

                            v3 = new BarEntry(Integer.parseInt(Total), i);
                            valueSet3.add(v3);

                            xAxis1.add(Department);

                            standardwise.append( "  <tr>\n" +
                                    "    <td align=\"center\">"+Department+"</td>\n" +
                                    "    <td align=\"center\">"+Male+"</td>\n" +
                                    "\t<td align=\"center\">"+checkNullEmpty(Female).toString()+"</td>\n" +
                                    "\t<td align=\"center\">"+checkNullEmpty(Total)+"</td>\n" +
                                    "  </tr>\n");

                            Total_Male+=Integer.parseInt(Male);
                            Total_Female+=Integer.parseInt(Female);
                            Total_AllTotal+=Integer.parseInt(Total);



                        }

                        standardwise.append("  <tr>\n" +
                                "    <td align=\"center\" bgcolor=\"#D33EE5\" > <font color=\"#fff\">Total</td>\n" +
                                "    <td align=\"center\" bgcolor=\"#D33EE5\"><font color=\"#fff\">"+Total_Male+"</td>\n" +
                                "\t<td align=\"center\" bgcolor=\"#D33EE5\"><font color=\"#fff\">"+Total_Female+"</td>\n" +
                                "\t<td align=\"center\" bgcolor=\"#D33EE5\"><font color=\"#fff\"><font color=\"#fff\">"+Total_AllTotal+"</td>\n" +
                                "  </tr>\n");




                        /**
                         * Loading Bar Chart as Default
                         */
                        BarData data = new BarData(getXAxisValues(xAxis1), getDataSet(valueSet1,valueSet2,valueSet3));
                        if (data != null)
                        {
                            student_chart.setData(data);
                            student_chart.animateXY(2000, 2000);
                            student_chart.invalidate();
                            student_chart.setScaleMinima(2f, 1f);


                        }



                        //**************************************************************************
                        Total_Male=0;
                        Total_Female=0;
                        Total_AllTotal=0;

                        /**
                         * Religion Info
                         */
                        for (int i = 0; i < jsonArray2.length(); i++) {
                            jsonObject = jsonArray2.getJSONObject(i);
                            String ReligionName = jsonObject.getString("ReligionName");
                            String Male = jsonObject.getString("Male");
                            String Female = jsonObject.getString("Female");
                            String Total = jsonObject.getString("Total");

                            religionwise.append( "  <tr>\n" +
                                    "    <td align=\"center\">"+ReligionName+"</td>\n" +
                                    "    <td align=\"center\">"+Male+"</td>\n" +
                                    "\t<td align=\"center\">"+checkNullEmpty(Female).toString()+"</td>\n" +
                                    "\t<td align=\"center\">"+checkNullEmpty(Total)+"</td>\n" +
                                    "  </tr>\n");

                            Total_Male+=Integer.parseInt(Male);
                            Total_Female+=Integer.parseInt(Female);
                            Total_AllTotal+=Integer.parseInt(Total);


                        }


                        religionwise.append("  <tr>\n" +
                                "    <td align=\"center\" bgcolor=\"#D33EE5\" > <font color=\"#fff\">Total</td>\n" +
                                "    <td align=\"center\" bgcolor=\"#D33EE5\"><font color=\"#fff\">"+Total_Male+"</td>\n" +
                                "\t<td align=\"center\" bgcolor=\"#D33EE5\"><font color=\"#fff\">"+Total_Female+"</td>\n" +
                                "\t<td align=\"center\" bgcolor=\"#D33EE5\"><font color=\"#fff\"><font color=\"#fff\">"+Total_AllTotal+"</td>\n" +
                                "  </tr>\n");






                        //**************************************************************************
                        /**
                         * Caste Info
                         */
                        Total_Male=0;
                        Total_Female=0;
                        Total_AllTotal=0;

                        for (int i = 0; i < jsonArray3.length(); i++) {
                            jsonObject = jsonArray3.getJSONObject(i);
                            String Community = jsonObject.getString("Community");
                            String Male = jsonObject.getString("Male");
                            String Female = jsonObject.getString("Female");
                            String Total = jsonObject.getString("Total");

                            communitywise.append( "  <tr>\n" +
                                    "    <td align=\"center\">"+Community+"</td>\n" +
                                    "    <td align=\"center\">"+Male+"</td>\n" +
                                    "\t<td align=\"center\">"+checkNullEmpty(Female).toString()+"</td>\n" +
                                    "\t<td align=\"center\">"+checkNullEmpty(Total)+"</td>\n" +
                                    "  </tr>\n");

                            Total_Male+=Integer.parseInt(Male);
                            Total_Female+=Integer.parseInt(Female);
                            Total_AllTotal+=Integer.parseInt(Total);


                        }

                        communitywise.append("  <tr>\n" +
                                "    <td align=\"center\" bgcolor=\"#D33EE5\" > <font color=\"#fff\">Total</td>\n" +
                                "    <td align=\"center\" bgcolor=\"#D33EE5\"><font color=\"#fff\">"+Total_Male+"</td>\n" +
                                "\t<td align=\"center\" bgcolor=\"#D33EE5\"><font color=\"#fff\">"+Total_Female+"</td>\n" +
                                "\t<td align=\"center\" bgcolor=\"#D33EE5\"><font color=\"#fff\"><font color=\"#fff\">"+Total_AllTotal+"</td>\n" +
                                "  </tr>\n");







                        //**************************************************************************
                        //Loading Webview



                        webview_values = "<!DOCTYPE html>\n" +
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
                                "<font class=\"sub\"><i class=\"fa fa-address-card-o fa-2x\" aria-hidden=\"true\"></i> Standard Wise</font>\n" +
                                "\n" +
                                "<div class=\"table-responsive\">          \n" +
                                "<table class=\"table table-bordered\">\n" +
                                "  <tr>\n" +
                                "    <th bgcolor=\"#ffa700\"><font color=\"#fff\">Standard</font></th>\n" +
                                "    <th bgcolor=\"#ffa700\"><font color=\"#fff\">Male</font></th>\n" +
                                "\t <th bgcolor=\"#ffa700\"><font color=\"#fff\">Female</font></th>\n" +
                                "\t<th bgcolor=\"#ffa700\"><font color=\"#fff\">Total</font></th>\n" +
                                "\n" +
                                "  </tr>\n" +

                                standardwise+

                                "\n" +
                                "</table>\n" +
                                "\n" +
                                "\n" +
                                "</div>\n" +
                                "\n" +
                                "<font class=\"sub\"><i class=\"fa fa-address-card-o fa-2x\" aria-hidden=\"true\"></i> Religion Wise</font>\n" +
                                "\n" +
                                "<div class=\"table-responsive\">          \n" +
                                "<table class=\"table table-bordered\">\n" +
                                "  <tr>\n" +
                                "    <th bgcolor=\"#ffa700\"><font color=\"#fff\">Religion</font></th>\n" +
                                "    <th bgcolor=\"#ffa700\"><font color=\"#fff\">Male</font></th>\n" +
                                "\t <th bgcolor=\"#ffa700\"><font color=\"#fff\">Female</font></th>\n" +
                                "\t<th bgcolor=\"#ffa700\"><font color=\"#fff\">Total</font></th>\n" +
                                "\n" +
                                "  </tr>\n" +


                                religionwise+


                                "\n" +
                                "</table>\n" +
                                "</div>\n" +
                                "<font class=\"sub\"><i class=\"fa fa-address-card-o fa-2x\" aria-hidden=\"true\"></i> Community Wise</font>\n" +
                                "\n" +
                                "<div class=\"table-responsive\">          \n" +
                                "<table class=\"table table-bordered\">\n" +
                                "  <tr>\n" +
                                "    <th bgcolor=\"#ffa700\"><font color=\"#fff\">Community</font></th>\n" +
                                "\t <th bgcolor=\"#ffa700\"><font color=\"#fff\">Male</font></th>\n" +
                                "\t<th bgcolor=\"#ffa700\"><font color=\"#fff\">Female</font></th>\n" +
                                "\t<th bgcolor=\"#ffa700\"><font color=\"#fff\">Total</font></th>\n" +
                                "\n" +
                                "  </tr>\n" +

                                communitywise+

                                "\n" +
                                "</table>\n" +
                                "</div>\n" +
                                "<!----------------------------------------------------------------->\n" +
                                "\n" +
                                "<br/>\n" +
                                "<!----------------------------------------------------------------->\n" +
                                "\n" +
                                "<!----------------------------------------------------------------->\n" +
                                "</body>\n" +
                                "</html>                                      ";


                        //LoadWebview(webview_values);

                        grid.performClick();



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

        MyDynamicToast.informationMessage(getActivity(), "Please wait Student Information loading..");


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

           // MyDynamicToast.successMessage(getActivity(), "Student Information Loaded Successfully...");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
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
                        Thread.sleep(10);
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


    //**********************************************************************************************

    private void Controlistener() {

        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                graph.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_bg_selected1));
                graph.setTextColor(getResources().getColor(R.color.white));

                grid.setBackgroundColor(getResources().getColor(R.color.white));
                grid.setTextColor(getResources().getColor(R.color.black));



                student_webvw.setVisibility(View.GONE);
                student_chart.setVisibility(View.VISIBLE);


                BarData data = new BarData(getXAxisValues(xAxis1), getDataSet(valueSet1,valueSet2,valueSet3));


                if (data != null)
                {
                    student_chart.setData(data);
                    student_chart.animateXY(2000, 2000);
                    student_chart.invalidate();
                    student_chart.setScaleMinima(2f, 1f);


                }


            }
        });


        //******************************************************************************************


        grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                grid.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_bg_selected1));
                grid.setTextColor(getResources().getColor(R.color.white));


                graph.setBackgroundColor(getResources().getColor(R.color.white));
                graph.setTextColor(getResources().getColor(R.color.black));


                student_webvw.setVisibility(View.VISIBLE);
                student_chart.setVisibility(View.GONE);

                LoadWebview(webview_values);


            }
        });

        //******************************************************************************************



    }


    //******************************************************************************************
    /**
     * Loading student bar chart
     */

    BarEntry v1, v2, v3;
    ArrayList<BarEntry> valueSet1 = new ArrayList<>();
    ArrayList<BarEntry> valueSet2 = new ArrayList<>();
    ArrayList<BarEntry> valueSet3 = new ArrayList<>();


    public ArrayList<BarDataSet> getDataSet(ArrayList<BarEntry> v1, ArrayList<BarEntry> v2, ArrayList<BarEntry> v3) {

        ArrayList<BarDataSet> dataSets = new ArrayList<>();

        valueSet1 = v1;
        valueSet2 = v2;
        valueSet3 = v3;


        BarDataSet barDataSet1 = null, barDataSet2 = null, barDataSet3 = null;

        dataSets = new ArrayList<>();

        barDataSet1 = new BarDataSet(valueSet1, "Male");
        barDataSet1.setColor(Color.rgb(136,37,180));

        barDataSet2 = new BarDataSet(valueSet2, "Female");
        barDataSet2.setColor(Color.rgb(255,167,0));

        barDataSet3 = new BarDataSet(valueSet3, "Total");
        barDataSet3.setColor(Color.rgb(6,171,0));


        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        dataSets.add(barDataSet3);


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
