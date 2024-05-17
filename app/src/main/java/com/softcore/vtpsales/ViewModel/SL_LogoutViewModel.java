package com.softcore.vtpsales.ViewModel;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.softcore.vtpsales.Model.CommonRes;
import com.softcore.vtpsales.Model.SL_LoginRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SL_LogoutViewModel extends MasterViewModel{
    MutableLiveData<CommonRes<List<String>>> _data;
    public SL_LogoutViewModel(@NonNull Application application) {
        super(application);
        _data=new MutableLiveData<>();
    }

    public LiveData<CommonRes<List<String>>> doLogout(SL_LoginRequest req_loginModel){
        _data=new MutableLiveData<>();
        CommonRes<List<String>> _newData=new CommonRes<>();

        remoteRepository.doLogout(req_loginModel, new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()){
                    Toast.makeText(getApplication(), "Session Logout success", Toast.LENGTH_SHORT).show();
                    List<String> _list=new ArrayList<>();
                    try{
                        JSONObject jsonObject=new JSONObject(response.body().toString());
                        String SessionId = jsonObject.optString("SessionId");

                        _list.add(response.body().toString());
                            _newData.messages = response.message();
                            _newData.data =_list ;
                            //_newData.status = AppConstant.STATUS.SUCCESS;

                            _newData.Session_ID = SessionId;


                    }catch (Exception e){
                    //    _list.add(response.body().toString());
                        _newData.messages = "Error in parsing data.";
                        _newData.data =new ArrayList<>() ;
                        //_newData.status = AppConstant.STATUS.ERROR;
                    }
                }else {
                    _newData.messages=response.message()+"";
                    _newData.data=new ArrayList<>();
                  //  _newData.status= AppConstant.STATUS.ERROR;
                }
                _data.postValue(_newData);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                _newData.messages=t.getMessage()+"\n Unable to connect";
                _newData.data=new ArrayList<>();
          //      _newData.status= AppConstant.STATUS.ERROR;
                _data.postValue(_newData);
                t.printStackTrace();
            }
        });
        return _data;
    }


}
