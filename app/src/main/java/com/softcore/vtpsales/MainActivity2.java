package com.softcore.vtpsales;

import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.softcore.vtpsales.databinding.ActivityMain2Binding;

public class MainActivity2 extends AppCompatActivity {

private ActivityMain2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     binding = ActivityMain2Binding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main2);
        NavigationUI.setupWithNavController(binding.navView, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(NavController controller, NavDestination destination, Bundle arguments) {
                if (destination.getId() == R.id.navigation_dashboard) {
                    binding.laybar.appbarTextView.setText("Dashboard");
                } else if (destination.getId() == R.id.navigation_attendance) {
                    binding.laybar.appbarTextView.setText("Attendance");
                } else if (destination.getId() == R.id.navigation_profile) {
                    binding.laybar.appbarTextView.setText("Profile");
                } else {
                    binding.laybar.appbarTextView.setText("");
                }

            }
        });

//        Button openFragmentButton = findViewById(R.id.open_fragment_button);
//        openFragmentButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                navController.navigate(R.id.your_fragment_id);
//            }
//        });

    }

}