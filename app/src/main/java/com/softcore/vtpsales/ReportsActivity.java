package com.softcore.vtpsales;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import com.softcore.vtpsales.AppUtils.AppUtil;
import com.softcore.vtpsales.Model.BalanceDueResponse;
import com.softcore.vtpsales.Model.CommanResorce;
import com.softcore.vtpsales.Model.SlpResponse;
import com.softcore.vtpsales.ViewModel.BalDueViewModel;
import com.softcore.vtpsales.ViewModel.OutsBalDueViewModel;
import com.softcore.vtpsales.ViewModel.PurchaseBalDueViewModel;
import com.softcore.vtpsales.ViewModel.ReceiptBalDueViewModel;
import com.softcore.vtpsales.ViewModel.SlpViewModel;
import com.softcore.vtpsales.databinding.ActivityReportsBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReportsActivity extends AppCompatActivity {

    ActivityReportsBinding binding;
    String FromDatePost;
    String ToDatePost;
    String ViewFromDate;
    String ViewToDate;
    String SlpName;
    String selectedSlpName;

    String SalesBldue;
    String PurchaseBldue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= ActivityReportsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.laybar.appbarTextView.setText("Reports");

        binding.laybar.backId.setVisibility(View.VISIBLE);
        binding.laybar.backId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.imgSales.setImageResource(R.drawable.svg_sales);
        binding.txtSalestitle.setText("-");
        binding.txtSalesdesc.setText("Sales-Credit Note (Gross)");

        binding.imgPurchase.setImageResource(R.drawable.svg_purchase);
        binding.txtPurchasetitle.setText("-");
        binding.txtPurchasedesc.setText("Purchase - Debit Note (Gross)");

        binding.imgReceipt.setImageResource(R.drawable.svg_receipt);
        binding.txtReceiptitle.setText("-");
        binding.txtReceiptdesc.setText("Customer Outstanding");

        binding.imgOutstandings.setImageResource(R.drawable.svg_outstanding);
        binding.txtOutstandingstitle.setText("-");
        binding.txtOutstandingsdesc.setText("Vendor Outstaning");

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

        GetSlpList();

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
        binding.laySales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextActivity("Sales","Sales_Customer_Wise_Report");
            }
        });
        binding.layPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextActivity("Purchase","Purchase_Vendor_Wise_Report");
            }
        });
        binding.layReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextActivity("Customer Outstanding","CustomerWise_Outstanding_Amount");
            }
        });
        binding.layOutstandings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextActivity("Vendor Outstanding","CustomerWise_Vendor_Outstanding_Amount");
            }
        });



    }

    private void NextActivity(String Type, String Flag) {

        Intent intent = new Intent(ReportsActivity.this, ReportsListActivity.class);
        intent.putExtra("TYPE", Type);
        intent.putExtra("FromDatePost",FromDatePost);
        intent.putExtra("ToDatePost",ToDatePost);
        intent.putExtra("ViewToDate",ViewToDate);
        intent.putExtra("ViewFromDate",ViewFromDate);
        intent.putExtra("SlpName",SlpName);
        intent.putExtra("Flag",Flag);
        intent.putExtra("Amount",binding.txtSalestitle.getText().toString().trim());
        startActivity(intent);
    }


    void GetSlpList() {


        String DbName = AppUtil.getStringData(getApplicationContext(), "DatabaseName", "");

        SlpViewModel slpViewModel = new ViewModelProvider(this).get(SlpViewModel.class);
        slpViewModel.getSlpData(DbName, "Sales_Employee_List").observe(this, new Observer<CommanResorce<List<SlpResponse>>>() {
            @Override
            public void onChanged(CommanResorce<List<SlpResponse>> listCommanResorce) {

                if (listCommanResorce.data != null && !listCommanResorce.data.isEmpty()) {

                    List<String> slpnames = new ArrayList<>();

                    slpnames.add("SELECT NAME");

                    for (SlpResponse database : listCommanResorce.data) {
                        slpnames.add(database.getSlpName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(ReportsActivity.this,
                            R.layout.simple_spinner_layout_black, slpnames);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    binding.spinnerSlpName.setAdapter(adapter);

                    binding.spinnerSlpName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                            selectedSlpName = slpnames.get(position);

                            if (!selectedSlpName.equals("SELECT NAME")) {
                                SlpName = selectedSlpName;
                                GetData();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {
                            // Do nothing
                        }
                    });

                } else {
                    // AppUtil.showTost(getApplicationContext(), "User not found. Please check your username.");
                    System.out.println("Slp Not found");
                }
                AppUtil.hideProgressDialog();
            }
        });
    }

    private void GetData() {

        GetSalesBalceDue();
        GetPurchaseBalceDue();
        GetReceiptBalceDue();
        GetOutstandingBalceDue();

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


                        if(!selectedSlpName.equals("SELECT NAME")){
                            GetData();
                        }
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

                        if(!selectedSlpName.equals("SELECT NAME")){
                            GetData();
                        }
                    }
                }, year, month, day);


        // Show the Date Picker dialog
        datePickerDialog.show();
    }

    private void GetSalesBalceDue() {

        AppUtil.showProgressDialog(binding.getRoot(),"Loading");
        String DbName = AppUtil.getStringData(getApplicationContext(),"DatabaseName","");

        System.out.println("Get sAles List : "+FromDatePost+" "+ToDatePost+" "+ SlpName+" "+ DbName+" "+ "Sales_Amount");
        BalDueViewModel balDueViewModel= new ViewModelProvider(this).get(BalDueViewModel.class);
        balDueViewModel.getBalanceDueData(FromDatePost,ToDatePost, SlpName, DbName, "Sales_Amount").observe(this, new Observer<CommanResorce<List<BalanceDueResponse>>>() {
            @Override
            public void onChanged(CommanResorce<List<BalanceDueResponse>> listCommanResorce) {

                if (listCommanResorce.data != null && !listCommanResorce.data.isEmpty()) {

                    if(listCommanResorce.data.get(0).getBalanceDue() != null){
                        String BalanceDue = listCommanResorce.data.get(0).getBalanceDue();
                        System.out.println("Sales BalanceDue : "+BalanceDue);

                        binding.txtSalestitle.setText("₹ "+BalanceDue);

                        //SalesBldue = BalanceDue;

                    }else {
                        binding.txtSalestitle.setText("-");
                    }

                }else {
                    binding.txtSalestitle.setText("-");
                }

                AppUtil.hideProgressDialog();
            }
        });


    }
    private void GetPurchaseBalceDue() {

        //  AppUtil.showProgressDialog(binding.getRoot(),"Loading");
        String DbName = AppUtil.getStringData(getApplicationContext(),"DatabaseName","");

        System.out.println("Get purchase List : "+FromDatePost+" "+ToDatePost+" "+ SlpName+" "+ DbName+" "+ "Purchase_Amount");
        PurchaseBalDueViewModel balDueViewModel= new ViewModelProvider(this).get(PurchaseBalDueViewModel.class);
        balDueViewModel.getPuchaseBalanceDueData(FromDatePost,ToDatePost, SlpName, DbName, "Purchase_Amount").observe(this, new Observer<CommanResorce<List<BalanceDueResponse>>>() {
            @Override
            public void onChanged(CommanResorce<List<BalanceDueResponse>> listCommanResorce) {

                if (listCommanResorce.data != null && !listCommanResorce.data.isEmpty()) {

                    if(listCommanResorce.data.get(0).getBalanceDue() != null){
                        String BalanceDue = listCommanResorce.data.get(0).getBalanceDue();
                        System.out.println("Puchase BalanceDue : "+BalanceDue);

                        binding.txtPurchasetitle.setText("₹ "+BalanceDue);
                    }else {
                        binding.txtPurchasetitle.setText("-");
                    }


                }
                    else {
                        binding.txtPurchasetitle.setText("-");
                    }


//                AppUtil.hideProgressDialog();
            }
        });


    }
    private void GetReceiptBalceDue() {

        //  AppUtil.showProgressDialog(binding.getRoot(),"Loading");
        String DbName = AppUtil.getStringData(getApplicationContext(),"DatabaseName","");

        System.out.println("Get Receipt List : "+FromDatePost+" "+ToDatePost+" "+ SlpName+" "+ DbName+" "+ "Customer_Outstanding_Amount");
        ReceiptBalDueViewModel receiptBalDueViewModel= new ViewModelProvider(this).get(ReceiptBalDueViewModel.class);
        receiptBalDueViewModel.getReceiptBalanceDueData(FromDatePost,ToDatePost, SlpName, DbName, "Customer_Outstanding_Amount").observe(this, new Observer<CommanResorce<List<BalanceDueResponse>>>() {
            @Override
            public void onChanged(CommanResorce<List<BalanceDueResponse>> listCommanResorce) {

                if (listCommanResorce.data != null && !listCommanResorce.data.isEmpty()) {

                    if(listCommanResorce.data.get(0).getBalanceDue() != null){
                        String BalanceDue = listCommanResorce.data.get(0).getBalanceDue();
                        System.out.println("Receipt BalanceDue : "+"₹ "+BalanceDue);

                        binding.txtReceiptitle.setText("₹ "+BalanceDue);
                    }else {
                        binding.txtReceiptitle.setText("-");
                    }


                }
                else {
                    binding.txtReceiptitle.setText("-");
                }


//                AppUtil.hideProgressDialog();
            }
        });


    }
    private void GetOutstandingBalceDue() {

        //  AppUtil.showProgressDialog(binding.getRoot(),"Loading");
        String DbName = AppUtil.getStringData(getApplicationContext(),"DatabaseName","");

        System.out.println("Get Receipt List : "+FromDatePost+" "+ToDatePost+" "+ SlpName+" "+ DbName+" "+ "Vendor_Outstanding_Amount");
        OutsBalDueViewModel outsBalDueViewModel= new ViewModelProvider(this).get(OutsBalDueViewModel.class);
        outsBalDueViewModel.getOutstandingBalanceDueData(FromDatePost,ToDatePost, SlpName, DbName, "Vendor_Outstanding_Amount").observe(this, new Observer<CommanResorce<List<BalanceDueResponse>>>() {
            @Override
            public void onChanged(CommanResorce<List<BalanceDueResponse>> listCommanResorce) {

                if (listCommanResorce.data != null && !listCommanResorce.data.isEmpty()) {

                    if(listCommanResorce.data.get(0).getBalanceDue() != null){
                        String BalanceDue = listCommanResorce.data.get(0).getBalanceDue();
                        System.out.println("Receipt BalanceDue : "+BalanceDue);

                        binding.txtOutstandingstitle.setText("₹ "+BalanceDue);
                    }else {
                        binding.txtOutstandingstitle.setText("-");
                    }


                }
                else {
                    binding.txtOutstandingstitle.setText("-");
                }


//                AppUtil.hideProgressDialog();
            }
        });


    }


}