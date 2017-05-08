package com.isms.kumar.Core_Modules;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class Task_Navigation extends AppCompatActivity {

    //*********************************************************************************************

    //Defining Variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    TextView Tv_EmployeeName,Tv_EmployeeEmail;


    Bundle b;

    //*********************************************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);


        try
        {


            GetInitialize();

            Controllisteners();


        } catch (Exception e) {
            e.printStackTrace();

        }

    }


    //**********************************************************************************************

    static TextView Title_TextVw;

    String PASSING_DBNAME;

    private void GetInitialize()
    {

        try {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            Title_TextVw=(TextView)toolbar.findViewById(R.id.txvw_title);



            navigationView = (NavigationView) findViewById(R.id.navigation_view);
            drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

            //**********************************

            /*"UserDetailsID": 1,
                    "EmployeeName": "vinoth",
                    "EmailID": "sdfds@gmail.com",
                    "UserName": "vinoth",
                    "InstitutionID": "vinoth1",
                    "InstitutionName": "IMMACULATE",
                    "DataBaseName": "ISMSARIRYUR",
                    "Address1": "Address1",
                    "Address2": "Address2",
                    "AreaCode": "area code",
                    "Pincode": "6005001",
                    "PhoneNo": "land line",
                    "FaxNo": "faxno",
                    "Email": "emaildi@gmail.com",
                    "Website": "website"
                    */

            b=getIntent().getExtras();
            String response_str=b.getString("JSON_OBJ");

            JSONObject jsonObject = new JSONObject(response_str);
            jsonObject = jsonObject.getJSONObject("ds");
            JSONArray jsonArray = jsonObject.getJSONArray("Table");

            String EmployeeName="-", EmployeeEmail="-";

            for(int i=0;i<jsonArray.length();i++)
            {
                jsonObject = jsonArray.getJSONObject(i);
                EmployeeName = jsonObject.getString("EmployeeName");
                EmployeeEmail = jsonObject.getString("Email");
                PASSING_DBNAME = jsonObject.getString("DataBaseName");
            }


            //Baseconfig.SweetDialgos(3, Task_Navigation.this, "Information2", jsonArray.toString()+"/"+EmployeeName, "Ok");
            View header=navigationView.getHeaderView(0);
            Tv_EmployeeName=(TextView)header.findViewById(R.id.txt_employeename);
            Tv_EmployeeEmail=(TextView)header.findViewById(R.id.txt_employeeemail);

            Tv_EmployeeName.setText(EmployeeName);
            Tv_EmployeeEmail.setText(EmployeeEmail);


            setupDrawerContent(navigationView);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //**********************************************************************************************


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.


        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    //*********************************************************************************************

    private void setupDrawerContent(NavigationView navigationView) {


        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {


                        selectDrawerItem(menuItem);


                        return true;
                    }
                });


        // Initializing Drawer Layout and ActionBarToggle
        // drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(Task_Navigation.this, drawerLayout, toolbar, 0, 0) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //  //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();


        selectDrawerItem(navigationView.getMenu().getItem(0));//Myths & Facts New Intent
        navigationView.getMenu().getItem(0).setChecked(true);


    }

    //*********************************************************************************************
    public void selectDrawerItem(MenuItem menuItem) {


        switch (menuItem.getItemId()) {

            case R.id.items00:

                // fragmentClass = DashboardNew.class;
                onSectionAttached(0, menuItem);


                break;

            case R.id.items0:

                // fragmentClass = DashboardNew.class;
                onSectionAttached(1, menuItem);


                break;

            case R.id.items1:

                // fragmentClass = About.class;
                onSectionAttached(2, menuItem);

                break;

            case R.id.items2:

                // fragmentClass = BC.class;
                onSectionAttached(3, menuItem);

                break;

            case R.id.items3:

                // fragmentClass = BC_ReadMore.class;
                onSectionAttached(4, menuItem);

                break;

            case R.id.items4:
                onSectionAttached(5, menuItem);

                //  fragmentClass = BBI.class;

                break;

            case R.id.items5:
                onSectionAttached(6, menuItem);

                // fragmentClass = PinkConnection.class;

                break;

            case R.id.items6:
                onSectionAttached(7, menuItem);

                // fragmentClass = PinkConnection.class;

                break;


        }


    }

    //*********************************************************************************************
    Fragment fragment = null;
    Class fragmentClass;

    public void onSectionAttached(int number, MenuItem menuItem) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        FragmentManager fragmentManager;

        Bundle args=new Bundle();
        args.putString("SESSION_DB_NAME",PASSING_DBNAME);

        switch (number) {
            case 0:


                fragmentClass = DashboardNew.class;
                try {


                    fragment = (Fragment) fragmentClass.newInstance();
                    fragment.setArguments(args);


                } catch (Exception e) {
                    e.printStackTrace();

                }

                // Insert the fragment by replacing any existing fragment
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();
                Title_TextVw.setText(R.string.dashboard);


                break;
            case 1:

                //new StudentDetails_Activity(Title_TextVw);
                fragmentClass = StudentDetails_Activity.class;

                try {


                    fragment = (Fragment) fragmentClass.newInstance();
                    fragment.setArguments(args);


                } catch (Exception e) {
                    e.printStackTrace();

                }

                // Insert the fragment by replacing any existing fragment
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();
                Title_TextVw.setText(R.string.student_details);


                break;
            case 2:


                fragmentClass = FeeCollection_Activity.class;
                try {


                    fragment = (Fragment) fragmentClass.newInstance();
                    fragment.setArguments(args);


                } catch (Exception e) {
                    e.printStackTrace();

                }

                // Insert the fragment by replacing any existing fragment
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();
                Title_TextVw.setText(R.string.fees_collection);

                break;
            case 3:



                break;
            case 4:


                break;
            case 5:


                break;

            case 6:

                break;

            case 7:

                new SweetAlertDialog(Task_Navigation.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(this.getResources().getString(R.string.information))
                        .setContentText("Are you sure want to signout")
                        .setCancelText(this.getResources().getString(R.string.no))
                        .setConfirmText(this.getResources().getString(R.string.yes))
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
                                Task_Navigation.this.finishAffinity();
                                startActivity(new Intent(Task_Navigation.this,Login_Activity.class));



                            }
                        })
                        .show();


                break;


        }


        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        //setTitle(menuItem.getTitle());
        // Close the navigation drawer
        drawerLayout.closeDrawers();


    }
    //*******************************************************************************************************

    ImageView exit;

    public void Controllisteners() {

        exit = (ImageView) toolbar.findViewById(R.id.ic_exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Baseconfig.ExitSweetDialog(Task_Navigation.this, Task_Navigation.class);


            }
        });

    }
    //*******************************************************************************************************


    @Override
    public void onBackPressed() {


        selectDrawerItem(navigationView.getMenu().getItem(0));

    }

    //*******************************************************************************************************



}
