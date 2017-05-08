package com.isms.kumar.Adapters;

/**
 * Created by Kumar on 5/6/2017.
 */

public class Getter_Setter {

    //**********************************************************************************************

    public static class Dashboard_Dataobjects {

        public String Title_Name;
        int Icon;

        public String getTitle_Name() {
            return Title_Name;
        }

        public void setTitle_Name(String title_Name) {
            Title_Name = title_Name;
        }

        public int getIcon() {
            return Icon;
        }

        public void setIcon(int icon) {
            Icon = icon;
        }


    }

    //**********************************************************************************************

    public static class FeeCollection{

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getFeeCategory() {
            return FeeCategory;
        }

        public void setFeeCategory(String feeCategory) {
            FeeCategory = feeCategory;
        }

        public String getFeeAmount() {
            return FeeAmount;
        }

        public void setFeeAmount(String feeAmount) {
            FeeAmount = feeAmount;
        }

        public String Id;
        public String FeeCategory;
        public String FeeAmount;



    }



    //**********************************************************************************************


}
