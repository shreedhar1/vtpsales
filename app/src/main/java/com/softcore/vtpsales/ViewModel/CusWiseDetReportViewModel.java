package com.softcore.vtpsales.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.softcore.vtpsales.Model.CommanResorce;
import com.softcore.vtpsales.Model.CusReportWiseDetModel;
import com.softcore.vtpsales.Model.CusReportWiseDetModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CusWiseDetReportViewModel extends MainViewModel {

    MutableLiveData<CommanResorce<List<CusReportWiseDetModel>>> _data;

    public CusWiseDetReportViewModel(@NonNull Application application) {
        super(application);
        _data=new MutableLiveData<>();

    }
    public LiveData<CommanResorce<List<CusReportWiseDetModel>>> getCusWiseReportData(String FromDate, String ToDate, String SlpName, String DbName, String Flag){
        CommanResorce<List<CusReportWiseDetModel>> _new_data=new CommanResorce<>();
        _data=new MutableLiveData<>();

        repository.getCusWiseDet( FromDate, ToDate, SlpName, DbName, Flag, new Callback<List<CusReportWiseDetModel>>() {
            @Override
            public void onResponse(Call<List<CusReportWiseDetModel>> call, Response<List<CusReportWiseDetModel>> response) {
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
            public void onFailure(Call<List<CusReportWiseDetModel>> call, Throwable t) {
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
