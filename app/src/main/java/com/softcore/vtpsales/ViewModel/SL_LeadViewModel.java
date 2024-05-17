package com.softcore.vtpsales.ViewModel;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.softcore.vtpsales.Model.CommonRes;
import com.softcore.vtpsales.Model.LeadGenModel;
import com.softcore.vtpsales.Model.SL_LoginRequest;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SL_LeadViewModel extends MasterViewModel{
    MutableLiveData<CommonRes<List<String>>> _data;
    public SL_LeadViewModel(@NonNull Application application) {
        super(application);
        _data=new MutableLiveData<>();
    }

    public LiveData<CommonRes<List<String>>> getLead(LeadGenModel reqModel){
        _data=new MutableLiveData<>();
        CommonRes<List<String>> _newData=new CommonRes<>();

        remoteRepository.doLead(reqModel, new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()) {
                    List<String> _list = new ArrayList<>();

                    try {
                        JsonObject jsonObject = response.body();
                        if (jsonObject != null && jsonObject.has("error")) {
                            JsonObject errorObject = jsonObject.getAsJsonObject("error");
                            String message = errorObject.getAsJsonObject("message").get("value").getAsString();
                            System.out.println("Error new: " + message);
                            _newData.messages = message;
                        }else{
                            _newData.messages = "status code: " + response.code() + " " + response.message();
                        }

                        _list.add(response.body().toString());

                        _newData.data = _list;
                    }
                    catch (Exception e) {
                        _list.add(response.body().toString());
                        _newData.messages = "status code: " + response.code() + " " + response.message();
                        _newData.data = new ArrayList<>();
                    }

                } else {
                    try {
                        _newData.messages = response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    _newData.data = new ArrayList<>();
                }
                _data.postValue(_newData);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                System.out.println("error message" + t.getMessage());
                _newData.messages = t.getMessage() + "\n Unable to connect";
                _newData.data = new ArrayList<>();
                _data.postValue(_newData);
                t.printStackTrace();
            }
        });
        return _data;
    }


}
