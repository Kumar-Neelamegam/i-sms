package com.isms.kumar.Core_Modules;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Vidhya-Android on 5/6/2017.
 */

public class FeeCollection_Activity extends AppCompatActivity
{

    // Variable creation

    TextView tot_collection,bill_count,cancel,cons_count,cons_amt;
    TextView graph,grid;

    //**********************************************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_collection);

        try
        {
            GetInitialize();
            Controlistener();

        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    //**********************************************************************************************

    private void GetInitialize()
    {
        //Initialize

        tot_collection = (TextView)findViewById(R.id.txt_collection);
        bill_count = (TextView)findViewById(R.id.txt_bill);
        cancel = (TextView)findViewById(R.id.txt_cancel);
        cons_count = (TextView)findViewById(R.id.txt_cons_count);
        cons_amt = (TextView)findViewById(R.id.txt_cons_amt);

        graph = (TextView)findViewById(R.id.txt_graph);
        grid = (TextView)findViewById(R.id.txt_grid);

    }

    //**********************************************************************************************

    private void Controlistener()
    {

        //Click Event

        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //*******************************************************************************************

        grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //******************************************************************************************

    }





//End
//**********************************************************************************************

}