package com.softcore.vtpsales.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.softcore.vtpsales.AppUtils.AppConstant;
import com.softcore.vtpsales.Model.BirthdayModel;
import com.softcore.vtpsales.Model.CommanResorce;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BirthdayListViewModel extends MainViewModel{

    MutableLiveData<CommanResorce<List<BirthdayModel>>> _data;

    public BirthdayListViewModel(@NonNull Application application) {
        super(application);
        _data=new MutableLiveData<>();

    }
    public LiveData<CommanResorce<List<BirthdayModel>>> getBirthdayListinfo(String DbName,String Flag){
        CommanResorce<List<BirthdayModel>> _new_data=new CommanResorce<>();
        _data=new MutableLiveData<>();
        repository.getBirthdayList(DbName,Flag, new Callback<List<BirthdayModel>>() {
            @Override
            public void onResponse(Call<List<BirthdayModel>> call, Response<List<BirthdayModel>> response) {
                if(response.isSuccessful()){

                    System.out.println("Birthday sucess"+response.body());
                    System.out.println("sucess"+response.code());
                    _new_data.data=response.body();
                    _new_data.message=response.message();
                    _new_data.status= AppConstant.status.SUCCESS;
                }
                else {

                    System.out.println("failed"+response.body());
                    System.out.println("failed"+response.code());
                    _new_data.data=new ArrayList<>();
                    _new_data.status= AppConstant.status.ERROR;
                    _new_data.message=response.message();
                }
                _data.postValue(_new_data);
            }

            @Override
            public void onFailure(Call<List<BirthdayModel>> call, Throwable t) {
                System.out.println("Failed "+t.getMessage());
                _new_data.data=new ArrayList<>();
                _new_data.status= AppConstant.status.ERROR;
                _new_data.message="failed Error";
                _data.postValue(_new_data);
//                Log.e("error",""+t.getMessage());
            }
        });

        return _data;

    }


}
