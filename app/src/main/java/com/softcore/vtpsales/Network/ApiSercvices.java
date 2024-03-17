package com.softcore.vtpsales.Network;


import com.softcore.vtpsales.Model.AttendanceModel;
import com.softcore.vtpsales.Model.BalanceDueResponse;
import com.softcore.vtpsales.Model.BirthdayModel;
import com.softcore.vtpsales.Model.ClockRequest;
import com.softcore.vtpsales.Model.CusClockRequest;
import com.softcore.vtpsales.Model.CusReportWiseDetModel;
import com.softcore.vtpsales.Model.CusReportWiseModel;
import com.softcore.vtpsales.Model.CustomerModel;
import com.softcore.vtpsales.Model.Database;
import com.softcore.vtpsales.Model.MyTeamModel;
import com.softcore.vtpsales.Model.SlpResponse;
import com.softcore.vtpsales.Model.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiSercvices {

    @GET("database-list")
    Call<List<Database>> getDatabases();

    @GET("emp_login")
    Call<List<UserModel>> getLoginDetails(@Query("DB_NAME") String DbName,@Query("UserName") String UserName, @Query("UserPassword") String Password);

    @GET("SCS_VTP_API")
    Call<List<BirthdayModel>> getCustomers(
            @Query("DB_NAME") String dbName,
            @Query("Flag") String flag
    );

    @GET("SCS_VTP_API")
    Call<List<AttendanceModel>> getAttendanceDetails(
            @Query("DB_NAME") String dbName,
            @Query("Flag") String flag
    );

    @POST("SCS_CLOCK")
    Call<Void> sendClockData(@Body ClockRequest request);
    @POST("SCS_CLOCK")
    Call<Void> sendCusClockData(@Body CusClockRequest request);

    @GET("MY_Team")
    Call<List<MyTeamModel>> getMyTeamsData(@Query("DB_NAME") String DbName, @Query("ExtEmpNo") String ExtEmpNo);

    @GET("SCS_VTP_API")
    Call<List<SlpResponse>> getSlpData(@Query("DB_NAME") String DbName, @Query("Flag") String Flag);

    @GET("SCS_VTP_API")
    Call<List<CustomerModel>> getActCustomer(@Query("DB_NAME") String DbName, @Query("Flag") String Flag);


    @GET("SCS_Outstanding_Reports")
    Call<List<BalanceDueResponse>> getDueBalance(@Query("FromDate") String FromDate,
                                             @Query("ToDate") String ToDate,
                                             @Query("SlpName") String SlpName,
                                             @Query("DB_NAME") String DB_NAME,
                                             @Query("Flag") String Flag);

    @GET("SCS_Outstanding_Reports")
    Call<List<BalanceDueResponse>> getPurchaseBalanceDue(@Query("FromDate") String FromDate,
                                                 @Query("ToDate") String ToDate,
                                                 @Query("SlpName") String SlpName,
                                                 @Query("DB_NAME") String DB_NAME,
                                                 @Query("Flag") String Flag);

    @GET("SCS_Outstanding_Reports")
    Call<List<BalanceDueResponse>> getReceipt(@Query("FromDate") String FromDate,
                                                         @Query("ToDate") String ToDate,
                                                         @Query("SlpName") String SlpName,
                                                         @Query("DB_NAME") String DB_NAME,
                                                         @Query("Flag") String Flag);

    @GET("SCS_Outstanding_Reports")
    Call<List<BalanceDueResponse>> getOutstanding(@Query("FromDate") String FromDate,
                                                         @Query("ToDate") String ToDate,
                                                         @Query("SlpName") String SlpName,
                                                         @Query("DB_NAME") String DB_NAME,
                                                         @Query("Flag") String Flag);

    @GET("SCS_Outstanding_Reports")
    Call<List<CusReportWiseModel>> getCusWiseReport(@Query("FromDate") String FromDate,
                                                    @Query("ToDate") String ToDate,
                                                    @Query("SlpName") String SlpName,
                                                    @Query("DB_NAME") String DB_NAME,
                                                    @Query("Flag") String Flag);

    @GET("SCS_Outstanding_Reports")
    Call<List<CusReportWiseDetModel>> getCusWiseDetreport(@Query("FromDate") String FromDate,
                                                          @Query("ToDate") String ToDate,
                                                          @Query("SlpName") String SlpName,
                                                          @Query("DB_NAME") String DB_NAME,
                                                          @Query("Flag") String Flag);
}
