package com.softcore.vtpsales;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.softcore.vtpsales.AppUtils.AppUtil;
import com.softcore.vtpsales.Model.ClockRequest;
import com.softcore.vtpsales.Model.CommanResorce;
import com.softcore.vtpsales.Model.CusClockRequest;
import com.softcore.vtpsales.Model.CustomerModel;
import com.softcore.vtpsales.Model.GeneralModel;
import com.softcore.vtpsales.Model.SlpResponse;
import com.softcore.vtpsales.Network.RemoteRepository;
import com.softcore.vtpsales.ViewModel.CustomerViewModel;
import com.softcore.vtpsales.ViewModel.SlpViewModel;
import com.softcore.vtpsales.databinding.ActivityCustAttendenceBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustAttendenceActivity extends AppCompatActivity {
    String OPERATION;
    ActivityCustAttendenceBinding binding;
    String TYPE;
    private Handler handler;
    final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    FusedLocationProviderClient fusedLocationClient;


    String CurrentLocation = "";

    String SlpName;
    CustomerModel selectedmodel;
    String selectedSlpName;
    CusClockRequest request;
    String status;
    private static final int CAMERA_AND_LOCATION_PERMISSION_REQUEST_CODE = 100;
    private CameraPreview cameraPreview;
    private FrameLayout cameraContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustAttendenceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        TYPE = getIntent().getStringExtra("type");

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

       // fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        System.out.println("TYPE:" + TYPE);

        OPERATION = getIntent().getStringExtra("operation");

        binding.laybar.appbarTextView.setText("Customer Clock " + (OPERATION.equals("in") ? "In" : "Out"));

        binding.laybar.backId.setVisibility(View.VISIBLE);
        binding.laybar.backId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (OPERATION.equals("in")) {
            status = "Clock In";
            binding.textButton.setText("CHECK IN");



        } else if (OPERATION.equals("out")) {
            status = "Clock Out";
            binding.textButton.setText("CHECK OUT");

//            ClockInTime = "0000";
//            ClockOutTime = currentTime12;
//            LocationIn = "";
//            LocationOut = CurrentLocation;
//            ClockOutRemark = binding.edRemark.getText().toString();

        }
        binding.edRemark.setHint("Remark for "+status);
        binding.ClockInOutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CurrentLocation.equals("")){
                    Toast.makeText(CustAttendenceActivity.this, "location not available", Toast.LENGTH_SHORT).show();
                }else if(selectedmodel != null){
                    Clock_In_Out(v, OPERATION);
                }else {
                    Toast.makeText(CustAttendenceActivity.this, "Select Company Name", Toast.LENGTH_SHORT).show();
                }


            }
        });

        handler = new Handler(Looper.getMainLooper());

        // Update the time every second
        handler.post(updateTimeRunnable);





        getCustomerList();
        getCurrentLocation();
    }

    private void showCameraPreview() {
        cameraContainer = findViewById(R.id.camera_preview);
        cameraPreview = new CameraPreview(this);
        cameraContainer.addView(cameraPreview);
    }

    private void Clock_In_Out(View v, String OPERATION) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String currentDate = dateFormat.format(calendar.getTime());

        SimpleDateFormat time24Format = new SimpleDateFormat("HHmm");
        String currentTime12 = time24Format.format(calendar.getTime());

        System.out.println("CurrentDate: " + currentDate);
        System.out.println("CurrentTime: " + currentTime12);

        String EmpName = AppUtil.getStringData(getApplicationContext(), "EmpName", "");
        String EmpCode = AppUtil.getStringData(getApplicationContext(), "EmpCode", "");

        System.out.println("EmpName: " + EmpName);
        System.out.println("EmpCode: " + EmpCode);

        String ClockOutTime = "0000";
        String ClockInTime = "0000";
        String ClockInRemark = "";
        String ClockOutRemark = "";
        String LocationIn = "";
        String LocationOut = "";

        if (OPERATION.equals("in")) {
            ClockOutTime = "0000";
            ClockInTime = currentTime12;

            LocationIn = CurrentLocation;
            LocationOut = "";
            ClockOutRemark = "";
            ClockInRemark = binding.edRemark.getText().toString();
        } else if (OPERATION.equals("out")) {
            ClockOutTime = currentTime12;
            ClockInTime = "0000";
            LocationIn = "";
            LocationOut = CurrentLocation;
            ClockOutRemark = binding.edRemark.getText().toString();
        }


        request = new CusClockRequest(
                currentDate,
                EmpCode,
                EmpName,
                ClockInTime,
                ClockInRemark,
                ClockOutTime,
                ClockOutRemark,
                LocationIn,
                LocationOut,
                selectedmodel.getCardCode()
        );

        Gson gson = new Gson();
        String jsonRequest = gson.toJson(request);

        System.out.println("Request String: " + jsonRequest);

        AppUtil.showProgressDialog(v, "Loading");

        RemoteRepository repository = new RemoteRepository();
        repository.sendCusClockData(request, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    AppUtil.hideProgressDialog();

                    Toast.makeText(CustAttendenceActivity.this,  binding.textButton.getText().toString()+" Success", Toast.LENGTH_SHORT).show();
                    System.out.println("Response Code: " + response.code());
                    Intent intent = new Intent(CustAttendenceActivity.this, MainActivity2.class);
                    startActivity(intent);
                    finish();
                    // Handle success
                } else {
                    AppUtil.hideProgressDialog();
                    System.out.println("Response Code: " + response.code());

                    // Handle failure
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle failure
                Toast.makeText(CustAttendenceActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                AppUtil.hideProgressDialog();
            }
        });


    }

    private void getCustomerList() {
        String dbName = AppUtil.getStringData(getApplicationContext(), "DatabaseName", "");

        CustomerViewModel customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
        customerViewModel.getActCusDetail(dbName, "Active_Customer_List").observe(this, new Observer<CommanResorce<List<CustomerModel>>>() {
            @Override
            public void onChanged(CommanResorce<List<CustomerModel>> listCommanResorce) {

                if (listCommanResorce.data != null && !listCommanResorce.data.isEmpty()) {

                    List<String> slpNames = new ArrayList<>();
                    List<CustomerModel> selectedModelList = new ArrayList<>();


                    for (CustomerModel database : listCommanResorce.data) {
                        slpNames.add(database.getCardName());
                        selectedModelList.add(database);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(CustAttendenceActivity.this,
                            R.layout.simple_spinner_design, slpNames);

//                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

                    binding.autoCompleteTextView.setAdapter(adapter);
                    binding.autoCompleteTextView.setThreshold(1);
                    binding.autoCompleteTextView.setDropDownHeight(800);
                    binding.autoCompleteTextView.setOnClickListener(new View.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(View v) {
                                                                            binding.autoCompleteTextView.showDropDown();
                                                                        }
                                                                    }
                    );
                    binding.autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            selectedSlpName = slpNames.get(position);
//
//                                SlpName = selectedSlpName;
//                                selectedmodel = selectedModelList.get(position); // Adjust position by -1 to account for the "SELECT CUSTOMER NAME" item
//                                System.out.println("code: " + selectedmodel.getCardCode());

                            String selectedName = adapter.getItem(position);
                            CustomerModel selectedModel = null;
                            for (CustomerModel model : listCommanResorce.data) {
                                if (model.getCardName().equals(selectedName)) {
                                    selectedModel = model;
                                    break;
                                }
                            }
                            if (selectedModel != null) {
                                selectedmodel = selectedModel;
//                                selStateCode = selectedModel.getCode();
                                selectedSlpName = selectedmodel.getCardName();
                                SlpName = selectedSlpName;
                                System.out.println("card Name: " + selectedmodel.getCardName());
                                System.out.println("card code : " + selectedmodel.getCardCode());
                                // GetData();
                            }

                                String add = selectedmodel.getAddress();
                                String street = selectedmodel.getStreet();
                                String block = selectedmodel.getBlock();
                                String city = selectedmodel.getCity();
                                String state = selectedmodel.getShipToState();
                                String country = selectedmodel.getShipToCountry();
                                String zip = selectedmodel.getZipCode();

                                String cus_address = add +", "+street +", "+block +", "+city +", "+
                                        state +", "+country +"- "+zip;

                                binding.txtCusAddress.setVisibility(View.VISIBLE);
                                binding.txtCusAddress.setText(cus_address);


                        }
                    });

                    binding.autoCompleteTextView.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            adapter.getFilter().filter(s.toString());
                            System.out.println(s.toString());
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                        }
                    });

                } else {
                    // AppUtil.showTost(getApplicationContext(), "User not found. Please check your username.");
                    System.out.println("Slp Not found");
                }

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
                            Toast.makeText(CustAttendenceActivity.this, "Location not available", Toast.LENGTH_SHORT).show();
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