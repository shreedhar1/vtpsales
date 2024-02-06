package com.softcore.vtpsales;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.softcore.vtpsales.databinding.ActivityCustAttendenceBinding;

public class CustAttendenceActivity extends AppCompatActivity {
    String OPERATION;
    ActivityCustAttendenceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCustAttendenceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        OPERATION=getIntent().getStringExtra("operation");
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Customer Clock "+ (OPERATION.equals("in")?"In":"Out"));

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