
package sample;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Table {

    @SerializedName("UserDetailsID")
    @Expose
    private Integer userDetailsID;
    @SerializedName("EmployeeName")
    @Expose
    private String employeeName;
    @SerializedName("EmailID")
    @Expose
    private String emailID;
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("InstitutionID")
    @Expose
    private String institutionID;
    @SerializedName("InstitutionName")
    @Expose
    private String institutionName;
    @SerializedName("DataBaseName")
    @Expose
    private String dataBaseName;
    @SerializedName("Address1")
    @Expose
    private String address1;
    @SerializedName("Address2")
    @Expose
    private String address2;
    @SerializedName("AreaCode")
    @Expose
    private String areaCode;
    @SerializedName("Pincode")
    @Expose
    private String pincode;
    @SerializedName("PhoneNo")
    @Expose
    private String phoneNo;
    @SerializedName("FaxNo")
    @Expose
    private String faxNo;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Website")
    @Expose
    private String website;

    public Integer getUserDetailsID() {
        return userDetailsID;
    }

    public void setUserDetailsID(Integer userDetailsID) {
        this.userDetailsID = userDetailsID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getInstitutionID() {
        return institutionID;
    }

    public void setInstitutionID(String institutionID) {
        this.institutionID = institutionID;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public String getDataBaseName() {
        return dataBaseName;
    }

    public void setDataBaseName(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getFaxNo() {
        return faxNo;
    }

    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

}
