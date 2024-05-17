package com.softcore.vtpsales.Network;

import static com.softcore.vtpsales.Network.UnsafeOkHttpClient.getUnsafeOkHttpClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//import view.utils.AppConstants;


public class SL_NetworkController {

    static ApiSercvices service;
    Retrofit retrofit;
    static SL_NetworkController networkController;


    private SL_NetworkController(String SLBase_Url) {
        if (retrofit == null) {
            // init cookie manager


            retrofit = new Retrofit.Builder().baseUrl(SLBase_Url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getUnsafeOkHttpClient()).build();
        }
        service = retrofit.create(ApiSercvices.class);

    }

    public static SL_NetworkController getInstance(String SLBase_Url) {
        if (networkController == null) {
            networkController = new SL_NetworkController(SLBase_Url);
        }
        return networkController;
    }

    public ApiSercvices getService() {
        return service;
    }



}
