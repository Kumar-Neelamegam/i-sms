package com.isms.kumar.Core_Modules;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.isms.kumar.Utilities.RuntimePermissionsActivity;

public class Splash_Activity extends RuntimePermissionsActivity implements ActivityCompat.OnRequestPermissionsResultCallback {


    /**
     * Created at 14/05/2017
     * Muthukumar N & Vidhya K
     */

    //****************************************************************************
    //Declaration
    ProgressBar progressBar;
    int progress;
    TextView progress_status;
    int progressStatus = 0;
    Handler handler = new Handler();

    //****************************************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        try {

            //For permission
            isStoragePermissionGranted();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //****************************************************************************
    //After permission granted
    @Override
    public void onPermissionsGranted(int requestCode) {
        try {
            GetInitialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //****************************************************************************
    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }

    //****************************************************************************

    //Initialization

    public void GetInitialize() {

        YoYo.with(Techniques.BounceIn).duration(2500).playOn(findViewById(R.id.logo));

        progressBar = (ProgressBar) findViewById(R.id.spin_kit);

        progress_status = (TextView) findViewById(R.id.textprgrs);

        LoadNextActivity();

    }

    //****************************************************************************

    public void LoadNextActivity() {


        new Thread(new Runnable() {
            public void run() {

                while (progressStatus < 100) {
                    progressStatus = doSomeWork();

                    handler.post(new Runnable() {
                        public void run() {
                            //progressBar.setProgress(progressStatus);
                            progress_status.setText((String.valueOf(progressStatus)));
                        }
                    });
                }

                handler.post(new Runnable() {
                    public void run() {

                        try {

                            finish();
                            Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
                            startActivity(intent);

                        } catch (Exception e) {

                            e.printStackTrace();
                        }

                    }
                });
            }

            private int doSomeWork() {
                try {
                    // ---simulate doing some work---
                    Thread.sleep(30);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return
                        ++progress;
            }
        }).start();

    }
//****************************************************************************


    private static final int REQUEST_PERMISSIONS = 200;

    public void isStoragePermissionGranted() {

        Splash_Activity.super.requestAppPermissions(new
                        String[]{
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.CHANGE_WIFI_STATE
                }, R.string
                        .runtime_permissions_txt
                , REQUEST_PERMISSIONS);


    }

    //***************************************************************************************************


}
