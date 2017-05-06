package com.isms.kumar.Core_Modules;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Vidhya-Android on 5/6/2017.
 */

public class StudentDetails_Activity extends AppCompatActivity
{

    // Variable creation

    TextView male,female,total_Stu;
    TextView graph,grid;



    //**********************************************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_student_details);

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

        male = (TextView)findViewById(R.id.male_count);
        female = (TextView)findViewById(R.id.female_count);
        total_Stu = (TextView)findViewById(R.id.stu_total);

        graph = (TextView)findViewById(R.id.txt_graph);
        grid = (TextView)findViewById(R.id.txt_grid);

    }

    //**********************************************************************************************

    private void Controlistener()
    {

        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //******************************************************************************************


        grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //******************************************************************************************

    }


    //**********************************************************************************************



//End
//**********************************************************************************************

}
