package com.softcore.vtpsales.Model;

import com.google.gson.annotations.SerializedName;

public class CusReportWiseModel {

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
    @SerializedName("BalanceDue")
    private String balanceDue;
    @SerializedName("Count")
    private String count;
    @SerializedName("Vendor_Name")
    private String vendor_Name;
    @SerializedName("Vendor_Code")
    private String vendor_Code;

    @SerializedName("Month")
    private String Month;
    //for Sales
    @SerializedName("Net Amount INV+CRN")
    private String NetAmtINV_CRN;
    @SerializedName("Gross Amount INV+CRN")
    private String GrossAmtINV_CRN;

    // for Sales and Customer Outstanding
    @SerializedName("Net sales Amount")
    private String NetSalesAmt;
    @SerializedName("Gross sales Amount")
    private String GrossSalesAmt;
    @SerializedName(" Net Credit Amount")
    private String NetCrdAmt;
    @SerializedName("Gross Crdit Amount")
    private String GrossCrditAmt;

    //for Customer Outstanding
    @SerializedName("Net Amount AR+CRN")
    private String NetAmtINV_ARCRN;
    @SerializedName("Gross Amount AR+CRN")
    private String GrossAmtINV_ARCRN;
    @SerializedName("Gross Credit Amount")
    private String GrossCrdAmt;

    //Purchase
    @SerializedName("Net Amount AP+CRN")
    private String NetAmtApCrn;
    @SerializedName("Gross Amount AP+CRN")
    private String GrossAmtApCrn;
    @SerializedName("Net Purchase Amount")
    private String NetPurchaseAmt;
    @SerializedName("Gross Purchase Amount")
    private String GrossPurchaseAmt;
    @SerializedName(" Net Debit Amount")
    private String NetDebitAmt;
    @SerializedName("Gross Debit Amount")
    private String GrossDebitAmt;



    public String getNetAmtApCrn() {
        return NetAmtApCrn;
    }

    public void setNetAmtApCrn(String netAmtApCrn) {
        NetAmtApCrn = netAmtApCrn;
    }

    public String getGrossAmtApCrn() {
        return GrossAmtApCrn;
    }

    public void setGrossAmtApCrn(String grossAmtApCrn) {
        GrossAmtApCrn = grossAmtApCrn;
    }

    public String getNetPurchaseAmt() {
        return NetPurchaseAmt;
    }

    public void setNetPurchaseAmt(String netPurchaseAmt) {
        NetPurchaseAmt = netPurchaseAmt;
    }

    public String getGrossPurchaseAmt() {
        return GrossPurchaseAmt;
    }

    public void setGrossPurchaseAmt(String grossPurchaseAmt) {
        GrossPurchaseAmt = grossPurchaseAmt;
    }

    public String getNetDebitAmt() {
        return NetDebitAmt;
    }

    public void setNetDebitAmt(String netDebitAmt) {
        NetDebitAmt = netDebitAmt;
    }

    public String getGrossDebitAmt() {
        return GrossDebitAmt;
    }

    public void setGrossDebitAmt(String grossDebitAmt) {
        GrossDebitAmt = grossDebitAmt;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public String getGrossCrditAmt() {
        return GrossCrditAmt;
    }

    public void setGrossCrditAmt(String grossCrditAmt) {
        GrossCrditAmt = grossCrditAmt;
    }

    public String getNetAmtINV_ARCRN() {
        return NetAmtINV_ARCRN;
    }

    public void setNetAmtINV_ARCRN(String netAmtINV_ARCRN) {
        NetAmtINV_ARCRN = netAmtINV_ARCRN;
    }

    public String getGrossAmtINV_ARCRN() {
        return GrossAmtINV_ARCRN;
    }

    public void setGrossAmtINV_ARCRN(String grossAmtINV_ARCRN) {
        GrossAmtINV_ARCRN = grossAmtINV_ARCRN;
    }

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

    public String getNetAmtINV_CRN() {
        return NetAmtINV_CRN;
    }

    public void setNetAmtINV_CRN(String netAmtINV_CRN) {
        NetAmtINV_CRN = netAmtINV_CRN;
    }

    public String getGrossAmtINV_CRN() {
        return GrossAmtINV_CRN;
    }

    public void setGrossAmtINV_CRN(String grossAmtINV_CRN) {
        GrossAmtINV_CRN = grossAmtINV_CRN;
    }

    public String getNetSalesAmt() {
        return NetSalesAmt;
    }

    public void setNetSalesAmt(String netSalesAmt) {
        NetSalesAmt = netSalesAmt;
    }

    public String getGrossSalesAmt() {
        return GrossSalesAmt;
    }

    public void setGrossSalesAmt(String grossSalesAmt) {
        GrossSalesAmt = grossSalesAmt;
    }

    public String getNetCrdAmt() {
        return NetCrdAmt;
    }

    public void setNetCrdAmt(String netCrdAmt) {
        NetCrdAmt = netCrdAmt;
    }

    public String getGrossCrdAmt() {
        return GrossCrdAmt;
    }

    public void setGrossCrdAmt(String grossCrdAmt) {
        GrossCrdAmt = grossCrdAmt;
    }
}
