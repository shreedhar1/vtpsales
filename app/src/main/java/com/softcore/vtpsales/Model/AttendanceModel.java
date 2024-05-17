package com.softcore.vtpsales.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AttendanceModel implements Parcelable {

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
    @SerializedName("LocationIn")
    private String LocationIn;
    @SerializedName("LocationOut")
    private String LocationOut;

    public AttendanceModel() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    protected AttendanceModel(Parcel in) {
        empCode = in.readString();
        empName = in.readString();
        CustomerCode = in.readString();
        date = in.readString();
        checkIn = in.readString();
        checkOut = in.readString();
        LocationIn = in.readString();
        LocationOut = in.readString();
    }

    public static final Creator<AttendanceModel> CREATOR = new Creator<AttendanceModel>() {
        @Override
        public AttendanceModel createFromParcel(Parcel in) {
            return new AttendanceModel(in);
        }

        @Override
        public AttendanceModel[] newArray(int size) {
            return new AttendanceModel[size];
        }
    };

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

    public String getLocationIn() {
        return LocationIn;
    }

    public void setLocationIn(String locationIn) {
        LocationIn = locationIn;
    }

    public String getLocationOut() {
        return LocationOut;
    }

    public void setLocationOut(String locationOut) {
        LocationOut = locationOut;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(empCode);
        dest.writeString(empName);
        dest.writeString(CustomerCode);
        dest.writeString(date);
        dest.writeString(checkIn);
        dest.writeString(checkOut);
        dest.writeString(LocationIn);
        dest.writeString(LocationOut);
    }
}
