package com.softcore.vtpsales.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.softcore.vtpsales.Model.CommanResorce;
import com.softcore.vtpsales.Model.LeadSeriesModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeadSeriesViewModel extends MainViewModel{

    MutableLiveData<CommanResorce<List<LeadSeriesModel>>> _data;

    public LeadSeriesViewModel(@NonNull Application application) {
        super(application);
        _data=new MutableLiveData<>();

    }
    public LiveData<CommanResorce<List<LeadSeriesModel>>> getLeadSeries(String dbName){
        CommanResorce<List<LeadSeriesModel>> _new_data=new CommanResorce<>();
        _data=new MutableLiveData<>();
        repository.getLeadseries(dbName, new Callback<List<LeadSeriesModel>>() {
            @Override
            public void onResponse(Call<List<LeadSeriesModel>> call, Response<List<LeadSeriesModel>> response) {
                if(response.isSuccessful()){

                    System.out.println("GeneralModel list success"+response.body());
                    System.out.println("success code"+response.code());
                    _new_data.data=response.body();
//                    _new_data.message="Seccess";
//                    _new_data.status= AppConst.status.SUCCESS;
                }
                else {

                    System.out.println("GeneralModel list failed"+response.body());
                    System.out.println("failed code"+response.code());
                    _new_data.data=new ArrayList<>();
//                    _new_data.status= AppConst.status.ERROR;
                    _new_data.message="Failed Loading GeneralModel list";
                }
                _data.postValue(_new_data);
            }

            @Override
            public void onFailure(Call<List<LeadSeriesModel>> call, Throwable t) {
                System.out.println("Failed "+t.getMessage());
                _new_data.data=new ArrayList<>();
//                _new_data.status= AppConst.status.ERROR;
                _new_data.message=t.getMessage();
                _data.postValue(_new_data);
//                Log.e("error",""+t.getMessage());
            }
        });

        return _data;

    }


}
