package com.softcore.vtpsales.Model;

import com.google.gson.annotations.SerializedName;

public class BalanceDueResponse {
    @SerializedName("BalanceDue")
    private String BalanceDue;
    @SerializedName("Net Amount AR+CRN")
    private String NetAmtArCrn;
    @SerializedName("Gross Amount AR+CRN")
    private String GrossAmtArCrn;
    @SerializedName(" Net Sale Amount")
    private String NetSalesAmt;
    @SerializedName("Gross sales Amount")
    private String GrossSalesAmt;
    @SerializedName(" Net Credit Amount")
    private String NetCreditAmt;
    @SerializedName("Gross Crdit Amount")
    private String GrossCreditAmt;

    @SerializedName("Net Amount AP+CRN")
    private String NetAmtApCrn;
    @SerializedName("Gross Amount AP+CRN")
    private String GrossAmtApCrn;
    @SerializedName(" Net Purchase Amount")
    private String NetPurchaseAmt;
    @SerializedName("Gross Purchase Amount")
    private String GrossPurchaseAmt;
    @SerializedName(" Net Debit Amount")
    private String NetDebitAmt;
    @SerializedName("Gross Debit Amount")
    private String GrossDebitAmt;


    public String getNetAmtArCrn() {
        return NetAmtArCrn;
    }

    public void setNetAmtArCrn(String netAmtArCrn) {
        NetAmtArCrn = netAmtArCrn;
    }

    public String getGrossAmtArCrn() {
        return GrossAmtArCrn;
    }

    public void setGrossAmtArCrn(String grossAmtArCrn) {
        GrossAmtArCrn = grossAmtArCrn;
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

    public String getNetCreditAmt() {
        return NetCreditAmt;
    }

    public void setNetCreditAmt(String netCreditAmt) {
        NetCreditAmt = netCreditAmt;
    }

    public String getGrossCreditAmt() {
        return GrossCreditAmt;
    }

    public void setGrossCreditAmt(String grossCreditAmt) {
        GrossCreditAmt = grossCreditAmt;
    }

    public String getBalanceDue() {
        return BalanceDue;
    }

    public void setBalanceDue(String balanceDue) {
        BalanceDue = balanceDue;
    }


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
}
