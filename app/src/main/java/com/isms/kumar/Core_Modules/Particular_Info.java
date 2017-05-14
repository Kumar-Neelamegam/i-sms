package com.isms.kumar.Core_Modules;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.desai.vatsal.mydynamictoast.MyDynamicToast;
import com.isms.kumar.Json_Webservices.RestClientHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Kumar on 5/13/2017.
 */

public class Particular_Info extends AppCompatActivity {
    //**********************************************************************************************

    WebView student_webvw;


    Toolbar toolbar;
    ImageView back;


    //**********************************************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particulars);

        try {

            GetInitialize();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //**********************************************************************************************
    String SESSION_DATABASE;
    String PARTICULAR_CATEGORY;
    String API_URL;

    private void GetInitialize() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        back = (ImageView) toolbar.findViewById(R.id.imageView5);

        setSupportActionBar(toolbar);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Particular_Info.this.finish();

            }
        });

        Bundle b = getIntent().getExtras();
        SESSION_DATABASE = b.getString("SESSION_DB_NAME");
        PARTICULAR_CATEGORY = b.getString("PARTICULAR_CATEGORY");
        API_URL = b.getString("API_URL");

        student_webvw = (WebView) findViewById(R.id.std_webview);
        student_webvw.getSettings().setJavaScriptEnabled(true);
        student_webvw.setWebChromeClient(new WebChromeClient());


        if (Baseconfig.CheckNW(Particular_Info.this)) {

            LoadAllView();

        } else {
            Baseconfig.SweetDialgos(3, Particular_Info.this, getString(R.string.str_information), getString(R.string.no_connection), getString(R.string.str_ok));
        }


    }


    //**********************************************************************************************


    @Override
    public void onBackPressed() {

        Particular_Info.this.finish();

    }

    //**********************************************************************************************
    int Total_amount=0;

    public void LoadAllView() {

        //Original URL - uncomment this line
        //String URL = API_URL;

        //Static Testing URL
        String URL = "http://api.ispidersolutions.com/schoolapi/api/App/GetBillingDetailsbyParticulars?DatabaseName=iSMSReddiar&FromDate=01/01/2016&ToDate=01/01/2018&FeesCategory=" + PARTICULAR_CATEGORY + "";

        Log.e("PASSING URL CATEGORY: ", URL);


        //Get Request
        RestClientHelper.getInstance(Particular_Info.this).get(URL, new RestClientHelper.RestClientListener() {


            @Override
            public void onSuccess(String response) {


                try {
                    String Invalid = "{\"ds\":{\"Table\":[{\"Status\":\"Invalid\"}]}}";

                    //testing
                    //response = "{ \t\"ds\": { \t\t\"Table\": [{ \t\t\t\"Amount\": 10848, \t\t\t\"Particulars\": \"ADMISSION FEES\" \t\t}, { \t\t\t\"Amount\": 8760, \t\t\t\"Particulars\": \"FLAGDAY FEES\" \t\t}, { \t\t\t\"Amount\": 2685, \t\t\t\"Particulars\": \"MEDICAL FEES\" \t\t}, { \t\t\t\"Amount\": 12100, \t\t\t\"Particulars\": \"SPECIAL FEES\" \t\t}, { \t\t\t\"Amount\": 9315, \t\t\t\"Particulars\": \"TEACHER WELFARE FEES\" \t\t}, { \t\t\t\"Amount\": 344860, \t\t\t\"Particulars\": \"TUITION FEE\" \t\t}, { \t\t\t\"Amount\": 12100, \t\t\t\"Particulars\": \"SPECIAL FEES\" \t\t}, { \t\t\t\"Amount\": 9315, \t\t\t\"Particulars\": \"TEACHER WELFARE FEES\" \t\t}, { \t\t\t\"Amount\": 344860, \t\t\t\"Particulars\": \"TUITION FEE\" \t\t}, { \t\t\t\"Amount\": 12100, \t\t\t\"Particulars\": \"SPECIAL FEES\" \t\t}, { \t\t\t\"Amount\": 9315, \t\t\t\"Particulars\": \"TEACHER WELFARE FEES\" \t\t}, { \t\t\t\"Amount\": 344860, \t\t\t\"Particulars\": \"TUITION FEE\" \t\t}, { \t\t\t\"Amount\": 12100, \t\t\t\"Particulars\": \"SPECIAL FEES\" \t\t}, { \t\t\t\"Amount\": 9315, \t\t\t\"Particulars\": \"TEACHER WELFARE FEES\" \t\t}, { \t\t\t\"Amount\": 344860, \t\t\t\"Particulars\": \"TUITION FEE\" \t\t}, { \t\t\t\"Amount\": 12100, \t\t\t\"Particulars\": \"SPECIAL FEES\" \t\t}, { \t\t\t\"Amount\": 9315, \t\t\t\"Particulars\": \"TEACHER WELFARE FEES\" \t\t}, { \t\t\t\"Amount\": 344860, \t\t\t\"Particulars\": \"TUITION FEE\" \t\t}, { \t\t\t\"Amount\": 12100, \t\t\t\"Particulars\": \"SPECIAL FEES\" \t\t}, { \t\t\t\"Amount\": 9315, \t\t\t\"Particulars\": \"TEACHER WELFARE FEES\" \t\t}, { \t\t\t\"Amount\": 344860, \t\t\t\"Particulars\": \"TUITION FEE\" \t\t}, { \t\t\t\"Amount\": 12100, \t\t\t\"Particulars\": \"SPECIAL FEES\" \t\t}, { \t\t\t\"Amount\": 9315, \t\t\t\"Particulars\": \"TEACHER WELFARE FEES\" \t\t}, { \t\t\t\"Amount\": 344860, \t\t\t\"Particulars\": \"TUITION FEE\" \t\t}, { \t\t\t\"Amount\": 12100, \t\t\t\"Particulars\": \"SPECIAL FEES\" \t\t}, { \t\t\t\"Amount\": 9315, \t\t\t\"Particulars\": \"TEACHER WELFARE FEES\" \t\t}, { \t\t\t\"Amount\": 344860, \t\t\t\"Particulars\": \"TUITION FEE\" \t\t}, { \t\t\t\"Amount\": 12100, \t\t\t\"Particulars\": \"SPECIAL FEES\" \t\t}, { \t\t\t\"Amount\": 9315, \t\t\t\"Particulars\": \"TEACHER WELFARE FEES\" \t\t}, { \t\t\t\"Amount\": 344860, \t\t\t\"Particulars\": \"TUITION FEE\" \t\t}, { \t\t\t\"Amount\": 12100, \t\t\t\"Particulars\": \"SPECIAL FEES\" \t\t}, { \t\t\t\"Amount\": 9315, \t\t\t\"Particulars\": \"TEACHER WELFARE FEES\" \t\t}, { \t\t\t\"Amount\": 344860, \t\t\t\"Particulars\": \"TUITION FEE\" \t\t}, { \t\t\t\"Amount\": 12100, \t\t\t\"Particulars\": \"SPECIAL FEES\" \t\t}, { \t\t\t\"Amount\": 9315, \t\t\t\"Particulars\": \"TEACHER WELFARE FEES\" \t\t}, { \t\t\t\"Amount\": 344860, \t\t\t\"Particulars\": \"TUITION FEE\" \t\t}, { \t\t\t\"Amount\": 12100, \t\t\t\"Particulars\": \"SPECIAL FEES\" \t\t}, { \t\t\t\"Amount\": 9315, \t\t\t\"Particulars\": \"TEACHER WELFARE FEES\" \t\t}, { \t\t\t\"Amount\": 344860, \t\t\t\"Particulars\": \"TUITION FEE\" \t\t}, { \t\t\t\"Amount\": 12100, \t\t\t\"Particulars\": \"SPECIAL FEES\" \t\t}, { \t\t\t\"Amount\": 9315, \t\t\t\"Particulars\": \"TEACHER WELFARE FEES\" \t\t}, { \t\t\t\"Amount\": 344860, \t\t\t\"Particulars\": \"TUITION FEE\" \t\t}, { \t\t\t\"Amount\": 12100, \t\t\t\"Particulars\": \"SPECIAL FEES\" \t\t}, { \t\t\t\"Amount\": 9315, \t\t\t\"Particulars\": \"TEACHER WELFARE FEES\" \t\t}, { \t\t\t\"Amount\": 344860, \t\t\t\"Particulars\": \"TUITION FEE\" \t\t}, { \t\t\t\"Amount\": 12100, \t\t\t\"Particulars\": \"SPECIAL FEES\" \t\t}, { \t\t\t\"Amount\": 9315, \t\t\t\"Particulars\": \"TEACHER WELFARE FEES\" \t\t}, { \t\t\t\"Amount\": 344860, \t\t\t\"Particulars\": \"TUITION FEE\" \t\t}, { \t\t\t\"Amount\": 12100, \t\t\t\"Particulars\": \"SPECIAL FEES\" \t\t}, { \t\t\t\"Amount\": 9315, \t\t\t\"Particulars\": \"TEACHER WELFARE FEES\" \t\t}, { \t\t\t\"Amount\": 344860, \t\t\t\"Particulars\": \"TUITION FEE\" \t\t}, { \t\t\t\"Amount\": 12100, \t\t\t\"Particulars\": \"SPECIAL FEES\" \t\t}, { \t\t\t\"Amount\": 9315, \t\t\t\"Particulars\": \"TEACHER WELFARE FEES\" \t\t}, { \t\t\t\"Amount\": 344860, \t\t\t\"Particulars\": \"TUITION FEE\" \t\t}] \t} }";

                    if (!response.substring(1, response.length() - 1).replace("\\", "").toString().equalsIgnoreCase(Invalid)) {

                        String FinalResponse_Str = response.substring(1, response.length() - 1).replace("\\", "").toString();

                        Log.e("Particulars: ", FinalResponse_Str);

                        JSONObject jsonObject = new JSONObject(response);
                        jsonObject = jsonObject.getJSONObject("ds");
                        JSONArray jsonArray = jsonObject.getJSONArray("Table");//Fee Count

                        categorywise=new StringBuilder();

                        String Amount = "-", Particulars = "-";

                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            jsonObject = jsonArray.getJSONObject(i);
                            Amount = jsonObject.getString("Amount");
                            Particulars = jsonObject.getString("Particulars");


                            categorywise.append( "  <tr>\n" +
                                    "\t<td>"+checkNullEmpty(Particulars).toString()+"</td>\n" +
                                    "\t<td>"+checkNullEmpty(ConvertToCurrency(Integer.parseInt(Amount)))+"</td>\n" +
                                    "\n");

                            Total_amount+=Integer.parseInt(Amount);

                        }


                        LoadWebView();


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(String error) {
                // TODO: with error
                Log.e("Error: ", error);

                Baseconfig.SweetDialgos(3, Particular_Info.this, "Information", "Failed to get data..try again", "Ok");
            }
        });


    }



    //**********************************************************************************************


    StringBuilder categorywise;

    public void LoadWebView()
    {

        String webview_values = "<!DOCTYPE html>\n" +
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
                "<font class=\"sub\"><i class=\"fa fa-address-card-o fa-2x\" aria-hidden=\"true\"></i>"+PARTICULAR_CATEGORY+"</font>\n" +
                "\n" +
                "<div class=\"table-responsive\">          \n" +
                "<table class=\"table table-bordered\">\n" +
                "  <tr>\n" +
                "    <th bgcolor=\"#2d72c2\"><font color=\"#fff\">Particulars</font></th>\n" +
                "    <th bgcolor=\"#2d72c2\"><font color=\"#fff\">Amount</font></th>\n" +
                "\n" +
                "  </tr>\n" +

                categorywise+
                "\t</tr><tr>" +
                "<td bgcolor=\"#2d72c2\"><font color=\"#fff\">Total Amount</td>\n" +
                "<td bgcolor=\"#2d72c2\"><font color=\"#fff\">"+checkNullEmpty(ConvertToCurrency(Total_amount))+"</td>\n" +
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

        MyDynamicToast.informationMessage(Particular_Info.this, "Please wait Particulars loading..");


        student_webvw.getSettings().setJavaScriptEnabled(true);

        student_webvw.getSettings().setAllowContentAccess(true);


        student_webvw.setOnLongClickListener(v -> true);

        student_webvw.setLongClickable(false);


        student_webvw.addJavascriptInterface(new WebAppInterface(Particular_Info.this), "android");
        try {

            student_webvw.loadDataWithBaseURL("file:///android_asset/", webview_values, "text/html", "utf-8", null);

        } catch (Exception e) {
            e.printStackTrace();
        }



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

        return_str=  NumberFormat.getNumberInstance(Locale.ENGLISH).format(Value);

        return return_str;
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




}


