package com.isms.kumar.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.isms.kumar.Core_Modules.R;

import java.util.ArrayList;

/**
 * Created by Kumar on 5/6/2017.
 */

public class FeeCollectionAdapter extends RecyclerView.Adapter<FeeCollectionAdapter.ViewHolder>
{

    private ArrayList<Getter_Setter.FeeCollection> DataItems;
    private Context context;
    Fragment fragment = null;
    Class fragmentClass;
    String SESSION_DATABASENAME;

    //**********************************************************************************************

    public FeeCollectionAdapter(Context context, ArrayList<Getter_Setter.FeeCollection> DataItems, String SESSION_DATABASENAME) {
        this.DataItems = DataItems;
        this.context = context;
        this.SESSION_DATABASENAME = SESSION_DATABASENAME;
    }
    //**********************************************************************************************

    @Override
    public FeeCollectionAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fee_row_item, viewGroup, false);
        return new ViewHolder(view);
    }
    //**********************************************************************************************

    @Override
    public void onBindViewHolder(FeeCollectionAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.FeeCategory.setText(DataItems.get(i).getFeeAmount());
        viewHolder.FeeAmount.setText(DataItems.get(i).getFeeAmount());

        viewHolder.Btn_View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





            }
        });


        viewHolder.Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle args=new Bundle();
                args.putString("SESSION_DB_NAME",SESSION_DATABASENAME);







            }
        });



    }
    //**********************************************************************************************

    @Override
    public int getItemCount() {
        return DataItems.size();
    }

    //**********************************************************************************************

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView FeeCategory;
        private TextView FeeAmount;
        private Button Btn_View;
        LinearLayout Layout;

        public ViewHolder(View view)
        {
            super(view);

            FeeCategory = (TextView)view.findViewById(R.id.fee_category);
            FeeAmount = (TextView) view.findViewById(R.id.fee_amount);
            Btn_View = (Button) view.findViewById(R.id.btn_login_view);
            Layout=(LinearLayout)view.findViewById(R.id.layout_item);

        }
    }

    //**********************************************************************************************

}
