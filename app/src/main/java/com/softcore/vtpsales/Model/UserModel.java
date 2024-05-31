package com.softcore.vtpsales.Model;

public class UserModel {
    private String User_Password;
    private String firstName;
    private String lastName;
    private String EMP_Code;
    private String mobile;
    private String email;
    private String empID;
    private String Type;
    private String Sales_Employee;
    private String CompnyName;
    private String CompnyAddr;
    private String Building;
    private String IntrntAdrs;
    private String Phone1;
    private String E_Mail;

    public String getSales_Employee() {
        return Sales_Employee;
    }

    public void setSales_Employee(String sales_Employee) {
        Sales_Employee = sales_Employee;
    }


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

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getCompnyName() {
        return CompnyName;
    }

    public void setCompnyName(String compnyName) {
        CompnyName = compnyName;
    }

    public String getCompnyAddr() {
        return CompnyAddr;
    }

    public void setCompnyAddr(String compnyAddr) {
        CompnyAddr = compnyAddr;
    }

    public String getBuilding() {
        return Building;
    }

    public void setBuilding(String building) {
        Building = building;
    }

    public String getIntrntAdrs() {
        return IntrntAdrs;
    }

    public void setIntrntAdrs(String intrntAdrs) {
        IntrntAdrs = intrntAdrs;
    }

    public String getPhone1() {
        return Phone1;
    }

    public void setPhone1(String phone1) {
        Phone1 = phone1;
    }

    public String getE_Mail() {
        return E_Mail;
    }

    public void setE_Mail(String e_Mail) {
        E_Mail = e_Mail;
    }
}
