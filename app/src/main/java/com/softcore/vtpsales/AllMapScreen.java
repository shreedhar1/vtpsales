package com.softcore.vtpsales;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.android.SphericalUtil;
import com.softcore.vtpsales.Model.AttendanceModel;
import com.softcore.vtpsales.databinding.ActivityGoogleMapBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AllMapScreen extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap gMap;
    private ActivityGoogleMapBinding binding;
    List<AttendanceModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGoogleMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String EmpName = getIntent().getStringExtra("title");
        String daterange = getIntent().getStringExtra("daterange");

        binding.laybar.laytitle2.setVisibility(View.VISIBLE);
        binding.laybar.appbarTextView.setVisibility(View.GONE);
        binding.laybar.txtTitle2.setText(EmpName);
        binding.laybar.txtDesc2.setText(daterange);

        binding.laybar.print.setVisibility(View.GONE);
        binding.laybar.shareId.setVisibility(View.GONE);

        binding.laybar.backId.setVisibility(View.VISIBLE);
        binding.laybar.backId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        List<AttendanceModel> locationsList = (List<AttendanceModel>) getIntent().getSerializableExtra("locationsList");
        list = new ArrayList<>();
        list = locationsList;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String locationsJson = gson.toJson(locationsList);
        Log.d("Locations", locationsJson);



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;
        markLocations(list);
    }

    private void markLocations(List<AttendanceModel> locations) {
        if (gMap == null) {
            System.out.println("Map Is not ready yet");
            return;
        }

        if (locations == null || locations.isEmpty()) {
            Toast.makeText(this, "Locations not provided", Toast.LENGTH_SHORT).show();
            return;
        }

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Geocoder geocoder = new Geocoder(AllMapScreen.this);
                for (AttendanceModel location : locations) {
                    if (location.getLocationIn() != null && location.getLocationOut() != null) {
                        try {
                            List<Address> inLocationAddresses = geocoder.getFromLocationName(location.getLocationIn(), 1);
                            List<Address> outLocationAddresses = geocoder.getFromLocationName(location.getLocationOut(), 1);

                            if (!inLocationAddresses.isEmpty() && !outLocationAddresses.isEmpty()) {
                                Address inLocationAddress = inLocationAddresses.get(0);
                                Address outLocationAddress = outLocationAddresses.get(0);

                                LatLng inLatLng = new LatLng(inLocationAddress.getLatitude(), inLocationAddress.getLongitude());
                                LatLng outLatLng = new LatLng(outLocationAddress.getLatitude(), outLocationAddress.getLongitude());

                                // Update UI on the main thread
                                runOnUiThread(() -> {

                                    double distance = SphericalUtil.computeDistanceBetween(inLatLng, outLatLng) / 1000; // distance in kilometers

                                    // Add markers for in location and out location
                                    gMap.addMarker(new MarkerOptions().position(inLatLng).title("In Location "+String.format("%.2f", distance) + " km"));
                                    gMap.addMarker(new MarkerOptions().position(outLatLng).title("Out Location "+String.format("%.2f", distance) + " km"
                                    ));

                                    PolylineOptions polylineOptions = new PolylineOptions()
                                            .add(inLatLng, outLatLng)
                                            .clickable(true)
                                            .width(5) // width of the line
                                            .color(Color.RED);// color of the line
                                    gMap.addPolyline(polylineOptions);

//                                    gMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
//
//                                        @Override
//                                        public void onPolylineClick(Polyline polyline) {
//                                            Toast.makeText(AllMapScreen.this, "Distance: " + String.format("%.2f", distance) + " km", Toast.LENGTH_LONG).show();
//                                        }
//                                    });

                                    // Move camera to show all markers
                                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                                    builder.include(inLatLng);
                                    builder.include(outLatLng);
                                    LatLngBounds bounds = builder.build();
                                    int padding = 100; // offset from edges of the map in pixels
                                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                                    gMap.moveCamera(cu);
                                });
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return null;
            }
        }.execute();
    }





}
