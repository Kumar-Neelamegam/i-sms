package com.isms.kumar.Core_Modules;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



/**
 * Created by Android on 11/30/2016.
 */

public class DashboardNew extends Fragment {


    //*********************************************************************************************

    public GridLayoutManager lLayout;

    //*********************************************************************************************

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.dashboardnew, container, false);

        try
        {

            GetInitialize(v);

            Controllisterners(v);




        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

        return v;
    }
    //*********************************************************************************************

    public void GetInitialize(View v) {




    }


    //*********************************************************************************************

    public void Controllisterners(View v) {



    }

    //*********************************************************************************************


}