package com.softcore.vtpsales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.softcore.vtpsales.AppUtils.AppUtil;
import com.softcore.vtpsales.Model.CommanResorce;
import com.softcore.vtpsales.Model.GeneralModel;
import com.softcore.vtpsales.Model.LeadGenModel;
import com.softcore.vtpsales.Model.LeadSeriesModel;
import com.softcore.vtpsales.Model.SlpResponse;


import com.softcore.vtpsales.Network.ServiceLayerApi;
import com.softcore.vtpsales.ViewModel.ClpViewModel;
import com.softcore.vtpsales.ViewModel.CountryListViewModel;
import com.softcore.vtpsales.ViewModel.LeadSeriesViewModel;
import com.softcore.vtpsales.ViewModel.SEViewModel;


import com.softcore.vtpsales.ViewModel.StateListViewModel;
import com.softcore.vtpsales.databinding.ActivityLeadGenerationBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LeadGenerationActivity extends AppCompatActivity {

    ActivityLeadGenerationBinding binding;


    String selectedSalesEmp_Name;
    String selectedSalesEmp_Code;
    String SelectedCP_Name;
    String Dob;
    String AnniDate;

    List<LeadGenModel.BPAddress> AddressList ;

    LeadGenModel.ContactEmployee ContactModel;

    SlpResponse selSalesEmpModel;

    List<GeneralModel> StateList;
    GeneralModel selStateModel;
    String selStateCode;

    List<GeneralModel> CountryList;
    GeneralModel selCountryModel;
    String selCountryCode;

    private int loginAttempts = 0;
    private static final int MAX_LOGIN_ATTEMPTS = 1;

    //LeadGenModel.BPAddress

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLeadGenerationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AddressList = new ArrayList<>();

        binding.laybar.appbarTextView.setText("Lead Generation");

        binding.laybar.backId.setVisibility(View.VISIBLE);
        binding.laybar.backId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.etBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DobDatepicker();
            }
        });

        binding.etAnniversaryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnniDatepicker();
            }
        });


        String dbName = AppUtil.getStringData(getApplicationContext(), "DatabaseName", "");

        getLeadSeries(dbName);
        getCPList();
        getSEList();


        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginAttempts = 0;

                validateInput();


            }
        });

    }


    private void validateInput() {
        boolean isValid = true;

        if (TextUtils.isEmpty(binding.etLeadCode.getText())) {
            binding.tvLeadCodeError.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            binding.tvLeadCodeError.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(binding.etLeadName.getText())) {
            binding.tvLeadNameError.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            binding.tvLeadNameError.setVisibility(View.GONE);
        }

        if (selectedSalesEmp_Code == null) {
            binding.tvSalesPersonNameError.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            binding.tvSalesPersonNameError.setVisibility(View.GONE);
        }

        if (SelectedCP_Name == null) {
            binding.tvCollectionPersonError.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            binding.tvCollectionPersonError.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(binding.etAddress.getText())) {
            binding.tvAddressError.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            binding.tvAddressError.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(binding.etContactPerson.getText())) {
            binding.tvContactPersonError.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            binding.tvContactPersonError.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(binding.etMobileNo.getText())) {
            binding.tvMobileNoError.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            binding.tvMobileNoError.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(binding.etAnniversaryDate.getText())) {
            binding.tvAnniversaryDateError.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            binding.tvAnniversaryDateError.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(binding.etBirthDate.getText())) {
            binding.tvBirthDateError.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            binding.tvBirthDateError.setVisibility(View.GONE);
        }

        if (isValid) {
            PostData();

        }else {
            Toast.makeText(this, "Fill All Mandatory Fields", Toast.LENGTH_SHORT).show();
        }
    }


    private void PostData() {
        String CardCode = binding.etLeadCode.getText().toString().trim();
        String CardName = binding.etLeadName.getText().toString().trim();
        String ContactPerson = binding.etContactPerson.getText().toString().trim();
        String Dob =  binding.etBirthDate.getText().toString().trim();
        String MobNo =  binding.etMobileNo.getText().toString().trim();
        String ADate = "";
        if(binding.etAnniversaryDate.getText() != null){
            ADate =   binding.etAnniversaryDate.getText().toString().trim();
        }
        List<LeadGenModel.ContactEmployee> contactEmpList = new ArrayList<>();
        LeadGenModel.ContactEmployee contactEmployee = new LeadGenModel.ContactEmployee();
        contactEmployee.setCardCode(CardCode);
        contactEmployee.setName(ContactPerson);
        contactEmployee.setDateOfBirth(Dob);
        contactEmployee.setU_AnnDate(ADate);
        contactEmployee.setMobilePhone(MobNo);
        contactEmpList.add(contactEmployee);

        LeadGenModel leadGenModel = new LeadGenModel();
        leadGenModel.setCardCode(CardCode);
        leadGenModel.setCardName(CardName);
        leadGenModel.setSeries("634");
        leadGenModel.setCardType("cLid");
        leadGenModel.setGroupCode(130);
        leadGenModel.setContactPerson(ContactPerson);
        leadGenModel.setCurrency("INR");
        leadGenModel.setValid("tNO");
        leadGenModel.setFrozen("tYES");
        leadGenModel.setSalesPersonCode(Integer.parseInt(selectedSalesEmp_Code));
        leadGenModel.setU_CollectionPerson(SelectedCP_Name);
        leadGenModel.setBPAddresses(AddressList);
        leadGenModel.setContactEmployees(contactEmpList);

        SaveLead(leadGenModel);

    }

    private void SaveLead(LeadGenModel leadGenModel) {

        AppUtil.showProgressDialog(binding.getRoot(),"Loading");

        if (AppUtil.isNetworkAvailable(getApplicationContext())) {
            ServiceLayerApi.doSapLogin(LeadGenerationActivity.this, new ServiceLayerApi.ApiCallback() {
                @Override
                public void onLoginSuccess() {

                    ServiceLayerApi.doLeadCall(LeadGenerationActivity.this,leadGenModel, new ServiceLayerApi.ApiCallback() {
                        @Override
                        public void onLoginSuccess() {
                            Toast.makeText(LeadGenerationActivity.this, "Lead Generated Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LeadGenerationActivity.this, MainActivity2.class);
                            startActivity(intent);
                            finish();
                            AppUtil.hideProgressDialog();
                        }

                        @Override
                        public void onLoginFailure() {
                            AppUtil.hideProgressDialog();
                        }
                    });

                }

                @Override
                public void onLoginFailure() {
//                Boolean  isRetryingLogin = false;
//                loginAttempts++;
//                if (loginAttempts <= MAX_LOGIN_ATTEMPTS) {
//                    // Retry login
//                    ServiceLayerApi.doSapLogin(LeadGenerationActivity.this, this);
//                } else {
                    AppUtil.hideProgressDialog();
                    Toast.makeText(LeadGenerationActivity.this, "Session Refreshing Failed", Toast.LENGTH_SHORT).show();
                    //   }
                }

            });
        } else {
            Toast.makeText(this, "No Internet Available", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LeadGenerationActivity.this, MainActivity2.class);
            startActivity(intent);
            finish();
        }





    }


    public void DobDatepicker() {
        // Get current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        if (!binding.etBirthDate.getText().toString().isEmpty()) {
            try {
                // Parse the date from the EditText

                Date fromDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        .parse(binding.etBirthDate.getText().toString());

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
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                        // Format the selected date
                        Dob = dateFormat.format(calendar.getTime());
                        binding.etBirthDate.setText(Dob);

                    }
                }, year, month, day);


        // Show the Date Picker dialog
        datePickerDialog.show();
    }
    public void AnniDatepicker() {
        // Get current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        if (!binding.etAnniversaryDate.getText().toString().isEmpty()) {
            try {
                // Parse the date from the EditText

                Date anniDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        .parse(binding.etAnniversaryDate.getText().toString());

                // Update the calendar to the parsed date
                calendar.setTime(anniDate);
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
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                        // Format the selected date
                        AnniDate = dateFormat.format(calendar.getTime());
                        binding.etAnniversaryDate.setText(AnniDate);

                    }
                }, year, month, day);


        // Show the Date Picker dialog
        datePickerDialog.show();
    }
    private void getCPList() {

        ClpViewModel clpViewModel = new ViewModelProvider(this).get(ClpViewModel.class);
        clpViewModel.getClpData().observe(this, new Observer<CommanResorce<List<SlpResponse>>>() {
            @Override
            public void onChanged(CommanResorce<List<SlpResponse>> listCommanResorce) {

                if (listCommanResorce.data != null && !listCommanResorce.data.isEmpty()) {


                    getCollectionPerson(listCommanResorce.data);


                } else {
                    // AppUtil.showTost(getApplicationContext(), "User not found. Please check your username.");
                    System.out.println("Customer Not found");
                }


            }
        });
    }
    private void getSEList() {

        SEViewModel seViewModel = new ViewModelProvider(this).get(SEViewModel.class);
        seViewModel.getSEData().observe(this, new Observer<CommanResorce<List<SlpResponse>>>() {
            @Override
            public void onChanged(CommanResorce<List<SlpResponse>> listCommanResorce) {

                if (listCommanResorce.data != null && !listCommanResorce.data.isEmpty()) {

                    getSalesEmployee(listCommanResorce.data);


                } else {
                    // AppUtil.showTost(getApplicationContext(), "User not found. Please check your username.");
                    System.out.println("Customer Not found");
                }


            }
        });
    }
    private void getLeadSeries(String dbName) {

        LeadSeriesViewModel viewModel = new ViewModelProvider(this).get(LeadSeriesViewModel.class);
        viewModel.getLeadSeries(dbName).observe(this, new Observer<CommanResorce<List<LeadSeriesModel>>>() {
            @Override
            public void onChanged(CommanResorce<List<LeadSeriesModel>> listCommanResorce) {

                if (listCommanResorce.data != null && !listCommanResorce.data.isEmpty()) {

                    if(listCommanResorce.data.get(0).getNextNumber() != null){
                        String leadcode = "L"+listCommanResorce.data.get(0).getNextNumber();
                        binding.etLeadCode.setText(leadcode);
                        System.out.println("Lead Next Number :"+listCommanResorce.data.get(0).getNextNumber());
                    }
 
                } else {
                    // AppUtil.showTost(getApplicationContext(), "User not found. Please check your username.");
                    System.out.println("Customer Not found");
                }


            } 
        });
    }
    private void getStatelist(View dialogView) {

        StateListViewModel viewModel = new ViewModelProvider(this).get(StateListViewModel.class);
        viewModel.getStateList().observe(this, new Observer<CommanResorce<List<GeneralModel>>>() {
            @Override
            public void onChanged(CommanResorce<List<GeneralModel>> listCommanResorce) {

                if (listCommanResorce.data != null && !listCommanResorce.data.isEmpty()) {

                    getStateData(listCommanResorce.data,dialogView);


                } else {
                    // AppUtil.showTost(getApplicationContext(), "User not found. Please check your username.");
                    System.out.println("State Not found");
                }


            }
        });
    }
    private void getCountrylist(View dialogView) {

        CountryListViewModel viewModel = new ViewModelProvider(this).get(CountryListViewModel.class);
        viewModel.getCountryList().observe(this, new Observer<CommanResorce<List<GeneralModel>>>() {
            @Override
            public void onChanged(CommanResorce<List<GeneralModel>> listCommanResorce) {

                if (listCommanResorce.data != null && !listCommanResorce.data.isEmpty()) {

                    getCountryData(listCommanResorce.data,dialogView);


                } else {
                    // AppUtil.showTost(getApplicationContext(), "User not found. Please check your username.");
                    System.out.println("Country Not found");
                }


            }
        });
    }

    private void getStateData(List<GeneralModel> data, View dialogView) {
        List<String> names = new ArrayList<>();
        List<GeneralModel> genModel = new ArrayList<>();


        for (GeneralModel model : data) {
            names.add(model.getName());
            genModel.add(model);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(LeadGenerationActivity.this,
                R.layout.simple_spinner_design, names);

//                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        AutoCompleteTextView edSate = dialogView.findViewById(R.id.autotxtState);

        edSate.setAdapter(adapter);

        edSate.setDropDownHeight(400);
        edSate.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        edSate.setOnClickListener(new View.OnClickListener() {
                                                           @Override
                                                           public void onClick(View v) {
                                                               edSate.showDropDown();
                                                           }
                                                       }
        );
        edSate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selectedName = adapter.getItem(position);
                GeneralModel selectedModel = null;
                for (GeneralModel model : data) {
                    if (model.getName().equals(selectedName)) {
                        selectedModel = model;
                        break;
                    }
                }
                if (selectedModel != null) {

                    selStateCode = selectedModel.getCode();
                    System.out.println("selStateCode : " + selStateCode);
                    // GetData();
                }
                //selStateModel = names.get(position);
//
//                selStateCode = genModel.get(position).getCode(); aaa
//
//                System.out.println("selStateCode code: " + selStateCode);

                // GetData();

            }
        });

        edSate.addTextChangedListener(new TextWatcher() {
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

    }
    private void getCountryData(List<GeneralModel> data, View dialogView) {
        List<String> names = new ArrayList<>();
        List<GeneralModel> genModel = new ArrayList<>();


        for (GeneralModel model : data) {
            names.add(model.getName());
            genModel.add(model);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(LeadGenerationActivity.this,
                R.layout.simple_spinner_design, names);

//                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        AutoCompleteTextView edCountry = dialogView.findViewById(R.id.autotxtCountry);

        edCountry.setAdapter(adapter);

        edCountry.setDropDownHeight(400);
        edCountry.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        edCountry.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          edCountry.showDropDown();
                                      }
                                  }
        );
        edCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selectedName = adapter.getItem(position);
                GeneralModel selectedModel = null;
                for (GeneralModel model : data) {
                    if (model.getName().equals(selectedName)) {
                        selectedModel = model;
                        break;
                    }
                }
                if (selectedModel != null) {

                    selCountryCode = selectedModel.getCode();
                    System.out.println("selStateCode : " + selCountryCode);
                    // GetData();
                }

            }
        });

        edCountry.addTextChangedListener(new TextWatcher() {
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

    }


    private void getCollectionPerson(List<SlpResponse> data) {
        List<String> slpNames = new ArrayList<>();
        List<SlpResponse> selectedModelList = new ArrayList<>();


        for (SlpResponse database : data) {
            slpNames.add(database.getSlpName());
            selectedModelList.add(database);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(LeadGenerationActivity.this,
                R.layout.simple_spinner_design, slpNames);

//                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        binding.autotxtCP.setAdapter(adapter);
        binding.autotxtCP.setThreshold(1);
        binding.autotxtCP.setDropDownHeight(400);
        binding.autotxtCP.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        binding.autotxtCP.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     binding.autotxtCP.showDropDown();
                                                 }
                                             }
        );
        binding.autotxtCP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
                SelectedCP_Name = slpNames.get(position);
            }
        });

        binding.autotxtCP.addTextChangedListener(new TextWatcher() {
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

        binding.etAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
            }
        });


    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.activity_add_address, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        EditText edStreet = dialogView.findViewById(R.id.edStreet);
        EditText edBlock = dialogView.findViewById(R.id.edBlock);
        EditText edCity = dialogView.findViewById(R.id.edCity);
