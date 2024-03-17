package com.softcore.vtpsales.Model;

public class ClockRequest {
    private String Date;
    private String Employee_Code;
    private String Employee_Name;
    private String Check_In_Time;
    private String Check_In_Remark;
    private String Check_out_Time;
    private String Check_out_Remark;
    private String Check_out_Location;
    private String Customer_Code;

    public ClockRequest(String date, String employee_Code, String employee_Name, String check_In_Time, String check_In_Remark, String check_out_Time, String check_out_Remark, String check_out_Location ) {
        Date = date;
        Employee_Code = employee_Code;
        Employee_Name = employee_Name;
        Check_In_Time = check_In_Time;
        Check_In_Remark = check_In_Remark;
        Check_out_Time = check_out_Time;
        Check_out_Remark = check_out_Remark;
        Check_out_Location = check_out_Location;

    }

    // Getters and setters
}

