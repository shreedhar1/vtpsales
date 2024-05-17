package com.softcore.vtpsales.ViewModel;

import static com.softcore.vtpsales.AppUtils.AppConstant.status.SUCCESS;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.softcore.vtpsales.AppUtils.AppConstant;
import com.softcore.vtpsales.Model.CommonRes;
import com.softcore.vtpsales.Model.SL_LoginRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SL_LoginViewModel extends MasterViewModel {
    MutableLiveData<CommonRes<List<String>>> _dataLiveData;

    public SL_LoginViewModel(@NonNull Application application) {
        super(application);
        _dataLiveData = new MutableLiveData<>();
    }

    public LiveData<CommonRes<List<String>>> doLogin(SL_LoginRequest req_loginModel) {
        CommonRes<List<String>> _newData = new CommonRes<>();
        remoteRepository.doLogin(req_loginModel, new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if(response.isSuccessful()){
                    if(response.code() == 200){

                     //   _newData.status = SUCCESS;
                        _newData.code = response.code();
                        _newData.messages = response.message();
                        _newData.data = new ArrayList<>();
                        _dataLiveData.postValue(_newData);
                        Toast.makeText(getApplication(), "Session Refreshed", Toast.LENGTH_SHORT).show();
                    }else {
                       // _newData.status = AppConstant.status.ERROR;

                        _newData.code = response.code();
                        _newData.messages = response.message();
                        _newData.data = new ArrayList<>();
                        _dataLiveData.postValue(_newData);
                        Toast.makeText(getApplication(), "Message "+response.message(), Toast.LENGTH_SHORT).show();

                        Toast.makeText(getApplication(), "Response Body "+response.body(), Toast.LENGTH_SHORT).show();

                        Toast.makeText(getApplication(), "Status Code"+response.code(), Toast.LENGTH_SHORT).show();
                    }


                }











            }


            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                System.out.println("error message" + t.getMessage());
                Toast.makeText(getApplication(), t.getMessage(), Toast.LENGTH_SHORT).show();
                _newData.messages = t.getMessage() + "\n Unable to connect";
                _newData.data = new ArrayList<>();
                _newData.code = 0;
                _dataLiveData.postValue(_newData);
                t.printStackTrace();
            }
        });

        return _dataLiveData;
    }
}
