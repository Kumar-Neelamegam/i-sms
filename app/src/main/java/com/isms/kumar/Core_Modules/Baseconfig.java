package com.isms.kumar.Core_Modules;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Android on 5/6/2017.
 */

public class Baseconfig
{

    private static int InternetFlag;

    //*********************************************************************************
    public static Dialog showCustomDialog(String title, String message, Activity ctx)
    {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View inflatedLayout= inflater.inflate(R.layout.popup_layout, null, false);
        Dialog builderDialog = new Dialog(ctx);
        builderDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        TextView messageView = (TextView) inflatedLayout.findViewById(R.id.message);
        TextView titleView = (TextView) inflatedLayout.findViewById(R.id.title);
        ImageView image  = (ImageView) inflatedLayout.findViewById(R.id.load_image);
        // Create an animation instance
        Animation an;// = new RotateAnimation(0.0f, 360.0f, 1, 1);
        an = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        // Set the animation's parameters
        an.setDuration(700);               // duration in ms
        an.setRepeatCount(Animation.INFINITE);                // -1 = infinite repeated
        //an.setRepeatMode(Animation.RESTART); // reverses each repeat
        an.setFillAfter(true);               // keep rotation after animation

        // Aply animation to image view
        image.setAnimation(an);

        messageView.setText(message);
        titleView.setText(title);
        builderDialog.setContentView(inflatedLayout);
        builderDialog.setCancelable(false);
        builderDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation2;


        //builderDialog.show();
        return builderDialog;

    }

    //******************************************************************************************

    public static void SweetDialgos(int Id, Context ctx, String Title, String Message1, String Message2)
    {

        switch (Id)
        {


            //A basic message
            case 1:

                new SweetAlertDialog(ctx)
                        .setTitleText(Title)
                        .show();

                break;

            //A title with a text under
            case 2:

                new SweetAlertDialog(ctx)
                        .setTitleText(Title)
                        .setContentText(Message1)
                        .show();

                break;

            //A error message
            case 3:

                new SweetAlertDialog(ctx, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText(Title)
                        .setContentText(Message1)
                        .show();

                break;
            //A warning message
            case 4:

                new SweetAlertDialog(ctx, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(Title)
                        .setContentText(Message1)
                        .setConfirmText(Message2)
                        .show();

                break;

            case 5:

                new SweetAlertDialog(ctx, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Won't be able to recover this file!")
                        .setCancelText("No,cancel plx!")
                        .setConfirmText("Yes,delete it!")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                // reuse previous dialog instance, keep widget user state, reset them if you need
                                sDialog.setTitleText("Cancelled!")
                                        .setContentText("Your imaginary file is safe :)")
                                        .setConfirmText("OK")
                                        .showCancelButton(false)
                                        .setCancelClickListener(null)
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);


                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.setTitleText("Deleted!")
                                        .setContentText("Your imaginary file has been deleted!")
                                        .setConfirmText("OK")
                                        .showCancelButton(false)
                                        .setCancelClickListener(null)
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            }
                        })
                        .show();

                break;

        }


    }
    // **************************************************************

    public static void ExitSweetDialog(final Context ctx,final Class<?> className)
    {

        new SweetAlertDialog(ctx, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(ctx.getResources().getString(R.string.information))
                .setContentText(ctx.getResources().getString(R.string.message))
                .setCancelText(ctx.getResources().getString(R.string.no))
                .setConfirmText(ctx.getResources().getString(R.string.yes))
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {


                        sDialog.dismiss();

                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        sDialog.dismiss();
                        ((Activity) ctx).finishAffinity();



                    }
                })
                .show();

    }
    // **************************************************************

    public static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    //*********************************************************************************

    public static boolean CheckNW(Context ctx) {
        ConnectivityManager cn = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nf = cn.getActiveNetworkInfo();
        if (nf != null && nf.isConnected() == true) {

            Baseconfig.InternetFlag = 1;
            return true;
        } else {

            Baseconfig.InternetFlag = 0;
            return false;
        }
    }
    //*********************************************************************************




}
