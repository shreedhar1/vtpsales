package com.softcore.vtpsales;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.softcore.vtpsales.AppUtils.AppUtil;
import com.softcore.vtpsales.Model.CommanResorce;
import com.softcore.vtpsales.Model.Database;
import com.softcore.vtpsales.Model.SPModel;
import com.softcore.vtpsales.Model.UserModel;
import com.softcore.vtpsales.ViewModel.DatabaseListViewModel;
import com.softcore.vtpsales.ViewModel.LoginViewModel;
import com.softcore.vtpsales.ViewModel.SalesPersonsViewModel;
import com.softcore.vtpsales.databinding.ActivityLoginBinding;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    String selectedDatabaseName = "";
    private static final int TIME_INTERVAL = 2000; // Time between two back presses in milliseconds
    private long backPressedTime;
    String rememberme = "N";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getSupportActionBar().hide();
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       // setContentView(R.layout.activity_login);
        View rootView = binding.getRoot();

        rememberme = AppUtil.getStringData(getApplicationContext(),"Rem","");


        if(rememberme.equals("Y")){
            String userName = AppUtil.getStringData(getApplicationContext(),"EmpCode","");
            String password = AppUtil.getStringData(getApplicationContext(),"EmpPass","");
            String dbName = AppUtil.getStringData(getApplicationContext(),"DatabaseName","");
            String rem = AppUtil.getStringData(getApplicationContext(),"Rem","");

             rememberme = rem;
             binding.edUsername.setText(userName);
             binding.edPassword.setText(password);
             selectedDatabaseName = dbName;




        }
        if(rememberme.equals("Y")){
            binding.rememberCheckBox.setChecked(true);
        }

        setImage();


        GetDatabseList(rootView);


       // binding.spinnerDbName

        binding.rememberCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                rememberme = "Y";
            } else {
                rememberme = "N";
            }
        });



        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppUtil.showProgressDialog(binding.getRoot(),"Loading");

                String dbName = selectedDatabaseName.trim();
                String userName = binding.edUsername.getText().toString().trim();
                String password = binding.edPassword.getText().toString().trim();

                if (dbName.equals("SELECT DATABASE")) {
                    binding.errorMessageDb.setVisibility(View.VISIBLE);
                } else {
                    binding.errorMessageDb.setVisibility(View.GONE);
                }

                if (userName.isEmpty()) {
                    binding.errorMessageUsername.setVisibility(View.VISIBLE);
                } else {
                    binding.errorMessageUsername.setVisibility(View.GONE);
                }

                if (password.isEmpty()) {
                    binding.errorMessagePassword.setVisibility(View.VISIBLE);
                } else {
                    binding.errorMessagePassword.setVisibility(View.GONE);
                }

                if (!dbName.isEmpty() && !userName.isEmpty() && !password.isEmpty()) {
                    GetLoginDetails(dbName, userName, password);
                }
                AppUtil.hideProgressDialog();

            }
        });

    }

    private void setImage() {
        switch (selectedDatabaseName) {
            case "ARRHUM_LIVE":
                binding.imglogo.setImageResource(R.drawable.img_logo_arrhum);
                break;
            case "ENVIIRO_LIVE":
                binding.imglogo.setImageResource(R.drawable.img_enviiro);
                break;
            case "VTP_LIVE":
                binding.imglogo.setImageResource(R.drawable.logo);
                break;
            case "TEST_ENV_20231124":
                binding.imglogo.setImageResource(R.drawable.applogowhite);
                break;
        }
    }

    private void GetLoginDetails(String DbName,String username, String password) {
        System.out.println(DbName+" "+username+" "+password);
    // list_branch=new ArrayList<>();
        System.out.println("here");
        LoginViewModel loginViewModel= new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.getloginInfo(DbName,username,password).observe(this, new Observer<CommanResorce<List<UserModel>>>() {
            @Override
            public void onChanged(CommanResorce<List<UserModel>> listCommanResorce) {

                if (listCommanResorce.data != null && !listCommanResorce.data.isEmpty()) {
                    String apiPassword = listCommanResorce.data.get(0).getUser_Password();
                    if (apiPassword.equals(password)) {
                        AppUtil.saveStringData(getApplicationContext(),"Rem",rememberme);
                        String EmpId = listCommanResorce.data.get(0).getEmpID();
                        String EmpType = listCommanResorce.data.get(0).getType();
                        String CompnyName = listCommanResorce.data.get(0).getCompnyName();
                        String CompnyAddr = listCommanResorce.data.get(0).getCompnyAddr();
                        String Building = listCommanResorce.data.get(0).getBuilding();
                        String IntrntAdrs = listCommanResorce.data.get(0).getIntrntAdrs();
                        String Phone1 = listCommanResorce.data.get(0).getPhone1();
                        String E_Mail = listCommanResorce.data.get(0).getE_Mail();
                        AppUtil.saveStringData(getApplicationContext(),"EmpTypePName",listCommanResorce.data.get(0).getSales_Employee());
                        AppUtil.saveStringData(getApplicationContext(),"EmpCode",listCommanResorce.data.get(0).getEMP_Code());
                        AppUtil.saveStringData(getApplicationContext(),"EmpName",listCommanResorce.data.get(0).getFirstName()+" " +listCommanResorce.data.get(0).getLastName());
                        AppUtil.saveStringData(getApplicationContext(),"EmpEmail",listCommanResorce.data.get(0).getEmail());
                        AppUtil.saveStringData(getApplicationContext(),"EmpMob",listCommanResorce.data.get(0).getMobile());
                        AppUtil.saveStringData(getApplicationContext(),"EmpPass",listCommanResorce.data.get(0).getUser_Password());
                        AppUtil.saveStringData(getApplicationContext(),"EmpID",EmpId);
                        AppUtil.saveStringData(getApplicationContext(),"EmpType",EmpType);


                        AppUtil.saveStringData(getApplicationContext(),"ComName",CompnyName);
                        AppUtil.saveStringData(getApplicationContext(),"ComAddr",CompnyAddr);
                        AppUtil.saveStringData(getApplicationContext(),"ComBuilding",Building);
                        AppUtil.saveStringData(getApplicationContext(),"ComWebsite",IntrntAdrs);
                        AppUtil.saveStringData(getApplicationContext(),"ComPhone1",Phone1);
                        AppUtil.saveStringData(getApplicationContext(),"ComE_Mail",E_Mail);

                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                        getSplPersons(DbName,EmpId);

                        // Other actions after successful login
                        AppUtil.hideProgressDialog();
                        startActivity(new Intent(LoginActivity.this,MainActivity2.class));

                    } else {
                        AppUtil.showTost(getApplicationContext(), "Password incorrect. Please try again.");
                        System.out.println("Password incorrect");
                        AppUtil.hideProgressDialog();
                    }
                } else {
                    AppUtil.showTost(getApplicationContext(), "User not found. Please check your username.");
                    System.out.println("User not found");
                    AppUtil.hideProgressDialog();
                }

            }
        });



    }
    private void getSplPersons(String DbName,String EmpId) {
        System.out.println(DbName+" "+EmpId);
        // list_branch=new ArrayList<>();
        System.out.println("here");



        SalesPersonsViewModel viewModel= new ViewModelProvider(this).get(SalesPersonsViewModel.class);
        viewModel.getSalesPersonInfo(DbName,EmpId).observe(this, new Observer<CommanResorce<List<SPModel>>>() {
            @Override
            public void onChanged(CommanResorce<List<SPModel>> listCommanResorce) {

                if (listCommanResorce.data != null && !listCommanResorce.data.isEmpty()) {


                    String AllSalesPerson = "";

                    for(int i = 0;i<listCommanResorce.data.size();i++){

                        if(AllSalesPerson.equals("")){
                            AllSalesPerson = listCommanResorce.data.get(i).getSalesPrson();
                        }else {
                            AllSalesPerson = AllSalesPerson.trim()+","+listCommanResorce.data.get(i).getSalesPrson();
                        }
                    }

                    if(AllSalesPerson == null){
                        AppUtil.saveStringData(getApplicationContext(),"AllSalesPerson",EmpId);
                    }else {
                        AppUtil.saveStringData(getApplicationContext(),"AllSalesPerson",AllSalesPerson);
                    }
                    String SlpStored = AppUtil.getStringData(getApplicationContext(),"AllSalesPerson","");
                    System.out.println("AllSalesPerson"+SlpStored);
//                    String apiPassword = listCommanResorce.data.get(0).getUser_Password();
//                    if (apiPassword.equals(password)) {
//                        AppUtil.saveStringData(getApplicationContext(),"Rem",rememberme);
//
//                        AppUtil.saveStringData(getApplicationContext(),"EmpCode",listCommanResorce.data.get(0).getEMP_Code());
//                        AppUtil.saveStringData(getApplicationContext(),"EmpName",listCommanResorce.data.get(0).getFirstName()+" " +listCommanResorce.data.get(0).getLastName());
//                        AppUtil.saveStringData(getApplicationContext(),"EmpEmail",listCommanResorce.data.get(0).getEmail());
//                        AppUtil.saveStringData(getApplicationContext(),"EmpMob",listCommanResorce.data.get(0).getMobile());
//                        AppUtil.saveStringData(getApplicationContext(),"EmpPass",listCommanResorce.data.get(0).getUser_Password());
//                        AppUtil.saveStringData(getApplicationContext(),"EmpID",listCommanResorce.data.get(0).getEmpID());
//
//                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
//
//
//
//                        // Other actions after successful login
//                        AppUtil.hideProgressDialog();
//                        startActivity(new Intent(LoginActivity.this,MainActivity2.class));
//
//                    } else {
//                        AppUtil.showTost(getApplicationContext(), "Password incorrect. Please try again.");
//                        System.out.println("Password incorrect");
//                        AppUtil.hideProgressDialog();
//                    }
                } else {
                    AppUtil.showTost(getApplicationContext(), "User not found. Please check your username.");
                    System.out.println("User not found");
                    AppUtil.hideProgressDialog();
                }

            }
        });



    }



    void GetDatabseList(View rootView) {

        AppUtil.showProgressDialog(rootView,"Loading Database");
        DatabaseListViewModel dbListViewModel= new ViewModelProvider(this).get(DatabaseListViewModel.class);
        dbListViewModel.getDatabaseList().observe(this, new Observer<CommanResorce<List<Database>>>() {
            @Override
            public void onChanged(CommanResorce<List<Database>> listCommanResorce) {

                if (listCommanResorce.data != null && !listCommanResorce.data.isEmpty()) {

                    System.out.println("Test database");
                    List<String> databaseNames = new ArrayList<>();
                    databaseNames.add("SELECT DATABASE");

                    for (Database database : listCommanResorce.data) {
                        databaseNames.add(database.getDatabaseName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(LoginActivity.this,
                            R.layout.simple_spinner_layout, databaseNames);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    binding.spinnerDbName.setAdapter(adapter);

                    binding.spinnerDbName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                             selectedDatabaseName = databaseNames.get(position);
                            // Store or use the selected database name as needed
                            AppUtil.saveStringData(getApplicationContext(),"DatabaseName",selectedDatabaseName);
                            setImage();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {
                            // Do nothing
                        }
                    });
                    if (selectedDatabaseName != null) {
                        // Find the position of the selected database name in the list
                        int index = databaseNames.indexOf(selectedDatabaseName);
                        // Set the selected item in the spinner
                        if (index != -1) {
                            binding.spinnerDbName.setSelection(index);
                        }
                    }
                    AppUtil.hideProgressDialog();
                } else {
                    AppUtil.showTost(getApplicationContext(), "Failed: "+listCommanResorce.message);
                    AppUtil.hideProgressDialog();
                }

                AppUtil.hideProgressDialog();
            }
        });



    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();

            return;
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }

}