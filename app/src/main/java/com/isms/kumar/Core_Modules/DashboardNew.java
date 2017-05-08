package com.isms.kumar.Core_Modules;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.isms.kumar.Adapters.DashboardAdapter;
import com.isms.kumar.Adapters.Getter_Setter;

import java.util.ArrayList;


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
    //*********************************************************************************************

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dashboardnew, container, false);

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

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<Getter_Setter.Dashboard_Dataobjects> DataItems = prepareData();
        DashboardAdapter adapter = new DashboardAdapter(getActivity(), DataItems,SESSION_DATABASE);
        recyclerView.setAdapter(adapter);

    }

    //*********************************************************************************************

    private ArrayList<Getter_Setter.Dashboard_Dataobjects> prepareData() {

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

    public void Controllisterners(View v)
    {


    }

    //*********************************************************************************************


}