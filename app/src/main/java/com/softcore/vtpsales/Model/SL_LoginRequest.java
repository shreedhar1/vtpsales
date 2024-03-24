package com.softcore.vtpsales.Model;


public class SL_LoginRequest {
    private String CompanyDB;
    private String Password;
    private String UserName;

    public SL_LoginRequest(String companyDB, String password, String userName) {
        CompanyDB = companyDB;
        Password = password;
        UserName = userName;
    }


}
