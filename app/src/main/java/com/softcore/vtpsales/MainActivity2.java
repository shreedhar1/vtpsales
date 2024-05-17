package com.softcore.vtpsales;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.softcore.vtpsales.AppUtils.AppUtil;
import com.softcore.vtpsales.databinding.ActivityMain2Binding;

public class MainActivity2 extends AppCompatActivity {

private ActivityMain2Binding binding;
    private static final int TIME_INTERVAL = 2000; // Time between two back presses in milliseconds
    private long backPressedTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     binding = ActivityMain2Binding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());

        binding.laybar.backId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        AppUtil.setImage(binding,getApplicationContext());


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main2);
        NavigationUI.setupWithNavController(binding.navView, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(NavController controller, NavDestination destination, Bundle arguments) {
                if (destination.getId() == R.id.navigation_dashboard) {
                    binding.laybar.appbarTextView.setText("Dashboard");
                    binding.laybar.backId.setVisibility(View.GONE);
                    binding.laybar.ImgLogo.setVisibility(View.VISIBLE);
                } else if (destination.getId() == R.id.navigation_attendance) {
                    binding.laybar.appbarTextView.setText("Attendance");
                    binding.laybar.backId.setVisibility(View.VISIBLE);
                    binding.laybar.ImgLogo.setVisibility(View.GONE);
                } else if (destination.getId() == R.id.navigation_profile) {
                    binding.laybar.appbarTextView.setText("Profile");
                    binding.laybar.backId.setVisibility(View.VISIBLE);
                    binding.laybar.ImgLogo.setVisibility(View.GONE);
                } else if (destination.getId() == R.id.navigation_logout) {
                    binding.laybar.appbarTextView.setText("");
                    binding.laybar.backId.setVisibility(View.GONE);
                    binding.laybar.ImgLogo.setVisibility(View.GONE);
                     }

                else {
                    binding.laybar.appbarTextView.setText("");
                }

            }
        });
        
    }


    @Override
    public void onBackPressed() {
        if (backPressedTime + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }



}