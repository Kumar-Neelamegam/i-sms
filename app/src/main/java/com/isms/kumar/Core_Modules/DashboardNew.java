package com.isms.kumar.Core_Modules;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.isms.kumar.Adapters.Getter_Setter;
import com.isms.kumar.Json_Webservices.RestClientHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.isms.kumar.Json_Webservices.RestClientHelper.context;


/**
 * Created at 06/05/2017
 * Muthukumar N & Vidhya K
 */

public class DashboardNew extends Fragment {

    private static String SESSION_DATABASE = "";


    //*********************************************************************************************

    public GridLayoutManager lLayout;

    public final String title_name[] = {
            "Student Details",
            "Fees Collection",
            "Student Dues",
            "Attendance",
            "Admission Details",
            "SMS"
    };

    public final int image_drawables[] = {
            R.drawable.ic_dashboard_studentdetails,
            R.drawable.ic_dashboard_fee,
            R.drawable.ic_dashboard_due,
            R.drawable.ic_dashboard_attendance,
            R.drawable.ic_dashboard_admission,
            R.drawable.ic_dashboard_sms
    };

    CardView Student_Info, Student_Due, Student_Collection, Student_Attendance, Student_Admission, Student_SMS;

    TextView TotalCount, Male, Female, Total_Collection, Txt_BillCount;


