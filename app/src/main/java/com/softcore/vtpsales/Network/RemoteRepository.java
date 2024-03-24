package com.softcore.vtpsales.Network;


import com.softcore.vtpsales.Model.AttendanceModel;
import com.softcore.vtpsales.Model.BalanceDueResponse;
import com.softcore.vtpsales.Model.BirthdayModel;
import com.softcore.vtpsales.Model.ClockRequest;
import com.softcore.vtpsales.Model.CusClockRequest;
import com.softcore.vtpsales.Model.CusReportWiseDetModel;
import com.softcore.vtpsales.Model.CusReportWiseModel;
import com.softcore.vtpsales.Model.CustomerModel;
import com.softcore.vtpsales.Model.Database;
import com.softcore.vtpsales.Model.MyTeamMember;
import com.softcore.vtpsales.Model.MyTeamModel;
import com.softcore.vtpsales.Model.SL_LoginRequest;
import com.softcore.vtpsales.Model.SL_LoginResponse;
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


    public void getlogindetails(String DbName,String username,String password,Callback<List<UserModel>> callback){
        Call<List<UserModel>> call= Network.getNetwork().getservices().getLoginDetails(DbName,username,password);
        call.enqueue(callback);

    }

    public void getdatabaselist(Callback<List<Database>> callback){
        Call<List<Database>> call= Network.getNetwork().getservices().getDatabases();
        call.enqueue(callback);
    }

    public void getBirthdayList(String DbName,String Flag,Callback<List<BirthdayModel>> callback){
        Call<List<BirthdayModel>> call= Network.getNetwork().getservices().getCustomers(DbName,Flag);
        call.enqueue(callback);
    }

    public void getAttendanceList(String DbName,String Flag,Callback<List<AttendanceModel>> callback){
        Call<List<AttendanceModel>> call= Network.getNetwork().getservices().getAttendanceDetails(DbName,Flag);
        call.enqueue(callback);
    }

    public void sendClockData(ClockRequest request, Callback<Void> callback) {
        Call<Void> call = Network.getNetwork().getservices().sendClockData(request);
        call.enqueue(callback);
    }
    public void sendCusClockData(CusClockRequest request, Callback<Void> callback) {
        Call<Void> call = Network.getNetwork().getservices().sendCusClockData(request);
        call.enqueue(callback);
    }

    public void getMyTeamsDetails(String DbName,String ExtNo,Callback<List<MyTeamMember>> callback){
        Call<List<MyTeamMember>> call= Network.getNetwork().getservices().getMyTeamMembers(DbName,ExtNo);
        call.enqueue(callback);
    }

//    public void getMyTeamsDetails(String DbName,String EmpNo,Callback<List<MyTeamModel>> callback){
//        Call<List<MyTeamModel>> call= Network.getNetwork().getservices().getMyTeamsData(DbName,EmpNo);
//        call.enqueue(callback);
//    }
    public void getSlpDetails(String DbName,String Flag,Callback<List<SlpResponse>> callback){
        Call<List<SlpResponse>> call= Network.getNetwork().getservices().getSlpData(DbName,Flag);
        System.out.println("dbName: "+DbName+"Flag: "+Flag);
        call.enqueue(callback);
    }
    public void getClpDetails(Callback<List<SlpResponse>> callback){
        Call<List<SlpResponse>> call= Network.getNetwork().getservices().getClpData();
        call.enqueue(callback);
    }
    public void getSEDetails(Callback<List<SlpResponse>> callback){
        Call<List<SlpResponse>> call= Network.getNetwork().getservices().getSEData();
        call.enqueue(callback);
    }
    public void getActCustomerdetails(String DbName,String Flag,Callback<List<CustomerModel>> callback){
        Call<List<CustomerModel>> call= Network.getNetwork().getservices().getActCustomer(DbName,Flag);
        call.enqueue(callback);
    }
    public void getBalaceDueDetails(String FromDate,String ToDate,String SlpName,String DbName,String Flag,Callback<List<BalanceDueResponse>> callback){
        Call<List<BalanceDueResponse>> call= Network.getNetwork().getservices().getDueBalance(FromDate,ToDate,SlpName,DbName,Flag);
        call.enqueue(callback);
    }

    public void getPurchaseDueDetails(String FromDate,String ToDate,String SlpName,String DbName,String Flag,Callback<List<BalanceDueResponse>> callback){
        Call<List<BalanceDueResponse>> call= Network.getNetwork().getservices().getPurchaseBalanceDue(FromDate,ToDate,SlpName,DbName,Flag);
        call.enqueue(callback);
    }

    public void getReceiptDueDetails(String FromDate,String ToDate,String SlpName,String DbName,String Flag,Callback<List<BalanceDueResponse>> callback){
        Call<List<BalanceDueResponse>> call= Network.getNetwork().getservices().getReceipt(FromDate,ToDate,SlpName,DbName,Flag);
        call.enqueue(callback);
    }

    public void getOutstandingDueDetails(String FromDate,String ToDate,String SlpName,String DbName,String Flag,Callback<List<BalanceDueResponse>> callback){
        Call<List<BalanceDueResponse>> call= Network.getNetwork().getservices().getOutstanding(FromDate,ToDate,SlpName,DbName,Flag);
        call.enqueue(callback);
    }

    public void getCusWiseReportDetails(String FromDate,String ToDate,String SlpName,String DbName,String Flag,Callback<List<CusReportWiseModel>> callback){
        Call<List<CusReportWiseModel>> call= Network.getNetwork().getservices().getCusWiseReport(FromDate,ToDate,SlpName,DbName,Flag);
        call.enqueue(callback);
    }

    public void getCusWiseDet(String FromDate,String ToDate,String SlpName,String DbName,String Flag,Callback<List<CusReportWiseDetModel>> callback){
        Call<List<CusReportWiseDetModel>> call= Network.getNetwork().getservices().getCusWiseDetreport(FromDate,ToDate,SlpName,DbName,Flag);
        call.enqueue(callback);
    }
}
