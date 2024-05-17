package com.softcore.vtpsales.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.softcore.vtpsales.Model.CommanResorce;
import com.softcore.vtpsales.Model.ARInvoiceModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ARInvoiceViewModel extends MainViewModel {

    MutableLiveData<CommanResorce<List<ARInvoiceModel>>> _data;

    public ARInvoiceViewModel(@NonNull Application application) {
        super(application);
        _data=new MutableLiveData<>();

    }
    public LiveData<CommanResorce<List<ARInvoiceModel>>> getArInvoiceData(String DbName){
        CommanResorce<List<ARInvoiceModel>> _new_data=new CommanResorce<>();
        _data=new MutableLiveData<>();

        repository.getArInvDet(DbName, new Callback<List<ARInvoiceModel>>() {
            @Override
            public void onResponse(Call<List<ARInvoiceModel>> call, Response<List<ARInvoiceModel>> response) {
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
            public void onFailure(Call<List<ARInvoiceModel>> call, Throwable t) {
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
