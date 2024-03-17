package com.softcore.vtpsales.Model;

import com.google.gson.annotations.SerializedName;

public class CusReportWiseDetModel {

    @SerializedName("Sales Employee")
    private String salesEmployee;
    @SerializedName("Customer Code")
    private String customerCode;
    @SerializedName("Customer Name")
    private String customerName;
    @SerializedName("Sales_Employee")
    private String sales_Employee;
    @SerializedName("Customer_Code")
    private String customer_Code;
    @SerializedName("Customer_Name")
    private String customer_Name;
    @SerializedName("Doc_No")
    private String docNo;
    @SerializedName("Posting Date")
    private String postingDate;
    @SerializedName("BalanceDue")
    private String balanceDue;
    @SerializedName("Count")
    private String count;
    @SerializedName("Vendor_Name")
    private String vendor_Name;
    @SerializedName("Vendor_Code")
    private String vendor_Code;

    public String getSalesEmployee() {
        return salesEmployee;
    }

    public void setSalesEmployee(String salesEmployee) {
        this.salesEmployee = salesEmployee;
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

    public String getSales_Employee() {
        return sales_Employee;
    }

    public void setSales_Employee(String sales_Employee) {
        this.sales_Employee = sales_Employee;
    }

    public String getCustomer_Code() {
        return customer_Code;
    }

    public void setCustomer_Code(String customer_Code) {
        this.customer_Code = customer_Code;
    }

    public String getCustomer_Name() {
        return customer_Name;
    }

    public void setCustomer_Name(String customer_Name) {
        this.customer_Name = customer_Name;
    }

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public String getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }

    public String getBalanceDue() {
        return balanceDue;
    }

    public void setBalanceDue(String balanceDue) {
        this.balanceDue = balanceDue;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getVendor_Name() {
        return vendor_Name;
    }

    public void setVendor_Name(String vendor_Name) {
        this.vendor_Name = vendor_Name;
    }

    public String getVendor_Code() {
        return vendor_Code;
    }

    public void setVendor_Code(String vendor_Code) {
        this.vendor_Code = vendor_Code;
    }
}
