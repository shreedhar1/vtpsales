package com.softcore.vtpsales.Network;

import static com.softcore.vtpsales.Network.UnsafeOkHttpClient.getUnsafeOkHttpClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//import view.utils.AppConstants;


public class NetworkController {

    static ApiSercvices service;
    Retrofit retrofit;
    static NetworkController networkController;


    private NetworkController(String Base_Url) {
        if (retrofit == null) {
            // init cookie manager


            retrofit = new Retrofit.Builder().baseUrl(Base_Url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getUnsafeOkHttpClient()).build();
        }
        service = retrofit.create(ApiSercvices.class);

    }

    public static NetworkController getInstance(String Base_Url) {
        if (networkController == null) {
            networkController = new NetworkController(Base_Url);
        }
        return networkController;
    }

    public ApiSercvices getService() {
        return service;
    }



}
