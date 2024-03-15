package com.softcore.vtpsales.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.softcore.vtpsales.Model.CommanResorce;
import com.softcore.vtpsales.Model.Database;
import com.softcore.vtpsales.Model.UserModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatabaseListViewModel extends MainViewModel{

    MutableLiveData<CommanResorce<List<Database>>> _data;

    public DatabaseListViewModel(@NonNull Application application) {
        super(application);
        _data=new MutableLiveData<>();

    }
    public LiveData<CommanResorce<List<Database>>> getDatabaseList(){
        CommanResorce<List<Database>> _new_data=new CommanResorce<>();
        _data=new MutableLiveData<>();
        repository.getdatabaselist( new Callback<List<Database>>() {
            @Override
            public void onResponse(Call<List<Database>> call, Response<List<Database>> response) {
                if(response.isSuccessful()){

                    System.out.println("Database list success"+response.body());
                    System.out.println("success code"+response.code());
                    _new_data.data=response.body();
//                    _new_data.message="Seccess";
//                    _new_data.status= AppConst.status.SUCCESS;
                }
                else {

                    System.out.println("Database list failed"+response.body());
                    System.out.println("failed code"+response.code());
                    _new_data.data=new ArrayList<>();
//                    _new_data.status= AppConst.status.ERROR;
//                    _new_data.message="failer Error";
                }
                _data.postValue(_new_data);
            }

            @Override
            public void onFailure(Call<List<Database>> call, Throwable t) {
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
