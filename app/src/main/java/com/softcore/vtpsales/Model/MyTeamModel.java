package com.softcore.vtpsales.Model;

import com.google.gson.annotations.SerializedName;

public class MyTeamModel {

    @SerializedName("ExtEmpNo")
    private String EmpNo;

    @SerializedName("name")
    private String Name;

    @SerializedName("Role")
    private String Role;

    public String getEmpNo() {
        return EmpNo;
    }

    public void setEmpNo(String empNo) {
        EmpNo = empNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }
}
