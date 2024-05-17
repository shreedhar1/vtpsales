package com.softcore.vtpsales.Model;

import com.google.gson.annotations.SerializedName;

public class MyTeamMember {
    @SerializedName("ExtEmpNo")
    private String extEmpNo;

    @SerializedName("name")
    private String name;

    @SerializedName("Role")
    private String role;

    @SerializedName("email")
    private String email;

    @SerializedName("mobile")
    private String mobile;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getExtEmpNo() {
        return extEmpNo;
    }

    public void setExtEmpNo(String extEmpNo) {
        this.extEmpNo = extEmpNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
