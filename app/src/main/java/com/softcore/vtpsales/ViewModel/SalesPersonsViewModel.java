package com.softcore.vtpsales.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.softcore.vtpsales.Model.CommanResorce;
import com.softcore.vtpsales.Model.SPModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalesPersonsViewModel extends MainViewModel{

    MutableLiveData<CommanResorce<List<SPModel>>> _data;

    public SalesPersonsViewModel(@NonNull Application application) {
        super(application);
        _data=new MutableLiveData<>();

    }
    public LiveData<CommanResorce<List<SPModel>>> getSalesPersonInfo(String DbName,String EmpId){
        CommanResorce<List<SPModel>> _new_data=new CommanResorce<>();
        _data=new MutableLiveData<>();
        repository.getSlpPersons(DbName,EmpId, new Callback<List<SPModel>>() {
            @Override
            public void onResponse(Call<List<SPModel>> call, Response<List<SPModel>> response) {
                if(response.isSuccessful()){

                    System.out.println("sucess"+response.body());
                    System.out.println("sucess"+response.code());
                    _new_data.data=response.body();
//                    _new_data.message="Seccess";
//                    _new_data.status= AppConst.status.SUCCESS;
                }
                else {

                    System.out.println("failed"+response.body());
                    System.out.println("failed"+response.code());
                    _new_data.data=new ArrayList<>();
//                    _new_data.status= AppConst.status.ERROR;
//                    _new_data.message="failer Error";
                }
                _data.postValue(_new_data);
            }

            @Override
            public void onFailure(Call<List<SPModel>> call, Throwable t) {
                System.out.println("Failed "+t.getMessage());
                _new_data.data=new ArrayList<>();
//                _new_data.status= AppConst.status.ERROR;
//                _new_data.message="failer Error";
                _data.postValue(_new_data);
//                Log.e("error",""+t.getMessage());
            }
        });

        return _data;

    }


}
