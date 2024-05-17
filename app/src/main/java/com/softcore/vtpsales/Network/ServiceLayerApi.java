package com.softcore.vtpsales.Network;

import static com.softcore.vtpsales.AppUtils.AppConstant.status.ERROR;
import static com.softcore.vtpsales.AppUtils.AppConstant.status.SUCCESS;

import android.content.Intent;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.softcore.vtpsales.AppUtils.AppConstant;
import com.softcore.vtpsales.AppUtils.AppUtil;
import com.softcore.vtpsales.LeadGenerationActivity;
import com.softcore.vtpsales.MainActivity2;
import com.softcore.vtpsales.Model.CommonRes;
import com.softcore.vtpsales.Model.LeadGenModel;
import com.softcore.vtpsales.Model.SL_LoginRequest;
import com.softcore.vtpsales.ViewModel.SL_LeadViewModel;
import com.softcore.vtpsales.ViewModel.SL_LoginViewModel;
import com.softcore.vtpsales.ViewModel.SL_LogoutViewModel;

import java.util.List;

public class ServiceLayerApi {
    public interface ApiCallback {
        void onLoginSuccess();
        void onLoginFailure();
    }

    public static void doSapLogin(LeadGenerationActivity activity,ApiCallback callback) {

        SL_LoginViewModel slLoginViewModel = new ViewModelProvider(activity).get(SL_LoginViewModel.class);
        String dbName = AppUtil.getStringData(activity, "DatabaseName", "");

        SL_LoginRequest request = new SL_LoginRequest(dbName, AppConstant.Password, AppConstant.UserName);

        System.out.println("Service layer test dbName "+dbName+" "+AppConstant.Password+" "+AppConstant.UserName);
        slLoginViewModel.doLogin(request).observe(activity,
                new Observer<CommonRes<List<String>>>() {
                    @Override
                    public void onChanged(CommonRes<List<String>> listCommonRes) {

                        System.out.println("listCommonRes.code"+listCommonRes.code);

                        if(listCommonRes.code == 200){

                             callback.onLoginSuccess();

                        }
                        else{
                            if(listCommonRes.messages != null && listCommonRes.messages.equals("")){

                                try {
                                    // Assuming listCommonRes.messages is a JSON string
                                    String jsonString = listCommonRes.messages; // Assuming listCommonRes.messages is a valid JSON string
                                    Gson gson = new Gson();
                                    JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

                                    // Now you can access the fields of the JSON object
                                    String messageValue = jsonObject.getAsJsonObject("error")
                                            .getAsJsonObject("message")
                                            .get("value")
                                            .getAsString();

                                    Toast.makeText(activity, "Service Layer Error : " + messageValue, Toast.LENGTH_SHORT).show();

                                }
                                catch (JsonSyntaxException e) {
                                    System.out.println("Service Layer Error: " + e.getMessage());
                                }
                                callback.onLoginFailure();

                            }else{
                         //       Toast.makeText(activity, "Failed", Toast.LENGTH_SHORT).show();
                                callback.onLoginFailure();
                            }


                        }

                    }

                });

    }

    public static void doSapLogout(FragmentActivity activity, ApiCallback callback) {

        SL_LogoutViewModel slLogoutViewModel = new ViewModelProvider(activity).get(SL_LogoutViewModel.class);
        String dbName = AppUtil.getStringData(activity, "DatabaseName", "");

        SL_LoginRequest request = new SL_LoginRequest(dbName, AppConstant.Password, AppConstant.UserName);

        slLogoutViewModel.doLogout(request).observe(activity,
                new Observer<CommonRes<List<String>>>() {
                    @Override
                    public void onChanged(CommonRes<List<String>> listCommonRes) {
//                        try {
//                            //     alertDialog.dismiss();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//
//                        if (listCommonRes.data != null && listCommonRes.data.size() > 0) {
//                            try {
//
//                                Toast.makeText(activity, "Lead Successfully Generated", Toast.LENGTH_SHORT).show();
//
//                                System.out.println("New Session Id: "+listCommonRes.Session_ID);
//                                //  SaveSession(req_loginModel.UserName, req_loginModel.Password, req_loginModel.CompanyDB,listCommonRes.Session_ID);
//                                callback.onLoginSuccess();
//
//                                Intent intent = new Intent(activity, MainActivity2.class);
//                                activity.startActivity(intent);
//                                activity.finish();
//
//                            } catch (Exception e) {
//                                callback.onLoginFailure(); // Trigger getLead call
//                                e.printStackTrace();
//                            }
//
//                        } else {
//                            System.out.println("Failed");
//                            callback.onLoginFailure();
//                        }
                    }

                });

    }

    public static void doLeadCall(LeadGenerationActivity activity, LeadGenModel request, ApiCallback apiCallback) {

         SL_LeadViewModel slLeadViewModel = new ViewModelProvider(activity).get(SL_LeadViewModel.class);

        slLeadViewModel.getLead(request).observe(activity,
                new Observer<CommonRes<List<String>>>() {
                    @Override
                    public void onChanged(CommonRes<List<String>> listCommonRes) {

                        if (listCommonRes.data != null && listCommonRes.data.size() > 0) {
                            try {
                                System.out.println("Lead New Session Id: "+listCommonRes.Session_ID);
                                Toast.makeText(activity, "Lead Successfully Generated", Toast.LENGTH_SHORT).show();

                                apiCallback.onLoginSuccess();
                            } catch (Exception e) {
                                e.printStackTrace();
                                apiCallback.onLoginFailure();

                               // Toast.makeText(activity, "Failed: "+, Toast.LENGTH_SHORT).show();

                            }

                        } else {
//                            System.out.println(listCommonRes.messages);
//                            apiCallback.onLoginFailure();
//                            if(!listCommonRes.messages.isEmpty()){
//                                Toast.makeText(activity, "Failed: "+listCommonRes.messages, Toast.LENGTH_SHORT).show();
//                            }

                            if(listCommonRes.messages != null){
                                //    System.out.println("Failed :"+listCommonRes.messages.toString());
                                try {
                                    // Assuming listCommonRes.messages is a JSON string
                                    String jsonString = listCommonRes.messages; // Assuming listCommonRes.messages is a valid JSON string
                                    Gson gson = new Gson();
                                    JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

                                    // Now you can access the fields of the JSON object
                                    String messageValue = jsonObject.getAsJsonObject("error")
                                            .getAsJsonObject("message")
                                            .get("value")
                                            .getAsString();

                                    Toast.makeText(activity, "Failed : " + messageValue, Toast.LENGTH_SHORT).show();
                                    System.out.println("Failed : " + messageValue);
                                } catch (JsonSyntaxException e) {
                                    // Handle the case where the JSON string is not in the expected format
                                    System.out.println("Failed to parse JSON: " + e.getMessage());
                                    Toast.makeText(activity, "Failed to parse JSON: " + e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            }
                            apiCallback.onLoginFailure();
                        }
                    }

                });

    }


}
