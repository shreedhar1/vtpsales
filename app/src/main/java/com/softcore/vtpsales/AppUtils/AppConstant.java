package com.softcore.vtpsales.AppUtils;

//public class AppConstant {
//
//    //Live
//    public static final String BASE_URL = "http://103.96.42.106:7279/api/sap/"; //live
//    public static final String COMPANY_DB_USERNAME = "V12943"; //live
//    public static final String COMPANY_DB_PASSWORD = "12345"; //live
//    public static final String COMPANY_DB_NAME = "ENVIIRO_LIVE"; //live
//
//    public static final String LoginUrl = BASE_URL+"emp_login?";
//
//    //emp_login?DB_NAME=ENVIIRO_LIVE&UserName=V12943&UserPassword=12345
//
//}

import com.softcore.vtpsales.Model.SL_LoginRequest;

public class AppConstant {
    public static final String BASE_URL = "http://103.96.42.106:7279/API/SAP/";
    public static final String LOGIN_URL = BASE_URL + "emp_login";

    public static final String SLBase_Url = "https://103.96.42.106:50000/b1s/v1/";



    public enum status {SUCCESS,ERROR};

    public static String CompanyDB = "TEST_ENV_20231124";
    public static String Password = "Soft@123";
    public static String UserName = "manager";





}