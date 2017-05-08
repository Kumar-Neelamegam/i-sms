package com.isms.kumar.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.isms.kumar.Core_Modules.FeeCollection_Activity;
import com.isms.kumar.Core_Modules.R;
import com.isms.kumar.Core_Modules.StudentDetails_Activity;

import java.util.ArrayList;

/**
 * Created by Kumar on 5/6/2017.
 */

public class DashboardAdapter  extends RecyclerView.Adapter<DashboardAdapter.ViewHolder>
{

    private ArrayList<Getter_Setter.Dashboard_Dataobjects> DataItems;
    private Context context;
    Fragment fragment = null;
    Class fragmentClass;
    String SESSION_DATABASENAME;

    //**********************************************************************************************

    public DashboardAdapter(Context context,ArrayList<Getter_Setter.Dashboard_Dataobjects> DataItems,String SESSION_DATABASENAME) {
        this.DataItems = DataItems;
        this.context = context;
        this.SESSION_DATABASENAME = SESSION_DATABASENAME;
    }
    //**********************************************************************************************

    @Override
    public DashboardAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dashboard_rowitem, viewGroup, false);
        return new ViewHolder(view);
    }
    //**********************************************************************************************

    @Override
    public void onBindViewHolder(DashboardAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.Title.setText(DataItems.get(i).getTitle_Name());
        viewHolder.ImageIcon.setImageDrawable(context.getResources().getDrawable(DataItems.get(i).getIcon()));

        viewHolder.Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle args=new Bundle();
                args.putString("SESSION_DB_NAME",SESSION_DATABASENAME);


                switch (DataItems.get(i).getTitle_Name())
                {
                    //***********************************************
                    case "Student Details":
 
                        fragmentClass=StudentDetails_Activity.class;
                        try
                        {
                            fragment = (Fragment) fragmentClass.newInstance();
                            fragment.setArguments(args);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // Insert the fragment by replacing any existing fragment
                        FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();



                        break;

                    //***********************************************

                    case "Fees Collection":
                        fragmentClass=FeeCollection_Activity.class;
                        try
                        {
                            fragment = (Fragment) fragmentClass.newInstance();
                            fragment.setArguments(args);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // Insert the fragment by replacing any existing fragment
                        FragmentManager fragmentManager1 = ((FragmentActivity)context).getSupportFragmentManager();
                        fragmentManager1.beginTransaction().replace(R.id.frame, fragment).commit();


                        break;
                    //***********************************************
                }




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
        private TextView Title;
        private ImageView ImageIcon;
        LinearLayout Layout;
        public ViewHolder(View view) {
            super(view);

            Title = (TextView)view.findViewById(R.id.title_txt);
            ImageIcon = (ImageView) view.findViewById(R.id.img_icon);
            Layout=(LinearLayout)view.findViewById(R.id.layout_item);

        }
    }

    //**********************************************************************************************

}
