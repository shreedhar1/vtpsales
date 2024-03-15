package com.softcore.vtpsales;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.softcore.vtpsales.AppUtils.AppUtil;
import com.softcore.vtpsales.Model.CommanResorce;
import com.softcore.vtpsales.Model.Database;
import com.softcore.vtpsales.Model.UserModel;
import com.softcore.vtpsales.ViewModel.DatabaseListViewModel;
import com.softcore.vtpsales.ViewModel.LoginViewModel;
import com.softcore.vtpsales.databinding.ActivityDashboardBinding;
import com.softcore.vtpsales.databinding.ActivityLoginBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    String selectedDatabaseName = "";
    private static final int TIME_INTERVAL = 2000; // Time between two back presses in milliseconds
    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getSupportActionBar().hide();
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       // setContentView(R.layout.activity_login);

        GetDatabseList();


       // binding.spinnerDbName



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

                        AppUtil.saveStringData(getApplicationContext(),"EmpCode",listCommanResorce.data.get(0).getEMP_Code());
                        AppUtil.saveStringData(getApplicationContext(),"EmpName",listCommanResorce.data.get(0).getFirstName()+" " +listCommanResorce.data.get(0).getLastName());

                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
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

     void GetDatabseList() {

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

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {
                            // Do nothing
                        }
                    });

                } else {
                    AppUtil.showTost(getApplicationContext(), "User not found. Please check your username.");
                    System.out.println("User not found");
                    AppUtil.hideProgressDialog();
                }


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