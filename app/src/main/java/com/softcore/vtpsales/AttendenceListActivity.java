package com.softcore.vtpsales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import com.softcore.vtpsales.Adaptors.AdapterAttendance;
import com.softcore.vtpsales.AppUtils.AppUtil;
import com.softcore.vtpsales.Model.AttendanceModel;
import com.softcore.vtpsales.Model.CommanResorce;
import com.softcore.vtpsales.Model.CustomerModel;
import com.softcore.vtpsales.ViewModel.AttendanceListViewModel;
import com.softcore.vtpsales.ViewModel.CustomerViewModel;
import com.softcore.vtpsales.databinding.ActivityAttendenceListBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AttendenceListActivity extends AppCompatActivity {
    AdapterAttendance adapterAttendence;
    ActivityAttendenceListBinding binding;
    String DbName;
    String Flag;
    String FromDatePost;
    String ToDatePost;
    String ViewFromDate;
    String ViewToDate;
    String TYPE;
    String selectedSlpName;
    String SlpName;
    CustomerModel selectedmodel;
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

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        // Add 1 month to the current date
        calendar.add(Calendar.MONTH, 1);
        Date futureDate = calendar.getTime();

        SimpleDateFormat postfutureDate = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        SimpleDateFormat viewfutureDate = new SimpleDateFormat("yyyy-MMM-dd", Locale.getDefault());
        String postfuturedateString = postfutureDate.format(futureDate);
        String viewfuturedateString = viewfutureDate.format(futureDate);

        SimpleDateFormat postDate = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        SimpleDateFormat viewDate = new SimpleDateFormat("yyyy-MMM-dd", Locale.getDefault());
        String postdateString = postDate.format(currentDate);
        String viewdateString = viewDate.format(currentDate);

        FromDatePost = postfuturedateString;
        ToDatePost = postdateString;

        ViewFromDate = viewfuturedateString;
        ViewToDate = viewdateString;

        binding.edFromDate.setText(ViewFromDate);
        binding.edToDate.setText(ViewToDate);


        binding.edFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDatepicker();
            }
        });

        binding.edToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDatepicker();
            }
        });

        TYPE=getIntent().getStringExtra("type");
        System.out.println("List Type :"+TYPE);
        if(TYPE.equals("emp")){
            Flag = "Clock_IN_Out";
            binding.autoLay.setVisibility(View.GONE);
        }else if(TYPE.equals("cust")){
            Flag = "Clock_IN_Out_Customer";
            binding.autoLay.setVisibility(View.VISIBLE);
            getCustomerList();
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

                    binding.txtNoData.setVisibility(View.GONE);
                }else{
                    binding.txtNoData.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    public void fromDatepicker() {
        // Get current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        if (!binding.edFromDate.getText().toString().isEmpty()) {
            try {
                // Parse the date from the EditText
                Date fromDate = new SimpleDateFormat("yyyy-MMM-dd", Locale.getDefault())
                        .parse(binding.edFromDate.getText().toString());

                // Update the calendar to the parsed date
                calendar.setTime(fromDate);
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }




        // Create a new DatePickerDialog instance
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth);
                        // Create a SimpleDateFormat instance to format the date
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd", Locale.getDefault());

                        // Format the selected date
                        ViewFromDate = dateFormat.format(calendar.getTime());
                        binding.edFromDate.setText(ViewFromDate);


                        SimpleDateFormat dateFormatPost = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                        FromDatePost =  dateFormatPost.format(calendar.getTime());



                            GetData();

                    }
                }, year, month, day);


        // Show the Date Picker dialog
        datePickerDialog.show();
    }

    public void toDatepicker() {
        // Get current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        if (!binding.edToDate.getText().toString().isEmpty()) {
            try {
                // Parse the date from the EditText
                Date toDate = new SimpleDateFormat("yyyy-MMM-dd", Locale.getDefault())
                        .parse(binding.edToDate.getText().toString());

                // Update the calendar to the parsed date
                calendar.setTime(toDate);
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }




        // Create a new DatePickerDialog instance
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth);
                        // Create a SimpleDateFormat instance to format the date
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd", Locale.getDefault());

                        // Format the selected date
                        ViewToDate = dateFormat.format(calendar.getTime());
                        binding.edToDate.setText(ViewToDate);


                        SimpleDateFormat dateFormatPost = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                        ToDatePost =  dateFormatPost.format(calendar.getTime());


                            GetData();

                    }
                }, year, month, day);


        // Show the Date Picker dialog
        datePickerDialog.show();
    }

    private void GetData() {

        AppUtil.showProgressDialog(binding.getRoot(),"Loading");


        AttendanceListViewModel attendanceListViewModel = new ViewModelProvider(this).get(AttendanceListViewModel.class);
        attendanceListViewModel.getAttendanceListinfo(DbName,Flag).observe(this, new Observer<CommanResorce<List<AttendanceModel>>>() {
            @Override
            public void onChanged(CommanResorce<List<AttendanceModel>> listCommanResorce) {

                if (listCommanResorce.data != null && !listCommanResorce.data.isEmpty()) {



                    adapterAttendence.setData(listCommanResorce.data);


                    binding.txtNoData.setVisibility(View.GONE);
                }else {
                    binding.txtNoData.setVisibility(View.VISIBLE);
                }
                AppUtil.hideProgressDialog();
            }
        });


    }

    private void getCustomerList() {
        String dbName = AppUtil.getStringData(getApplicationContext(), "DatabaseName", "");

        CustomerViewModel customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
        customerViewModel.getActCusDetail(dbName, "Active_Customer_List").observe(this, new Observer<CommanResorce<List<CustomerModel>>>() {
            @Override
            public void onChanged(CommanResorce<List<CustomerModel>> listCommanResorce) {

                if (listCommanResorce.data != null && !listCommanResorce.data.isEmpty()) {

                    List<String> slpNames = new ArrayList<>();
                    List<CustomerModel> selectedModelList = new ArrayList<>();


                    for (CustomerModel database : listCommanResorce.data) {
                        slpNames.add(database.getCardName());
                        selectedModelList.add(database);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(AttendenceListActivity.this,
                            R.layout.simple_spinner_design, slpNames);

//                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

                    binding.autoCompleteTextView.setAdapter(adapter);
                    binding.autoCompleteTextView.setThreshold(1);
                    binding.autoCompleteTextView.setDropDownHeight(800);
                    binding.autoCompleteTextView.setOnClickListener(new View.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(View v) {
                                                                            binding.autoCompleteTextView.showDropDown();
                                                                        }
                                                                    }
                    );
                    binding.autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            selectedSlpName = slpNames.get(position);

                            if (!selectedSlpName.equals("SELECT CUSTOMER NAME")) {
                                SlpName = selectedSlpName;
                                selectedmodel = selectedModelList.get(position); // Adjust position by -1 to account for the "SELECT CUSTOMER NAME" item
                                System.out.println("code: " + selectedmodel.getCardCode());

                                GetData();

                            }
                        }
                    });

                    binding.autoCompleteTextView.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            adapter.getFilter().filter(s.toString());
                            System.out.println(s.toString());
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                        }
                    });

                } else {
                    // AppUtil.showTost(getApplicationContext(), "User not found. Please check your username.");
                    System.out.println("Customer Not found");
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