//        EditText edState = dialogView.findViewById(R.id.edState);
//        EditText edCountry = dialogView.findViewById(R.id.edCountry);

        AutoCompleteTextView edState  = dialogView.findViewById(R.id.autotxtState);
        AutoCompleteTextView edCountry  = dialogView.findViewById(R.id.autotxtCountry);

        EditText edZipcode = dialogView.findViewById(R.id.edZipcode);

        TextView submitButton = dialogView.findViewById(R.id.txtSubmit);
        TextView cancelButton = dialogView.findViewById(R.id.txtCancel);

        getStatelist(dialogView);
        getCountrylist(dialogView);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edStreet == null || edBlock == null || edCity == null || edState == null || edCountry == null|| edZipcode == null){

                    Toast.makeText(LeadGenerationActivity.this, "Fill all Fields", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    String AddressName = "";
                    String AddressType = "";

                    String combinedText = edStreet.getText().toString() + ", " +
                            edBlock.getText().toString() + ", " +
                            edCity.getText().toString() + ", " +
                            edState.getText().toString() + ", " +
                            edCountry.getText().toString() + "- " +
                            edZipcode.getText().toString();
                    binding.etAddress.setText(combinedText);

                    for (int a = 0; a < 2; a++) {
                        if (a % 2 == 0) {
                            AddressName = "BILL TO ID";
                            AddressType = "bo_BillTo";

                        }
                        else {
                            AddressName = "SHIP TO ID";
                            AddressType = "bo_ShipTo";

                        }

                        LeadGenModel.BPAddress bModel = new LeadGenModel.BPAddress();
                        bModel.setAddressName(AddressName);
                        bModel.setAddressType(AddressType);
                        bModel.setStreet(edStreet.getText().toString().trim());
                        bModel.setBlock(edBlock.getText().toString().trim());
                        bModel.setCity(edCity.getText().toString().trim());
                        bModel.setState(selStateCode);
                        bModel.setCountry(selCountryCode);
                        bModel.setZipCode(edZipcode.getText().toString().trim());

                        if(AddressList != null){
                            for (int i = 0; i < AddressList.size(); i++) {
                                LeadGenModel.BPAddress existingAddress = AddressList.get(i);
                                if (existingAddress.getAddressName().equals(bModel.getAddressName())) {
                                    // Replace existing address with new one
                                    AddressList.set(i, bModel);
                                    break; // Exit loop since address is replaced
                                }
                            }
                            if (!AddressList.contains(bModel)) {
                                AddressList.add(bModel);
                            }
                        }else {
                            AddressList.add(bModel);
                        }
                    }

                    for (LeadGenModel.BPAddress address : AddressList) {
                        System.out.println("Address Name: " + address.getAddressName());
                        System.out.println("Address Type: " + address.getAddressType());
                        System.out.println("Street: " + address.getStreet());
                        System.out.println("Block: " + address.getBlock());
                        System.out.println("City: " + address.getCity());
                        System.out.println("State: " + address.getState());
                        System.out.println("Country: " + address.getCountry());
                        System.out.println("Zip Code: " + address.getZipCode());

                        System.out.println("--------------------");
                    }


                }


                 dialog.dismiss();
            }
    });





        dialog.show();
    }

    private void getSalesEmployee(List<SlpResponse> data) {
        List<String> slpNames = new ArrayList<>();
        List<SlpResponse> selectedModelList = new ArrayList<>();


        for (SlpResponse database : data) {
            slpNames.add(database.getSlpName());
            selectedModelList.add(database);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(LeadGenerationActivity.this,
                R.layout.simple_spinner_design, slpNames);

//                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        binding.autoSalesPersonName.setAdapter(adapter);
        binding.autoSalesPersonName.setDropDownHeight(400);
        binding.autoSalesPersonName.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        binding.autoSalesPersonName.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     binding.autoSalesPersonName.showDropDown();
                                                 }
                                             }
        );
        binding.autoSalesPersonName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selectedName = adapter.getItem(position);
                SlpResponse selectedModel = null;
                for (SlpResponse model : data) {
                    if (model.getSlpName().equals(selectedName)) {
                        selectedModel = model;
                        break;
                    }
                }
                if (selectedModel != null) {
                    selectedSalesEmp_Name = selectedModel.getSlpName();
                    selectedSalesEmp_Code = selectedModel.getSlpCode();
                    System.out.println("SalesEmp code: " + selectedSalesEmp_Code);
                    // GetData();
                }
            }
        });

        binding.autoSalesPersonName.addTextChangedListener(new TextWatcher() {
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