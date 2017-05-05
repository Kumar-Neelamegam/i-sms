package com.isms.kumar.Core_Modules;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.isms.kumar.i_sms.R;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sample.Ds;
import sample.GetTodos;

public class Splash_Activity extends AppCompatActivity {
    private Retrofit retrofit;
    private GetTodos getTodos;
    TextView sample;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


       /* sample=(TextView)findViewById(R.id.txtvw);
        try {
            //Test
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.ispidersolutions.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            getTodos = retrofit.create(GetTodos.class);

            Call<Ds> call = getTodos.all();
            Response<Ds> response = call.execute();
            Ds result = response.body();

            String str=result.getTable().get(0).getEmployeeName();

            sample.setText(result.toString()+"-"+str);

        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
