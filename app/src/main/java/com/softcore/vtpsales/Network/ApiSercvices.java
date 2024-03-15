package com.softcore.vtpsales.Network;


import com.softcore.vtpsales.Model.AttendanceModel;
import com.softcore.vtpsales.Model.BirthdayModel;
import com.softcore.vtpsales.Model.ClockRequest;
import com.softcore.vtpsales.Model.Database;
import com.softcore.vtpsales.Model.MyTeamModel;
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


    @GET("MY_Team")
    Call<List<MyTeamModel>> getMyTeamsData(@Query("DB_NAME") String DbName, @Query("ExtEmpNo") String ExtEmpNo);

}
