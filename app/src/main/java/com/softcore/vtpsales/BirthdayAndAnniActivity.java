package com.softcore.vtpsales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.MenuItem;


import com.softcore.vtpsales.Adaptors.AdapterBirth;
import com.softcore.vtpsales.AppUtils.AppUtil;
import com.softcore.vtpsales.Model.BirthdayModel;
import com.softcore.vtpsales.Model.CommanResorce;
import com.softcore.vtpsales.ViewModel.BirthdayListViewModel;
import com.softcore.vtpsales.databinding.ActivityBirthdayAndAnniBinding;

import java.util.List;

public class BirthdayAndAnniActivity extends AppCompatActivity {
    AdapterBirth adapterBirth;
    ActivityBirthdayAndAnniBinding binding;
    String DbName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBirthdayAndAnniBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setTitle("Birthday & Anniversary");

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterBirth = new AdapterBirth();
        binding.recyclerView.setAdapter(adapterBirth);

        DbName = AppUtil.getStringData(getApplicationContext(),"DatabaseName","");

        System.out.println("Database Name: "+DbName);
        BirthdayListViewModel birthdayListViewModel = new ViewModelProvider(this).get(BirthdayListViewModel.class);
        birthdayListViewModel.getBirthdayListinfo(DbName,"Birthday_Anniversary").observe(this, new Observer<CommanResorce<List<BirthdayModel>>>() {
            @Override
            public void onChanged(CommanResorce<List<BirthdayModel>> listCommanResorce) {

                System.out.println("Birthday Data Message : "+listCommanResorce.message);
                System.out.println("Birthday Data status : "+listCommanResorce.status);
                System.out.println("Birthday Data listCommanResorce.data : "+listCommanResorce.data.toString());
                System.out.println("Birthday Data listCommanResorce : "+listCommanResorce.toString());

                        if (listCommanResorce.data != null && !listCommanResorce.data.isEmpty()) {

                            adapterBirth.setData(listCommanResorce.data);


                      }
            }
        });


//
//
//        RemoteRepository remoteRepository = new RemoteRepository();
//        remoteRepository.getBirthdayList() {
//            @Override
//            public void onSuccess(UserResponse userResponse) {
//                adapter.setData(userResponse.getData());
//            }
//
//            @Override
//            public void onError(String message) {
//                // Show error message
//                AppUtil.hideProgressDialog();
//                System.out.println("Error: "+message);
//                Toast.makeText(Users_Screen.this, " Recyclerview Failed :"+message, Toast.LENGTH_SHORT).show();
//
//            }
//        });


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