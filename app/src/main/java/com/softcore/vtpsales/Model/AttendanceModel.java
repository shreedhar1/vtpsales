package com.softcore.vtpsales.Model;

import com.google.gson.annotations.SerializedName;

public class AttendanceModel {

    @SerializedName("Employee_Code")
    private String empCode;
    @SerializedName("Sales_Employee")
    private String empName;
    @SerializedName("Customer_Code")
    private String CustomerCode;
    @SerializedName("date")
    private String date;
    @SerializedName("Check In")
    private String checkIn;
    @SerializedName("Check out")
    private String checkOut;

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getCustomerCode() {
        return CustomerCode;
    }

    public void setCustomerCode(String customerCode) {
        CustomerCode = customerCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }
}
