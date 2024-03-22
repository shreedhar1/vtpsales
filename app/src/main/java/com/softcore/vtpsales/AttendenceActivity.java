package com.softcore.vtpsales;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.util.JsonReader;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.softcore.vtpsales.AppUtils.AppUtil;
import com.softcore.vtpsales.Model.ClockRequest;
import com.softcore.vtpsales.Network.RemoteRepository;
import com.softcore.vtpsales.databinding.ActivityAttendenceBinding;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;


public class AttendenceActivity extends AppCompatActivity {
    String OPERATION;
    ActivityAttendenceBinding binding;
    ClockRequest request;
    String remark;
    String CurrentLocation = "";
    String status;
    private FrameLayout cameraContainer;
    final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    FusedLocationProviderClient fusedLocationClient;

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;



    private static final int LOCATION_PERMISSION_REQUEST_CODE = 101;
    private static final int CAMERA_AND_LOCATION_PERMISSION_REQUEST_CODE = 100;

    private Handler handler;
    private CameraPreview cameraPreview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAttendenceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        OPERATION = getIntent().getStringExtra("operation");


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request both permissions together
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                    CAMERA_AND_LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            showCameraPreview();
            getCurrentLocation();
            // Both permissions are already granted
        //    Toast.makeText(this, "Camera and location permissions granted", Toast.LENGTH_SHORT).show();
        }

        binding.laybar.appbarTextView.setText("Daily Attendance");

        binding.laybar.backId.setVisibility(View.VISIBLE);
        binding.laybar.backId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        handler = new Handler(Looper.getMainLooper());

        // Update the time every second
        handler.post(updateTimeRunnable);


        getCurrentLocation();


        if (OPERATION.equals("in")) {
            status = "Clock In";
            binding.textButton.setText("CHECK IN");
            binding.edRemark.setHint("Remark for Clock IN");
            remark = binding.edRemark.toString().trim();


        } else if (OPERATION.equals("out")) {
            status = "Clock Out";
            binding.textButton.setText("CHECK OUT");
            binding.edRemark.setHint("Remark for Clock Out");
            remark = binding.edRemark.toString().trim();


        }


        binding.ClockInOutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CurrentLocation.equals("")){
                    Toast.makeText(AttendenceActivity.this, "Location not available", Toast.LENGTH_SHORT).show();
                }else{
                    Clock_In_Out(v, OPERATION);
                }


            }
        });


    }

    private void showCameraPreview() {
        cameraContainer = findViewById(R.id.camera_preview);
        cameraPreview = new CameraPreview(this);
        cameraContainer.addView(cameraPreview);
    }

    private void Clock_In_Out(View v,String OPERATION) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String currentDate = dateFormat.format(calendar.getTime());

        SimpleDateFormat time24Format = new SimpleDateFormat("HHmm");
        String currentTime12 = time24Format.format(calendar.getTime());

        System.out.println("CurrentDate: "+currentDate);
        System.out.println("CurrentTime: "+currentTime12);

        String EmpName = AppUtil.getStringData(getApplicationContext(),"EmpName","");
        String EmpCode = AppUtil.getStringData(getApplicationContext(),"EmpCode","");

        System.out.println("EmpName: "+EmpName);
        System.out.println("EmpCode: "+EmpCode);

        String ClockOutTime = "0000";
        String ClockInTime = "0000";
        String ClockInRemark = "";

        String ClockOutRemark = "";

        if (OPERATION.equals("in")){
             ClockOutTime = "0000";
            ClockInTime = currentTime12;
            ClockInRemark = binding.edRemark.getText().toString();
        }else if(OPERATION.equals("out")){
            ClockInTime = "0000";
            ClockOutTime = currentTime12;
            ClockOutRemark = binding.edRemark.getText().toString();
        }



        request = new ClockRequest(
                currentDate,
                EmpCode,
                EmpName,
                ClockInTime,
                ClockInRemark,
                ClockOutTime,
                ClockOutRemark,
                CurrentLocation
        );

        Gson gson = new Gson();
        String jsonRequest = gson.toJson(request);

        System.out.println("Request String: "+jsonRequest);

        AppUtil.showProgressDialog(v,"Loading");

        RemoteRepository repository = new RemoteRepository();
        repository.sendClockData(request, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    AppUtil.hideProgressDialog();

                    Toast.makeText(AttendenceActivity.this, binding.textButton.getText().toString()+" Successfully", Toast.LENGTH_SHORT).show();
                    System.out.println("Response Code: "+response.code());
                    // Handle success
                } else {
                    AppUtil.hideProgressDialog();
                    System.out.println("Response Code: "+response.code());

                    // Handle failure
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle failure
                Toast.makeText(AttendenceActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                AppUtil.hideProgressDialog();
            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Runnable updateTimeRunnable = new Runnable() {
        @Override
        public void run() {
            updateTime();
            handler.postDelayed(this, 1000); // Update every second
        }
    };
    private void updateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a", Locale.US);
        String currentTime = sdf.format(new Date());
        binding.txtTime.setText(currentTime);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updateTimeRunnable);
    }

    private void getCurrentLocation() {


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        } else {
            getLastLocation();
        }
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            // Do something with latitude and longitude
                            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                            try {
                                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                                if (addresses != null && addresses.size() > 0) {
                                    Address address = addresses.get(0);
                                    String addressString = address.getAddressLine(0); // Get the first line of the address
                                   // Toast.makeText(getApplicationContext(), "Address: " + addressString, Toast.LENGTH_SHORT).show();
                                    CurrentLocation = addressString;
                                    binding.txtAddress.setText(addressString);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } else {
                            Toast.makeText(AttendenceActivity.this, "Location not available", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_AND_LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // Both permissions granted
                showCameraPreview();
                getCurrentLocation();

          //      Toast.makeText(this, "Camera and location permissions granted", Toast.LENGTH_SHORT).show();
            } else {
                // One or both permissions denied
                Toast.makeText(this, "Camera or location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}