package com.softcore.vtpsales.Network;


import com.google.gson.JsonObject;
import com.softcore.vtpsales.AppUtils.AppConstant;
import com.softcore.vtpsales.Model.ARInvoiceModel;
import com.softcore.vtpsales.Model.AttendanceModel;
import com.softcore.vtpsales.Model.BalanceDueResponse;
import com.softcore.vtpsales.Model.BirthdayModel;
import com.softcore.vtpsales.Model.ClockRequest;
import com.softcore.vtpsales.Model.CusClockRequest;
import com.softcore.vtpsales.Model.CusReportWiseDetModel;
import com.softcore.vtpsales.Model.CusReportWiseModel;
import com.softcore.vtpsales.Model.CustomerModel;
import com.softcore.vtpsales.Model.Database;
import com.softcore.vtpsales.Model.GeneralModel;
import com.softcore.vtpsales.Model.InOutPayModel;
import com.softcore.vtpsales.Model.LeadGenModel;
import com.softcore.vtpsales.Model.LeadSeriesModel;
import com.softcore.vtpsales.Model.MyTeamMember;
import com.softcore.vtpsales.Model.SL_LoginRequest;
import com.softcore.vtpsales.Model.SPModel;
import com.softcore.vtpsales.Model.SlpResponse;
import com.softcore.vtpsales.Model.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class RemoteRepository {

    public static RemoteRepository repository;

    public static RemoteRepository getRepository(){
        if(repository==null)
        {
            repository=new RemoteRepository();
        }
        return repository;
    }

//    public void doLogin(SL_LoginRequest request, Callback<JsonObject> callback) {
//        Call<JsonObject> call = NetworkController.getInstance().getService().doLogin(request);
//        call.enqueue(callback);
//    }
    public void doLogout(SL_LoginRequest request, Callback<JsonObject> callback) {
        Call<JsonObject> call = SL_NetworkController.getInstance(AppConstant.SLBase_Url).getService().doLogout(request);
        call.enqueue(callback);
    }

        public void doLogin(SL_LoginRequest req_loginModel, Callback<JsonObject> callback) {
        Call<JsonObject> call = SL_NetworkController.getInstance(AppConstant.SLBase_Url).getService().doLogin(req_loginModel);
        call.enqueue(callback);
    }

//    public void doLogout(Callback<Void> callback) {
//        Call<Void> call = NetworkController.getInstance().getService().doLogout();
//        call.enqueue(callback);
//    }

    public void doLead(LeadGenModel request, Callback<JsonObject> callback) {
        Call<JsonObject> call = SL_NetworkController.getInstance(AppConstant.SLBase_Url).getService().Lead(request);
        call.enqueue(callback);
    }

    public void getlogindetails(String DbName,String username,String password,Callback<List<UserModel>> callback){
        Call<List<UserModel>> call= NetworkController.getInstance(AppConstant.BASE_URL).getService().getLoginDetails(DbName,username,password);
        call.enqueue(callback);
    }
    public void getSlpPersons(String DbName,String EmpID,Callback<List<SPModel>> callback){
        Call<List<SPModel>> call= NetworkController.getInstance(AppConstant.BASE_URL).getService().GetSlpPersons(DbName,EmpID);
        call.enqueue(callback);

    }


    public void getdatabaselist(Callback<List<Database>> callback){
        Call<List<Database>> call= NetworkController.getInstance(AppConstant.BASE_URL).getService().getDatabases();
        call.enqueue(callback);
    }
    public void getLeadseries(String dbName, Callback<List<LeadSeriesModel>> callback){
        Call<List<LeadSeriesModel>> call= NetworkController.getInstance(AppConstant.BASE_URL).getService().getLeadSeries(dbName);
        call.enqueue(callback);
    }
    public void getStatelist(Callback<List<GeneralModel>> callback){
        Call<List<GeneralModel>> call= NetworkController.getInstance(AppConstant.BASE_URL).getService().getStates();
        call.enqueue(callback);
    }

    public void getCountrylist(Callback<List<GeneralModel>> callback){
        Call<List<GeneralModel>> call= NetworkController.getInstance(AppConstant.BASE_URL).getService().getCountries();
        call.enqueue(callback);
    }


    public void getBirthdayList(String DbName,String Flag,Callback<List<BirthdayModel>> callback){
        Call<List<BirthdayModel>> call= NetworkController.getInstance(AppConstant.BASE_URL).getService().getCustomers(DbName,Flag);
        call.enqueue(callback);
    }

    public void getAttendanceList(String DbName,String Flag,Callback<List<AttendanceModel>> callback){
        Call<List<AttendanceModel>> call= NetworkController.getInstance(AppConstant.BASE_URL).getService().getAttendanceDetails(DbName,Flag);
        call.enqueue(callback);
    }

    public void sendClockData(ClockRequest request, Callback<Void> callback) {
        Call<Void> call = NetworkController.getInstance(AppConstant.BASE_URL).getService().sendClockData(request);
        call.enqueue(callback);
    }
    public void sendCusClockData(CusClockRequest request, Callback<Void> callback) {
        Call<Void> call = NetworkController.getInstance(AppConstant.BASE_URL).getService().sendCusClockData(request);
        call.enqueue(callback);
    }

    public void getMyTeamsDetails(String DbName,String ExtNo,Callback<List<MyTeamMember>> callback){
        Call<List<MyTeamMember>> call= NetworkController.getInstance(AppConstant.BASE_URL).getService().getMyTeamMembers(DbName,ExtNo);
        call.enqueue(callback);
    }

//    public void getMyTeamsDetails(String DbName,String EmpNo,Callback<List<MyTeamModel>> callback){
//        Call<List<MyTeamModel>> call= NetworkController.getInstance(AppConstant.BASE_URL).getService().getMyTeamsData(DbName,EmpNo);
//        call.enqueue(callback);
//    }
    public void getSlpDetails(String DbName,String Flag,Callback<List<SlpResponse>> callback){
        Call<List<SlpResponse>> call= NetworkController.getInstance(AppConstant.BASE_URL).getService().getSlpData(DbName,Flag);
        System.out.println("dbName: "+DbName+"Flag: "+Flag);
        call.enqueue(callback);
    }
    public void getClpDetails(Callback<List<SlpResponse>> callback){
        Call<List<SlpResponse>> call= NetworkController.getInstance(AppConstant.BASE_URL).getService().getClpData();
        call.enqueue(callback);
    }
    public void getSEDetails(Callback<List<SlpResponse>> callback){
        Call<List<SlpResponse>> call= NetworkController.getInstance(AppConstant.BASE_URL).getService().getSEData();
        call.enqueue(callback);
    }
    public void getActCustomerdetails(String DbName,String Flag,Callback<List<CustomerModel>> callback){
        Call<List<CustomerModel>> call= NetworkController.getInstance(AppConstant.BASE_URL).getService().getActCustomer(DbName,Flag);
        call.enqueue(callback);
    }
    public void getBalaceDueDetails(String FromDate,String ToDate,String SlpName,String DbName,String Flag,Callback<List<BalanceDueResponse>> callback){
        Call<List<BalanceDueResponse>> call= NetworkController.getInstance(AppConstant.BASE_URL).getService().getDueBalance(FromDate,ToDate,SlpName,DbName,Flag);
        call.enqueue(callback);
    }

    public void getPurchaseDueDetails(String FromDate,String ToDate,String SlpName,String DbName,String Flag,Callback<List<BalanceDueResponse>> callback){
        Call<List<BalanceDueResponse>> call= NetworkController.getInstance(AppConstant.BASE_URL).getService().getPurchaseBalanceDue(FromDate,ToDate,SlpName,DbName,Flag);
        call.enqueue(callback);
    }

    public void getReceiptDueDetails(String FromDate,String ToDate,String SlpName,String DbName,String Flag,Callback<List<BalanceDueResponse>> callback){
        Call<List<BalanceDueResponse>> call= NetworkController.getInstance(AppConstant.BASE_URL).getService().getReceipt(FromDate,ToDate,SlpName,DbName,Flag);
        call.enqueue(callback);
    }

    public void getOutstandingDueDetails(String FromDate,String ToDate,String SlpName,String DbName,String Flag,Callback<List<BalanceDueResponse>> callback){
        Call<List<BalanceDueResponse>> call= NetworkController.getInstance(AppConstant.BASE_URL).getService().getOutstanding(FromDate,ToDate,SlpName,DbName,Flag);
        call.enqueue(callback);
    }

    public void getCusWiseReportDetails(String FromDate,String ToDate,String SlpName,String DbName,String Flag,Callback<List<CusReportWiseModel>> callback){
        Call<List<CusReportWiseModel>> call= NetworkController.getInstance(AppConstant.BASE_URL).getService().getCusWiseReport(FromDate,ToDate,SlpName,DbName,Flag);
        call.enqueue(callback);
    }
    public void getMonthWiseReportDetails(String FromDate,String ToDate,String SlpName,String DbName,String Flag,Callback<List<CusReportWiseModel>> callback){
        Call<List<CusReportWiseModel>> call= NetworkController.getInstance(AppConstant.BASE_URL).getService().getMonthsWiseReport(FromDate,ToDate,SlpName,DbName,Flag);
        call.enqueue(callback);
    }

    public void getCusWiseDet(String FromDate,String ToDate,String SlpName,String DbName,String Flag,Callback<List<CusReportWiseDetModel>> callback){
        Call<List<CusReportWiseDetModel>> call= NetworkController.getInstance(AppConstant.BASE_URL).getService().getCusWiseDetreport(FromDate,ToDate,SlpName,DbName,Flag);
        call.enqueue(callback);
    }

//getInOutWiseData
    public void getInOutWiseData(String FromDate,String ToDate,String SlpName,String DbName,String Flag,Callback<List<InOutPayModel>> callback){
        Call<List<InOutPayModel>> call= NetworkController.getInstance(AppConstant.BASE_URL).getService().getInOutWiseDetails(FromDate,ToDate,SlpName,DbName,Flag);
        call.enqueue(callback);
    }

    public void getArInvDet(String DbName,Callback<List<ARInvoiceModel>> callback){
        Call<List<ARInvoiceModel>> call= NetworkController.getInstance(AppConstant.BASE_URL).getService().getArInvoiceDetails(DbName);
        call.enqueue(callback);
    }
    public void getArCreditNoteInvDet(String DbName,Callback<List<ARInvoiceModel>> callback){
        Call<List<ARInvoiceModel>> call= NetworkController.getInstance(AppConstant.BASE_URL).getService().getArCreditNoteDetails(DbName);
        call.enqueue(callback);
    }

    public void getApInvDet(String DbName,Callback<List<ARInvoiceModel>> callback){
        Call<List<ARInvoiceModel>> call= NetworkController.getInstance(AppConstant.BASE_URL).getService().getApInvoiceDetails(DbName);
        call.enqueue(callback);
    }
    public void getApCreditNoteInvDet(String DbName,Callback<List<ARInvoiceModel>> callback){
        Call<List<ARInvoiceModel>> call= NetworkController.getInstance(AppConstant.BASE_URL).getService().getApCreditNoteDetails(DbName);
        call.enqueue(callback);
    }
}
