package com.softcore.vtpsales.Model;

import com.google.gson.annotations.SerializedName;

public class BirthdayModel {
    @SerializedName("Type")
    private String type;

    @SerializedName("Customer_Code")
    private String customerCode;

    @SerializedName("Customer_Name")
    private String customerName;

    @SerializedName("Birth_Date")
    private String birthDate;

    @SerializedName("Anniversary")
    private String anniversary;

    @SerializedName("Flag")
    private String flag;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAnniversary() {
        return anniversary;
    }

    public void setAnniversary(String anniversary) {
        this.anniversary = anniversary;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }


    // Getter and setter methods
}
