package com.softcore.vtpsales;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.softcore.vtpsales.AppUtils.AppUtil;
import com.softcore.vtpsales.Model.ClockRequest;
import com.softcore.vtpsales.Model.CommanResorce;
import com.softcore.vtpsales.Model.CusClockRequest;
import com.softcore.vtpsales.Model.CustomerModel;
import com.softcore.vtpsales.Model.SlpResponse;
import com.softcore.vtpsales.Network.RemoteRepository;
import com.softcore.vtpsales.ViewModel.CustomerViewModel;
import com.softcore.vtpsales.ViewModel.SlpViewModel;
import com.softcore.vtpsales.databinding.ActivityCustAttendenceBinding;

import java.io.IOException;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCustAttendenceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        TYPE=getIntent().getStringExtra("type");

        System.out.println("TYPE:"+TYPE);

        OPERATION=getIntent().getStringExtra("operation");

        binding.laybar.appbarTextView.setText("Customer Clock "+ (OPERATION.equals("in")?"In":"Out"));

        binding.laybar.backId.setVisibility(View.VISIBLE);
        binding.laybar.backId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (OPERATION.equals("in")){
            status = "Clock In";
            binding.textButton.setText("CHECK IN");
        }else if(OPERATION.equals("out")){
            status = "Clock Out";
            binding.textButton.setText("CHECK OUT");

        }

        binding.textButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Clock_In_Out(v, OPERATION);

            }
        });

        handler = new Handler(Looper.getMainLooper());

        // Update the time every second
        handler.post(updateTimeRunnable);

        getCustomerList();
        getCurrentLocation();
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

        String ClockInRemark = "";

        String ClockOutRemark = "";

        if (OPERATION.equals("in")){
            ClockOutTime = "0000";
        }else if(OPERATION.equals("out")){
            ClockOutTime = currentTime12;
        }



        request = new CusClockRequest(
                currentDate,
                EmpCode,
                EmpName,
                currentTime12,
                "",
                ClockOutTime,
                ClockOutRemark,
                CurrentLocation,
                selectedmodel.getCardCode()
        );

        Gson gson = new Gson();
        String jsonRequest = gson.toJson(request);

        System.out.println("Request String: "+jsonRequest);

        AppUtil.showProgressDialog(v,"Loading");

        RemoteRepository repository = new RemoteRepository();
        repository.sendCusClockData(request, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    AppUtil.hideProgressDialog();

                    Toast.makeText(CustAttendenceActivity.this, status+" Successfully", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(CustAttendenceActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                AppUtil.hideProgressDialog();
            }
        });



    }

    private void getCustomerList() {

        String DbName = AppUtil.getStringData(getApplicationContext(), "DatabaseName", "");

        CustomerViewModel customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
        customerViewModel.getActCusDetail(DbName, "Active_Customer_List").observe(this, new Observer<CommanResorce<List<CustomerModel>>>() {
            @Override
            public void onChanged(CommanResorce<List<CustomerModel>> listCommanResorce) {

                if (listCommanResorce.data != null && !listCommanResorce.data.isEmpty()) {

                    List<String> slpnames = new ArrayList<>();

                    slpnames.add("SELECT CUSTOMER NAME");
                    List<CustomerModel> selectedmodelList = new ArrayList<>();
                    for (CustomerModel database : listCommanResorce.data) {
                        slpnames.add(database.getCardName());
                        selectedmodelList.add(database);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(CustAttendenceActivity.this,
                            R.layout.simple_spinner_layout_black, slpnames);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    binding.spinnerCustomer.setAdapter(adapter);

                    binding.spinnerCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                            selectedSlpName = slpnames.get(position);

                            if (!selectedSlpName.equals("SELECT CUSTOMER NAME")) {
                                SlpName = selectedSlpName;
                                selectedmodel = selectedmodelList.get(position);
                            System.out.println("code: "+selectedmodel.getCardCode());
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {
                            // Do nothing
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

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        } else {
            getLastLocation();
        }
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }

    }


}