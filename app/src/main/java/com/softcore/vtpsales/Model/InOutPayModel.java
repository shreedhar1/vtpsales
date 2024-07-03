package com.softcore.vtpsales.Model;

public class InOutPayModel {
    String BPCode;
    String BPName;
    int SAP_Doc_No;
    String VTP_Doc_No;
    String PostingDate;
    String Reference;
    String SalesPerson;
    String CollectionPerson;
    double Payment_On_Account;
    double Total;
    double Amount;
    int Cheque_No;
    String Cheque_Date;
    String Mode_Of_Payment;
    String Remarks;
    String Inv_Doc_No;
    String VTPDocNo;

    public String getBPCode() {
        return BPCode;
    }

    public void setBPCode(String BPCode) {
        this.BPCode = BPCode;
    }

    public String getBPName() {
        return BPName;
    }

    public void setBPName(String BPName) {
        this.BPName = BPName;
    }

    public int getSAP_Doc_No() {
        return SAP_Doc_No;
    }

    public void setSAP_Doc_No(int SAP_Doc_No) {
        this.SAP_Doc_No = SAP_Doc_No;
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

    public String getReference() {
        return Reference;
    }

    public void setReference(String reference) {
        Reference = reference;
    }

    public String getSalesPerson() {
        return SalesPerson;
    }

    public void setSalesPerson(String salesPerson) {
        SalesPerson = salesPerson;
    }

    public String getCollectionPerson() {
        return CollectionPerson;
    }

    public void setCollectionPerson(String collectionPerson) {
        CollectionPerson = collectionPerson;
    }

    public double getPayment_On_Account() {
        return Payment_On_Account;
    }

    public void setPayment_On_Account(double payment_On_Account) {
        Payment_On_Account = payment_On_Account;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public int getCheque_No() {
        return Cheque_No;
    }

    public void setCheque_No(int cheque_No) {
        Cheque_No = cheque_No;
    }

    public String getCheque_Date() {
        return Cheque_Date;
    }

    public void setCheque_Date(String cheque_Date) {
        Cheque_Date = cheque_Date;
    }

    public String getMode_Of_Payment() {
        return Mode_Of_Payment;
    }

    public void setMode_Of_Payment(String mode_Of_Payment) {
        Mode_Of_Payment = mode_Of_Payment;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getInv_Doc_No() {
        return Inv_Doc_No;
    }

    public void setInv_Doc_No(String inv_Doc_No) {
        Inv_Doc_No = inv_Doc_No;
    }

    public String getVTPDocNo() {
        return VTPDocNo;
    }

    public void setVTPDocNo(String VTPDocNo) {
        this.VTPDocNo = VTPDocNo;
    }
}
