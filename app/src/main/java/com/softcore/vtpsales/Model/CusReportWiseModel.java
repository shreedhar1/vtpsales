package com.softcore.vtpsales.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class CusReportWiseModel implements Parcelable {

    @SerializedName("DocEntry")
    private String DocEntry;
    @SerializedName("DocNum")
    private String DocNum;
    @SerializedName("VTP_Doc_No")
    private String VTP_Doc_No;
    @SerializedName("Posting Date")
    private String PostingDate;
    @SerializedName("Ref_Document")
    private String Ref_Document;




    @SerializedName("SalesPerson")
    private String salesPerson;
    @SerializedName("CollectionPerson")
    private String collectionPerson;
    @SerializedName("Customer Code")
    private String customerCode;
    @SerializedName("Customer Name")
    private String customerName;

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
    @SerializedName("Vendor Name")
    private String vendorName;
    @SerializedName("Vendor Code")
    private String vendorCode;

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

    @SerializedName("Gross Amount")
    private String GrossAmt;
    @SerializedName("Net Amount")
    private String NetAmt;

    public CusReportWiseModel() {

    }
    public CusReportWiseModel(Parcel in) {
        salesPerson = in.readString();
        collectionPerson = in.readString();
        customerCode = in.readString();
        customerName = in.readString();
        customer_Code = in.readString();
        customer_Name = in.readString();
        balanceDue = in.readString();
        count = in.readString();
        vendor_Name = in.readString();
        vendor_Code = in.readString();
        Month = in.readString();
        NetAmtINV_CRN = in.readString();
        GrossAmtINV_CRN = in.readString();
        NetSalesAmt = in.readString();
        GrossSalesAmt = in.readString();
        NetCrdAmt = in.readString();
        GrossCrditAmt = in.readString();
        NetAmtINV_ARCRN = in.readString();
        GrossAmtINV_ARCRN = in.readString();
        GrossCrdAmt = in.readString();
        NetAmtApCrn = in.readString();
        GrossAmtApCrn = in.readString();
        NetPurchaseAmt = in.readString();
        GrossPurchaseAmt = in.readString();
        NetDebitAmt = in.readString();
        GrossDebitAmt = in.readString();
    }

    public static final Creator<CusReportWiseModel> CREATOR = new Creator<CusReportWiseModel>() {
        @Override
        public CusReportWiseModel createFromParcel(Parcel in) {
            return new CusReportWiseModel(in);
        }

        @Override
        public CusReportWiseModel[] newArray(int size) {
            return new CusReportWiseModel[size];
        }
    };

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(salesPerson);
        dest.writeString(collectionPerson);
        dest.writeString(customerCode);
        dest.writeString(customerName);
        dest.writeString(customer_Code);
        dest.writeString(customer_Name);
        dest.writeString(balanceDue);
        dest.writeString(count);
        dest.writeString(vendor_Name);
        dest.writeString(vendor_Code);
        dest.writeString(Month);
        dest.writeString(NetAmtINV_CRN);
        dest.writeString(GrossAmtINV_CRN);
        dest.writeString(NetSalesAmt);
        dest.writeString(GrossSalesAmt);
        dest.writeString(NetCrdAmt);
        dest.writeString(GrossCrditAmt);
        dest.writeString(NetAmtINV_ARCRN);
        dest.writeString(GrossAmtINV_ARCRN);
        dest.writeString(GrossCrdAmt);
        dest.writeString(NetAmtApCrn);
        dest.writeString(GrossAmtApCrn);
        dest.writeString(NetPurchaseAmt);
        dest.writeString(GrossPurchaseAmt);
        dest.writeString(NetDebitAmt);
        dest.writeString(GrossDebitAmt);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getSalesPerson() {
        return salesPerson;
    }

    public void setSalesPerson(String salesPerson) {
        this.salesPerson = salesPerson;
    }

    public String getCollectionPerson() {
        return collectionPerson;
    }

    public void setCollectionPerson(String collectionPerson) {
        this.collectionPerson = collectionPerson;
    }

    public String getDocEntry() {
        return DocEntry;
    }

    public void setDocEntry(String docEntry) {
        DocEntry = docEntry;
    }

    public String getDocNum() {
        return DocNum;
    }

    public void setDocNum(String docNum) {
        DocNum = docNum;
    }

    public String getRef_Document() {
        return Ref_Document;
    }

    public void setRef_Document(String ref_Document) {
        Ref_Document = ref_Document;
    }

    public String getVTP_Doc_No() {
        return VTP_Doc_No;
    }

    public void setVTP_Doc_No(String VTP_Doc_No) {
        this.VTP_Doc_No = VTP_Doc_No;
    }

    public String getPostingDate() {
        return PostingDate;
    }

    public void setPostingDate(String postingDate) {
        PostingDate = postingDate;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
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

    public String getGrossAmt() {
        return GrossAmt;
    }

    public void setGrossAmt(String grossAmt) {
        GrossAmt = grossAmt;
    }


    public String getNetAmt() {
        return NetAmt;
    }

    public void setNetAmt(String netAmt) {
        NetAmt = netAmt;
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
