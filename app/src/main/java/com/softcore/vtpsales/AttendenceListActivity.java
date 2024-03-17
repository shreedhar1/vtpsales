package com.softcore.vtpsales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.softcore.vtpsales.Adaptors.AdapterAttendance;
import com.softcore.vtpsales.AppUtils.AppUtil;
import com.softcore.vtpsales.Model.AttendanceModel;
import com.softcore.vtpsales.Model.CommanResorce;
import com.softcore.vtpsales.ViewModel.AttendanceListViewModel;
import com.softcore.vtpsales.databinding.ActivityAttendenceListBinding;

import java.util.List;

public class AttendenceListActivity extends AppCompatActivity {
    AdapterAttendance adapterAttendence;
    ActivityAttendenceListBinding binding;
    String DbName;
    String Flag;

    String TYPE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAttendenceListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setTitle("Attendance List");

        binding.laybar.appbarTextView.setText("Attendance List");

        binding.laybar.backId.setVisibility(View.VISIBLE);
        binding.laybar.backId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TYPE=getIntent().getStringExtra("type");
        System.out.println("List Type :"+TYPE);
        if(TYPE.equals("emp")){
            Flag = "Clock_IN_Out";
        }else if(TYPE.equals("cust")){
            Flag = "Clock_IN_Out_Customer";
        }

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterAttendence = new AdapterAttendance();
        binding.recyclerView.setAdapter(adapterAttendence);

        DbName = AppUtil.getStringData(getApplicationContext(),"DatabaseName","");

        System.out.println("Database Name: "+DbName);
        AttendanceListViewModel attendanceListViewModel = new ViewModelProvider(this).get(AttendanceListViewModel.class);
        attendanceListViewModel.getAttendanceListinfo(DbName,Flag).observe(this, new Observer<CommanResorce<List<AttendanceModel>>>() {
            @Override
            public void onChanged(CommanResorce<List<AttendanceModel>> listCommanResorce) {

                if (listCommanResorce.data != null && !listCommanResorce.data.isEmpty()) {

                    adapterAttendence.setData(listCommanResorce.data);


                }
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