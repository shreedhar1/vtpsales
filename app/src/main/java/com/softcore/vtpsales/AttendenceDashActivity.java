package com.softcore.vtpsales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.softcore.vtpsales.databinding.ActivityAttendenceDashBinding;

public class AttendenceDashActivity extends AppCompatActivity {

    ActivityAttendenceDashBinding binding;
    String TYPE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_attendence_dash);
        binding=ActivityAttendenceDashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        TYPE=getIntent().getStringExtra("type");

        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(TYPE.equals("emp")?"Daily Attendance":"Customer Attendance");


        binding.cardCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TYPE.equals("emp")) {
                    Intent intent = new Intent(AttendenceDashActivity.this, AttendenceActivity.class);
                    intent.putExtra("operation", "in");
                    startActivity(intent);
                }else if (TYPE.equals("cust")) {
                    Intent intent = new Intent(AttendenceDashActivity.this, CustAttendenceActivity.class);
                    intent.putExtra("operation", "in");
                    startActivity(intent);
                }
            }
        });
        binding.cardCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TYPE.equals("emp")) {
                    Intent intent = new Intent(AttendenceDashActivity.this, AttendenceActivity.class);
                    intent.putExtra("operation", "out");
                    startActivity(intent);
                }else if(TYPE.equals("cust")) {
                    Intent intent = new Intent(AttendenceDashActivity.this, CustAttendenceActivity.class);
                    intent.putExtra("operation", "out");
                    startActivity(intent);
                }
            }
        });
        binding.cardListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AttendenceDashActivity.this, AttendenceListActivity.class));
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