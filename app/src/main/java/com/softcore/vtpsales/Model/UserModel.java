package com.softcore.vtpsales.Model;

public class UserModel {
    private String User_Password;
    private String firstName;
    private String lastName;
    private String EMP_Code;
    private String mobile;
    private String email;


    // Getters and setters

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_Password() {
        return User_Password;
    }

    public void setUser_Password(String user_Password) {
        User_Password = user_Password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEMP_Code() {
        return EMP_Code;
    }

    public void setEMP_Code(String EMP_Code) {
        this.EMP_Code = EMP_Code;
    }
}
