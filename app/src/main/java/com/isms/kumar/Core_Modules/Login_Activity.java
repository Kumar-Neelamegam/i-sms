package com.isms.kumar.Core_Modules;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * Created by Vidhya-Android on 5/6/2017.
 */

public class Login_Activity extends AppCompatActivity
{

    // Variable creation

    EditText username, password;
    CheckBox remember;
    info.hoang8f.widget.FButton login;


    //**********************************************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

        username = (EditText)findViewById(R.id.edt_user);
        password = (EditText)findViewById(R.id.edt_pwd);
        remember = (CheckBox)findViewById(R.id.chk_remember);
        login = (info.hoang8f.widget.FButton)findViewById(R.id.btn_login);


    }


    //**********************************************************************************************

    private void Controlistener()
    {

        // Click Event Declaration

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    //**********************************************************************************************




//End
//**********************************************************************************************

}