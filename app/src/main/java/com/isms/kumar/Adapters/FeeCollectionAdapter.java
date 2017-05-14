package com.isms.kumar.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.isms.kumar.Core_Modules.Baseconfig;
import com.isms.kumar.Core_Modules.Particular_Info;
import com.isms.kumar.Core_Modules.R;
import com.isms.kumar.Json_Webservices.RestClientHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Kumar on 5/6/2017.
 */

public class FeeCollectionAdapter extends RecyclerView.Adapter<FeeCollectionAdapter.ViewHolder> {

    private ArrayList<Getter_Setter.FeeCollection> DataItems;
    private Context context;
    Fragment fragment = null;
    Class fragmentClass;
    String SESSION_DATABASENAME;
    String FROMDATE, TODATE;

    //**********************************************************************************************

    public FeeCollectionAdapter(Context context, ArrayList<Getter_Setter.FeeCollection> DataItems, String SESSION_DATABASENAME, String FromDate, String ToDate) {
        this.DataItems = DataItems;
        this.context = context;
        this.SESSION_DATABASENAME = SESSION_DATABASENAME;
        this.FROMDATE = FromDate;
        this.TODATE = ToDate;
    }
    //**********************************************************************************************

    @Override
    public FeeCollectionAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fee_row_item, viewGroup, false);
        return new ViewHolder(view);
    }
    //**********************************************************************************************

    @Override
    public void onBindViewHolder(FeeCollectionAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.FeeCategory.setText(DataItems.get(i).getFeeCategory());
        viewHolder.FeeAmount.setText(ConvertToCurrency(Integer.parseInt(DataItems.get(i).getFeeAmount())));

        viewHolder.Btn_View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Bundle args = new Bundle();
                //args.putString("SESSION_DB_NAME", SESSION_DATABASENAME);

                //Toast.makeText(context, "Selected Category: " + DataItems.get(i).getFeeCategory(), Toast.LENGTH_SHORT).show();

                LoadData(SESSION_DATABASENAME,DataItems.get(i).getFeeCategory());


            }
        });

        if (i % 2 == 1) {
            viewHolder.full_layout.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        } else {
            viewHolder.full_layout.setBackgroundColor(context.getResources().getColor(R.color.ash));
        }


    }

    //**********************************************************************************************
    /**
     * Convert number to currency
     * @param Value
     * @return
     */
    public String ConvertToCurrency(int Value)
    {

        String return_str="0";

        return_str=  NumberFormat.getNumberInstance(Locale.US).format(Value);

        return return_str;
    }
    //**********************************************************************************************

    public void LoadData(String dbname, final String Category) {




        String APIURL="http://api.ispidersolutions.com/schoolapi/api/App/GetBillingDetails?DatabaseName="+dbname+"&FromDate="+FROMDATE+"&ToDate="+TODATE+"&FeesCategory="+Category+"";
        Intent viewdetails=new Intent(((Activity)context), Particular_Info.class);
        viewdetails.putExtra("SESSION_DB",dbname);
        viewdetails.putExtra("PARTICULAR_CATEGORY",Category);
        viewdetails.putExtra("API_URL",APIURL);
        ((Activity)context).startActivity(viewdetails);




     /*   //Original URL
        //String URL = "http://api.ispidersolutions.com/schoolapi/api/App/GetBillingDetails?DatabaseName="+dbname+"&FromDate=01/01/2016&ToDate=01/01/2018&FeesCategory="+Category+"";


        //Static Testing URL
        String URL = "http://api.ispidersolutions.com/schoolapi/api/App/GetBillingDetailsbyParticulars?DatabaseName=iSMSReddiar&FromDate=01/01/2016&ToDate=01/01/2018&FeesCategory="+Category+"";

        Log.e("PASSING URL CATEGORY: ", URL);


        RestClientHelper.getInstance(context).get(URL, new RestClientHelper.RestClientListener() {


            @Override
            public void onSuccess(String response) {

                try
                {

                    String Invalid = "{\"ds\":{\"Table\":[{\"Status\":\"Invalid\"}]}}";

                    if (!response.substring(1, response.length() - 1).replace("\\", "").toString().equalsIgnoreCase(Invalid)) {

                        String FinalResponse_Str = response.substring(1, response.length() - 1).replace("\\", "").toString();

                        *//*JSONObject jsonObject = new JSONObject(FinalResponse_Str);
                        jsonObject = jsonObject.getJSONObject("ds");
                        JSONArray jsonArray = jsonObject.getJSONArray("Table");//Fee Count

                        *//*//**//**************************************************************************
                        *//**//**
                         * To get fee particulars count
                         * @Muthukumar
                         *//**//*
                        String Amount = "-",Particulars = "-";

                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            jsonObject = jsonArray.getJSONObject(i);
                            Amount = jsonObject.getString("Amount");
                            Particulars = jsonObject.getString("Particulars");

                        }*//*

                        *//*LayoutInflater inflater = LayoutInflater.from(context);
                        View inflatedLayout= inflater.inflate(R.layout.popup_layout_particulars, null, false);
                        Dialog builderDialog = new Dialog(context);
                        builderDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                        TextView Txt_Category = (TextView) inflatedLayout.findViewById(R.id.txt_category);
                        TextView Txt_Amount = (TextView) inflatedLayout.findViewById(R.id.txt_amount);
                        TextView Txt_Particulars = (TextView) inflatedLayout.findViewById(R.id.txt_particulars);

                        Txt_Category.setText(" "+Category);
                        Txt_Amount.setText(" "+ConvertToCurrency(Integer.parseInt(Amount)));
                        Txt_Particulars.setText(" "+Particulars);

                        builderDialog.setContentView(inflatedLayout);
                        builderDialog.setCancelable(true);
                        builderDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation2;
                        builderDialog.show();*//*



                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(String error) {
                // TODO: with error
                Log.e("Error: ", error);

                Baseconfig.SweetDialgos(3, context, "Information", "Failed to get data..try again", "Ok");
            }
        });
*/

    }


    //**********************************************************************************************

    @Override
    public int getItemCount() {
        return DataItems.size();
    }

    //**********************************************************************************************

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView FeeCategory;
        private TextView FeeAmount;
        private ImageButton Btn_View;
        private LinearLayout full_layout;


        public ViewHolder(View view) {
            super(view);

            FeeCategory = (TextView) view.findViewById(R.id.fee_category);
            FeeAmount = (TextView) view.findViewById(R.id.fee_amount);
            Btn_View = (ImageButton) view.findViewById(R.id.btn_login_view);
            full_layout = (LinearLayout) view.findViewById(R.id.layout_rowitem);


        }
    }

    //**********************************************************************************************

}
