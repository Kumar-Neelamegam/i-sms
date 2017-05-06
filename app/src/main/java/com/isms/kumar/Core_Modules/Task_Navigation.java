package com.isms.kumar.Core_Modules;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


import org.json.JSONObject;

import java.io.InputStream;

import ch.boye.httpclientandroidlib.HttpResponse;
import ch.boye.httpclientandroidlib.client.HttpClient;
import ch.boye.httpclientandroidlib.client.methods.HttpPost;
import ch.boye.httpclientandroidlib.entity.StringEntity;
import ch.boye.httpclientandroidlib.impl.client.DefaultHttpClient;


public class Task_Navigation extends AppCompatActivity {

    //*********************************************************************************************

    //Defining Variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;


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


    private void GetInitialize()
    {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        setupDrawerContent(navigationView);


    }


    //**********************************************************************************************


    /**
     * POST DATA TO BHI - SERVER
     * Inserting New User
     */
    private class HttpAsyncTask2 extends AsyncTask<String, Void, String> {

        Dialog builderDialog;


        @Override
        protected String doInBackground(String... urls) {


            return POST(urls[0]);
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            builderDialog = Baseconfig.showCustomDialog(Task_Navigation.this.getString(R.string.please_wait), "Loading..", Task_Navigation.this);

            builderDialog.show();
        }


        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            // Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();


            if (builderDialog.isShowing() && builderDialog != null) {
                builderDialog.dismiss();
            }

            //Toast.makeText(getBaseContext(), "Received!"+result, Toast.LENGTH_LONG).show();

            Log.e("BHI - Server: ", result);

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


        }


    }

    //*********************************************************************************************
    Fragment fragment = null;
    Class fragmentClass;

    public void onSectionAttached(int number, MenuItem menuItem) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        FragmentManager fragmentManager;

        switch (number) {
            case 0:


                fragmentClass = DashboardNew.class;
                try {


                    fragment = (Fragment) fragmentClass.newInstance();

                } catch (Exception e) {
                    e.printStackTrace();

                }

                // Insert the fragment by replacing any existing fragment
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();


                break;
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
            case 4:


                break;
            case 5:


                break;

            case 6:
                // fragmentClass = PinkConnection.class;
              /*  Task_Navigation.this.finish();
                Intent intent6 = new Intent(Task_Navigation.this, Video.class);
                Task_Navigation.this.startActivity(intent6);
                Task_Navigation.this.overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
*/

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



    //*******************************************************************************

    /**
     * HTTP Avantari
     * GET & POST METHOD
     *
     * @param url
     * @return
     * @Muthukumar 01/2/2017
     */
    public static String POST(String url) {
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            String json = "";

            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
           /* jsonObject.accumulate("name", person.getName());
            jsonObject.accumulate("country", person.getCountry());
            jsonObject.accumulate("twitter", person.getTwitter());
*/
            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            //httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if (inputStream != null)
                result = Baseconfig.convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;


    }


    //*******************************************************************************


}
