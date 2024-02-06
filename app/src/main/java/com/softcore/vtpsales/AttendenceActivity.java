package com.softcore.vtpsales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.softcore.vtpsales.databinding.ActivityAttendenceBinding;

public class AttendenceActivity extends AppCompatActivity {
    String OPERATION;
    ActivityAttendenceBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAttendenceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        OPERATION=getIntent().getStringExtra("operation");

        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Daily Attendance");

        if (OPERATION.equals("in")){
            binding.textButton.setText("CHECK IN");
        }else if(OPERATION.equals("out")){
            binding.textButton.setText("CHECK OUT");

        }
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