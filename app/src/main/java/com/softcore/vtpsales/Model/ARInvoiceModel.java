package com.softcore.vtpsales.Model;

import com.google.gson.annotations.SerializedName;

public class ARInvoiceModel {
    @SerializedName("DocEntry")
    int docEntry;
    @SerializedName("DocNum")
    int docNum;
    @SerializedName("CardName")
    String cardName;
    @SerializedName("Posting Date")
    String postingDate;
    @SerializedName("Due Date")
    String dueDate;
    @SerializedName("Customer Ref No.")
    String customerRefNo;
    @SerializedName("Tax Date")
    String taxDate;
    @SerializedName("Status")
    String status;
    @SerializedName("Item Description")
    String itemDescription;
    @SerializedName("Quantity")
    double quantity;
    @SerializedName("Rate")
    double rate;
    @SerializedName("HSN/SAC")
    String hsnSac;
    @SerializedName("Tax Rate")
    double taxRate;
    @SerializedName("Net Amount")
    double netAmount;
    @SerializedName("CGST Amount")
    double cgstAmount;
    @SerializedName("SGST Amount")
    double sgstAmount;
    @SerializedName("IGST Amount")
    double igstAmount;
    @SerializedName("Gross Amount")
    double grossAmount;
    @SerializedName("Remarks")
    String remarks;
    @SerializedName("LR No")
    String lrNo;
    @SerializedName("LR Date")
    String lrDate;
    @SerializedName("VehicleNo")
    String vehicleNo;
    @SerializedName("Final Destination")
    String finalDestination;

    public int getDocEntry() {
        return docEntry;
    }

    public void setDocEntry(int docEntry) {
        this.docEntry = docEntry;
    }

    public int getDocNum() {
        return docNum;
    }

    public void setDocNum(int docNum) {
        this.docNum = docNum;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getCustomerRefNo() {
        return customerRefNo;
    }

    public void setCustomerRefNo(String customerRefNo) {
        this.customerRefNo = customerRefNo;
    }

    public String getTaxDate() {
        return taxDate;
    }

    public void setTaxDate(String taxDate) {
        this.taxDate = taxDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getHsnSac() {
        return hsnSac;
    }

    public void setHsnSac(String hsnSac) {
        this.hsnSac = hsnSac;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(double netAmount) {
        this.netAmount = netAmount;
    }

    public double getCgstAmount() {
        return cgstAmount;
    }

    public void setCgstAmount(double cgstAmount) {
        this.cgstAmount = cgstAmount;
    }

    public double getSgstAmount() {
        return sgstAmount;
    }

    public void setSgstAmount(double sgstAmount) {
        this.sgstAmount = sgstAmount;
    }

    public double getIgstAmount() {
        return igstAmount;
    }

    public void setIgstAmount(double igstAmount) {
        this.igstAmount = igstAmount;
    }

    public double getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(double grossAmount) {
        this.grossAmount = grossAmount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getLrNo() {
        return lrNo;
    }

    public void setLrNo(String lrNo) {
        this.lrNo = lrNo;
    }

    public String getLrDate() {
        return lrDate;
    }

    public void setLrDate(String lrDate) {
        this.lrDate = lrDate;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getFinalDestination() {
        return finalDestination;
    }

    public void setFinalDestination(String finalDestination) {
        this.finalDestination = finalDestination;
    }

}
