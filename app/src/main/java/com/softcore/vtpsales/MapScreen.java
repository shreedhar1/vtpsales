package com.softcore.vtpsales;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
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
import com.softcore.vtpsales.R;
import com.softcore.vtpsales.databinding.ActivityCustAttendenceBinding;
import com.softcore.vtpsales.databinding.ActivityGoogleMapBinding;

import java.io.IOException;
import java.util.List;

public class MapScreen extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap gMap;
    private String InLocation;
    private String OutLocation;
    private String EmpName;
    ActivityGoogleMapBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGoogleMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.laybar.appbarTextView.setText("Google Map View");

        binding.laybar.backId.setVisibility(View.VISIBLE);
        binding.laybar.print.setVisibility(View.GONE);
        binding.laybar.shareId.setVisibility(View.GONE);
        binding.laybar.backId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        InLocation = getIntent().getStringExtra("InLocation");
        OutLocation = getIntent().getStringExtra("OutLocation");
        EmpName =  getIntent().getStringExtra("EmpName");
//
//        InLocation = "5WXX+8J7, Wagle Industrial Estate, Thane West, Thane, Maharashtra 400604";
//        OutLocation = "5WGV+G37, Lal Bahadur Shastri Marg, Near Johnson Company, Sambhaji Nagar, Mulund West, Mumbai, Maharashtra 400080";

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;
        markLocations();
    }

    private void markLocations() {
        if (InLocation == null || OutLocation == null) {
            Toast.makeText(this, "Locations not provided", Toast.LENGTH_SHORT).show();
            return;
        }

        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> inLocationAddresses = geocoder.getFromLocationName(InLocation, 1);
            List<Address> outLocationAddresses = geocoder.getFromLocationName(OutLocation, 1);

            if (!inLocationAddresses.isEmpty() && !outLocationAddresses.isEmpty()) {
                Address inLocationAddress = inLocationAddresses.get(0);
                Address outLocationAddress = outLocationAddresses.get(0);

                LatLng inLatLng = new LatLng(inLocationAddress.getLatitude(), inLocationAddress.getLongitude());
                LatLng outLatLng = new LatLng(outLocationAddress.getLatitude(), outLocationAddress.getLongitude());

                // Add markers for in location and out location
                gMap.addMarker(new MarkerOptions().position(inLatLng).title("In Location"));
                gMap.addMarker(new MarkerOptions().position(outLatLng).title("Out Location"));

                // Draw a line between in location and out location
                PolylineOptions polylineOptions = new PolylineOptions()
                        .add(inLatLng, outLatLng)
                        .width(5) // width of the line
                        .color(Color.RED); // color of the line
                gMap.addPolyline(polylineOptions);

                // Move camera to show all markers
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(inLatLng);
                builder.include(outLatLng);
                LatLngBounds bounds = builder.build();
                int padding = 100; // offset from edges of the map in pixels
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                gMap.moveCamera(cu);
            } else {
                Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error finding location", Toast.LENGTH_SHORT).show();
        }
    }

}
