package com.softcore.vtpsales;



import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;


import com.softcore.vtpsales.Adaptors.AdapterBirth;
import com.softcore.vtpsales.Adaptors.ImageSliderAdapter;
import com.softcore.vtpsales.AppUtils.AppUtil;
import com.softcore.vtpsales.Model.BirthdayModel;
import com.softcore.vtpsales.Model.CommanResorce;
import com.softcore.vtpsales.ViewModel.BirthdayListViewModel;
import com.softcore.vtpsales.databinding.ActivityBirthdayAndAnniBinding;

import java.util.ArrayList;
import java.util.List;

public class BirthdayAndAnniActivity extends AppCompatActivity {
    AdapterBirth adapterBirth;
    ActivityBirthdayAndAnniBinding binding;
    String DbName;
    private String selectedType = "All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBirthdayAndAnniBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.laybar.appbarTextView.setText("Birthday & Anniversary");

        binding.laybar.backId.setVisibility(View.VISIBLE);
        binding.laybar.backId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        List<String> typesList = new ArrayList<>();
        typesList.add("All");
        typesList.add("BirthDay");
        typesList.add("Anniversary");
        binding.layFilter.setHint("Select Type");
        binding.autoCompleteTextView.setText(selectedType);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, typesList);

        binding.autoCompleteTextView.setAdapter(adapter);

        binding.autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedType = adapter.getItem(position);
                getData();
            }
        });


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterBirth = new AdapterBirth();
        binding.recyclerView.setAdapter(adapterBirth);

        getData();

    }

    private void getData() {
        DbName = AppUtil.getStringData(getApplicationContext(),"DatabaseName","");

        AppUtil.showProgressDialog(binding.getRoot(),"Loading");
        System.out.println("Database Name: "+DbName);
        BirthdayListViewModel birthdayListViewModel = new ViewModelProvider(this).get(BirthdayListViewModel.class);
        birthdayListViewModel.getBirthdayListinfo(DbName,"Birthday_Anniversary").observe(this, new Observer<CommanResorce<List<BirthdayModel>>>() {
            @Override
            public void onChanged(CommanResorce<List<BirthdayModel>> listCommanResorce) {

                if (listCommanResorce.data != null && !listCommanResorce.data.isEmpty()) {

                   // List<BirthdayModel> filteredList = new ArrayList<>();




                        if (selectedType.equals("All")) {
                            adapterBirth.setData(listCommanResorce.data);
                        } else {
                            List<BirthdayModel> filteredList = new ArrayList<>();
                            for (BirthdayModel modell : listCommanResorce.data) {
                                if (selectedType.equalsIgnoreCase(modell.getType())) {
                                    filteredList.add(modell);
                                }
                            }

                                adapterBirth.setData(filteredList);

                        }
                    List<BirthdayModel> filList = new ArrayList<>();
                    for (BirthdayModel model : listCommanResorce.data) {

                    if (AppUtil.isToday(model.getBirthDate())) {
                        filList.add(model);
                    }

                    if(filList.isEmpty()){
                        binding.viewPager.setVisibility(View.GONE);
                    }else {
                        binding.viewPager.setVisibility(View.VISIBLE);
                    }

                }

                ImageSliderAdapter adapter = new ImageSliderAdapter(getApplicationContext(), filList,false);
                binding.viewPager.setAdapter(adapter);
                final Handler handler = new Handler();
                final Runnable update = new Runnable() {
                    public void run() {
                        int currentPage = binding.viewPager.getCurrentItem();
                        int nextPage = currentPage + 1;
                        if (nextPage >= adapter.getCount()) {
                            nextPage = 0;
                        }
                        binding.viewPager.setCurrentItem(nextPage, true);
                        handler.postDelayed(this, 8000); // Repeat every 3 seconds
                    }
                };

                // Start auto-slide
                handler.postDelayed(update, 3000); // Delayed start after 3 seconds

                }else{
                  //  binding.cardlayBirthday.setVisibility(View.GONE);
                    binding.textPast.setText("Past (0)");
                }

                AppUtil.hideProgressDialog();
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