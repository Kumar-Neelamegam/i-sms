package com.isms.kumar.Core_Modules;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.isms.kumar.Json_Webservices.RestClientHelper;

/**
 * Created by Vidhya-Android on 5/6/2017.
 */

public class Login_Activity extends AppCompatActivity {

    // Variable creation

    EditText username, password;
    CheckBox remember;
    info.hoang8f.widget.FButton login;


    Toolbar toolbar;
    ImageView exit;


    // shared preference
    SharedPreferences sharedPreferences;


    /**
     * Created at 06/05/2017
     * Muthukumar N & Vidhya K
     */
    //**********************************************************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {
            GetInitialize();
            Controlistener();

            //Remeber Me
            loadSavedPreferences();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //**********************************************************************************************

    private void GetInitialize() {

        //Initialize

        username = (EditText) findViewById(R.id.edt_user);
        password = (EditText) findViewById(R.id.edt_pwd);
        remember = (CheckBox) findViewById(R.id.chk_remember);
        login = (info.hoang8f.widget.FButton) findViewById(R.id.btn_login);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        exit = (ImageView) toolbar.findViewById(R.id.ic_exit);

    }


    //**********************************************************************************************

    private void Controlistener() {

        setSupportActionBar(toolbar);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Baseconfig.ExitSweetDialog(Login_Activity.this, null);


            }
        });

        // Click Event Declaration
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (username.getText().length() > 0 && password.getText().toString().length() > 0) {
                    //Validate Username and password
                    if (Baseconfig.CheckNW(Login_Activity.this)) {
                        ValidateUser();
                    } else {
                        Baseconfig.SweetDialgos(4, Login_Activity.this, "Information", "Dataconnection not available..try again..", "Ok");
                    }

                } else {
                    Baseconfig.SweetDialgos(3, Login_Activity.this, "Information", "Enter user credentials", "Ok");
                }

            }
        });


    }

    //**********************************************************************************************

    /**
     * To get user details and information
     * saving in local shared preferences
     * Muthukumar N & Vidhya K
     * 07/05/2017
     */

    public void ValidateUser() {

        //need to check username and password before proceeding'
        //boolean b = loadSavedPreferences();


        if (LOCAL_JSON_LOGIN_INFO.length()>0)//Session local iruntha direct next activity
        {

            Login_Activity.this.finish();
            Intent next = new Intent(Login_Activity.this, Task_Navigation.class);
            next.putExtra("JSON_OBJ", LOCAL_JSON_LOGIN_INFO.toString());
            startActivity(next);
        } else//ilaina api json get panitu save panum - first time matum call agum
        {

            String Username = username.getText().toString();
            String Password = password.getText().toString();

            String URL = "http://api.ispidersolutions.com/schoolapi/api/App/GetUserDetails?UserName=" + Username + "&Password=" + Password + "";

            Log.e("Login API: ",URL);

            //Get Request
            RestClientHelper.getInstance(Login_Activity.this).get(URL, new RestClientHelper.RestClientListener() {


                @Override
                public void onSuccess(String response) {


                    try {
                        String Invalid = "{\"ds\":{\"Table\":[{\"Status\":\"Invalid\"}]}}";

                        if (!response.substring(1, response.length() - 1).replace("\\", "").toString().equalsIgnoreCase(Invalid)) {

                            String Response_Pass = response.substring(1, response.length() - 1).replace("\\", "").toString();

                            try {

                                ////////////////////////////////////////////////////////////////////////////////
                                savePreferences("CHECKBOX", remember.isChecked());
                                {
                                    if (remember.isChecked()) {
                                        savePreferences("NAME", username.getText().toString());
                                        savePreferences1("PWD", password.getText().toString());
                                    } else {
                                        clearPreference();
                                    }
                                }
                                //////////////////////////////////////////////////////////////////////////////////

                                savePreferences1("JSON_OBJ_LOCAL", Response_Pass.toString());

                                Login_Activity.this.finish();
                                Intent next = new Intent(Login_Activity.this, Task_Navigation.class);
                                next.putExtra("JSON_OBJ", Response_Pass.toString());
                                startActivity(next);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            Baseconfig.SweetDialgos(3, Login_Activity.this, "Information", "Invalid User Credentials..", "Ok");
                            username.setText("");
                            password.setText("");

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onError(String error) {
                    // TODO: with error
                    Log.e("Error: ", error);

                    Baseconfig.SweetDialgos(3, Login_Activity.this, "Information", "Failed to get data..try again", "Ok");
                }
            });

        }


    }


    //**********************************************************************************************

    @Override
    public void onBackPressed() {

    }

    //#######################################################################################################
    String LOCAL_JSON_LOGIN_INFO;

    private boolean loadSavedPreferences() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        boolean cbValue = sp.getBoolean("CHECKBOX", false);
        String name = sp.getString("NAME", "");
        String password = sp.getString("PWD", "");
        String json_valu = sp.getString("JSON_OBJ_LOCAL", "");

        LOCAL_JSON_LOGIN_INFO = json_valu;

        if (cbValue) {
            remember.setChecked(true);

        } else {
            remember.setChecked(false);
        }

        username.setText(name);
        this.password.setText(password);


        if (remember.isChecked()) {
            username.clearFocus();

            this.password.clearFocus();

            login.setFocusable(true);
        }

        if (name.length() > 0 && password.length() > 0 && json_valu.toString().length()>0) {

            return true;
        } else {
            return false;
        }


    }


    //#######################################################################################################
    /*
     *Remeber Me Using Shared Preference
	 */
    private void savePreferences(String key, boolean value) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    private void savePreferences(String key, String value) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }

    private void savePreferences1(String key, String value) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }

    private void savePreferences2(String key, String value) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }

    private void clearPreference() {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = sp.edit();
        edit.clear();
        edit.commit();
    }


    //#######################################################################################################


//End
//**********************************************************************************************

}