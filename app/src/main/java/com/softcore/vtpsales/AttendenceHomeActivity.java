package com.softcore.vtpsales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.softcore.vtpsales.databinding.ActivityAttendenceHomeBinding;

public class AttendenceHomeActivity extends AppCompatActivity {
    ActivityAttendenceHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAttendenceHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.laybar.appbarTextView.setText("Attendance");

        binding.laybar.backId.setVisibility(View.VISIBLE);
        binding.laybar.backId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


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