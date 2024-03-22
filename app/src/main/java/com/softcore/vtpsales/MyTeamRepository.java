package com.softcore.vtpsales;
import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.softcore.vtpsales.Model.MyTeamMember;
import com.softcore.vtpsales.Network.ApiSercvices;
import com.softcore.vtpsales.Network.NetworkClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyTeamRepository {
    private ApiSercvices apiService;

    public MyTeamRepository() {
        apiService = NetworkClient.getRetrofitInstance().create(ApiSercvices.class);
    }

    public LiveData<List<MyTeamMember>> getMyTeamMembers(String dbName, String extEmpNo) {
        MutableLiveData<List<MyTeamMember>> data = new MutableLiveData<>();
        apiService.getMyTeamMembers(dbName, extEmpNo).enqueue(new Callback<List<MyTeamMember>>() {
            @Override
            public void onResponse(Call<List<MyTeamMember>> call, Response<List<MyTeamMember>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<MyTeamMember>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
