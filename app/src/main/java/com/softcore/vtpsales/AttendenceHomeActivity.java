package com.softcore.vtpsales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.softcore.vtpsales.databinding.ActivityAttendenceHomeBinding;

public class AttendenceHomeActivity extends AppCompatActivity {
    ActivityAttendenceHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_attendence_home);
        binding=ActivityAttendenceHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Attendance");



        binding.cardDailyAttendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(AttendenceHomeActivity.this, AttendenceDashActivity.class);
                intent.putExtra("type","emp");
                startActivity(intent);
            }
        });
        binding.cardCustomerAttendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AttendenceHomeActivity.this, AttendenceDashActivity.class);
                intent.putExtra("type","cust");
                startActivity(intent);
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

}