    //*********************************************************************************************

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dashboardnew1, container, false);

        try {

            GetInitialize(v);

            Controllisterners(v);

        } catch (Exception e) {
            e.printStackTrace();

        }

        return v;
    }
    //*********************************************************************************************

    RecyclerView recyclerView;

    public void GetInitialize(View v) {

        Bundle b = getArguments();
        SESSION_DATABASE = b.getString("SESSION_DB_NAME");

        /**
         * To load recycler view old dashboard
         */
      /*  recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<Getter_Setter.Dashboard_Dataobjects> DataItems = prepareData();
        DashboardAdapter adapter = new DashboardAdapter(getActivity(), DataItems,SESSION_DATABASE);
        recyclerView.setAdapter(adapter);*/

        Student_Info = (CardView) v.findViewById(R.id.student_info_layout);
        Student_Due = (CardView) v.findViewById(R.id.student_due_layout);
        Student_Collection = (CardView) v.findViewById(R.id.student_collection_layout);
        Student_Attendance = (CardView) v.findViewById(R.id.student_attendance_layout);
        Student_Admission = (CardView) v.findViewById(R.id.student_admission_layout);
        Student_SMS = (CardView) v.findViewById(R.id.student_sms_layout);

        YoYo.with(Techniques.BounceIn).duration(1500).playOn(v.findViewById(R.id.student_info_layout));
        YoYo.with(Techniques.BounceIn).duration(1500).playOn(v.findViewById(R.id.student_due_layout));
        YoYo.with(Techniques.BounceIn).duration(1500).playOn(v.findViewById(R.id.student_collection_layout));
        YoYo.with(Techniques.BounceIn).duration(1500).playOn(v.findViewById(R.id.student_attendance_layout));
        YoYo.with(Techniques.BounceIn).duration(1500).playOn(v.findViewById(R.id.student_admission_layout));
        YoYo.with(Techniques.BounceIn).duration(1500).playOn(v.findViewById(R.id.student_sms_layout ));


        TotalCount = (TextView) v.findViewById(R.id.stu_total);
        Male = (TextView) v.findViewById(R.id.male_count);
        Female = (TextView) v.findViewById(R.id.female_count);
        Total_Collection = (TextView) v.findViewById(R.id.total_collection);
        Txt_BillCount = (TextView) v.findViewById(R.id.billcount);


        GetCurrentDateValues();


    }

    //*********************************************************************************************

    public void GetCurrentDateValues() {



        //*****************************************************
        /**
         * To get current strength of student
         * Male and Female
         */
        //Static Testing URL
        //String URL ="http://api.ispidersolutions.com/schoolapi/api/App/GetStudentDetails?DatabaseName="+SESSION_DATABASE+"";

        String URL = "http://api.ispidersolutions.com/schoolapi/api/App/GetStudentDetails?DatabaseName=iSMSReddiar";

        Log.e("PASSING URL 1: ", URL);

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

                        //**************************************************************************
                        /**
                         * To get male and female students count
                         * @Muthukumar
                         */

                        String MALE_STUDENTS_COUNT = "0", FEMALE_STUDENTS_COUNT = "0";

                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            String Category = jsonObject.getString("Catgory");
                            String Nos = jsonObject.getString("Nos");
                            Log.e("Category + Nos: ", Category + " / " + Nos);

                            if (Category.toString().equals("Male")) {
                                MALE_STUDENTS_COUNT = Nos;
                            } else if (Category.toString().equals("Female")) {
                                FEMALE_STUDENTS_COUNT = Nos;
                            }

                        }

                        Male.setText(MALE_STUDENTS_COUNT);
                        Female.setText(FEMALE_STUDENTS_COUNT);

                        int TotalStrength = Integer.parseInt(MALE_STUDENTS_COUNT) + Integer.parseInt(FEMALE_STUDENTS_COUNT);
                        TotalCount.setText(String.valueOf(TotalStrength));


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



        //*****************************************************

        /**
         * To get all total collections
         * Fee
         */
        //Original URL
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String FEE_URL = "http://api.ispidersolutions.com/schoolapi/api/App/GetBillingDetails?DatabaseName="+SESSION_DATABASE+"&FromDate="+dateFormat.format(new Date())+"&ToDate="+dateFormat.format(new Date())+"";


        //Static Testing URL
        FEE_URL = "http://api.ispidersolutions.com/schoolapi/api/App/GetBillingDetails?DatabaseName=iSMSReddiar&FromDate=01/01/2016&ToDate=01/01/2018";

        Log.e("PASSING URL 2: ", FEE_URL);

        //Get Request
        RestClientHelper.getInstance(getActivity()).get(FEE_URL, new RestClientHelper.RestClientListener() {


            @Override
            public void onSuccess(String response) {

                try {

                    String Invalid = "{\"ds\":{\"Table\":[{\"Status\":\"Invalid\"}]}}";

                    if (!response.substring(1, response.length() - 1).replace("\\", "").toString().equalsIgnoreCase(Invalid)) {

                        String FinalResponse_Str = response.substring(1, response.length() - 1).replace("\\", "").toString();

                        Log.e("FinalResponse_Str",FinalResponse_Str);

                        JSONObject jsonObject = new JSONObject(FinalResponse_Str);
                        jsonObject = jsonObject.getJSONObject("ds");
                        JSONArray jsonArray = jsonObject.getJSONArray("Table");//Fee Count


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

                        }

                        Total_Collection.setText("₹  "+Baseconfig.ConvertToCurrency(Integer.parseInt(Amount)));
                        Txt_BillCount.setText(BillCount);

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

        //*****************************************************f


    }

        //*********************************************************************************************

        private ArrayList<Getter_Setter.Dashboard_Dataobjects> prepareData () {

            ArrayList<Getter_Setter.Dashboard_Dataobjects> Dataitems = new ArrayList<>();
            for (int i = 0; i < title_name.length; i++) {
                Getter_Setter.Dashboard_Dataobjects obj = new Getter_Setter.Dashboard_Dataobjects();
                obj.setTitle_Name(title_name[i]);
                obj.setIcon(image_drawables[i]);
                Dataitems.add(obj);
            }
            return Dataitems;

        }

        //*********************************************************************************************

        Fragment fragment = null;
        Class fragmentClass;

        public void Controllisterners (View v)
        {


            Bundle args=new Bundle();
            args.putString("SESSION_DB_NAME",SESSION_DATABASE);


            Student_Info.setOnClickListener(view -> {

                fragmentClass=StudentDetails_Activity.class;
                try
                {
                    fragment = (Fragment) fragmentClass.newInstance();
                    fragment.setArguments(args);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Insert the fragment by replacing any existing fragment
                FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();



            });






            Student_Collection.setOnClickListener(view -> {


                fragmentClass=FeeCollection_Activity.class;
                try
                {
                    fragment = (Fragment) fragmentClass.newInstance();
                    fragment.setArguments(args);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Insert the fragment by replacing any existing fragment
                FragmentManager fragmentManager1 = ((FragmentActivity)context).getSupportFragmentManager();
                fragmentManager1.beginTransaction().replace(R.id.frame, fragment).commit();

            });











        }

        //*********************************************************************************************


    }