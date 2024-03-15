package com.softcore.vtpsales.Network;


import com.softcore.vtpsales.Model.AttendanceModel;
import com.softcore.vtpsales.Model.BirthdayModel;
import com.softcore.vtpsales.Model.ClockRequest;
import com.softcore.vtpsales.Model.Database;
import com.softcore.vtpsales.Model.MyTeamModel;
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

    public void getMyTeamsDetails(String DbName,String EmpNo,Callback<List<MyTeamModel>> callback){
        Call<List<MyTeamModel>> call= Network.getNetwork().getservices().getMyTeamsData(DbName,EmpNo);
        call.enqueue(callback);

    }
}
