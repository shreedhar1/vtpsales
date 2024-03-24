package com.softcore.vtpsales.Network;

import static com.softcore.vtpsales.AppUtils.AppConstant.SLBase_Url;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {
 //   private static final String BASE_URL = "http://103.96.42.106:7279/api/sap/";

    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(SLBase_Url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
