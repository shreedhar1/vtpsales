package com.softcore.vtpsales;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.softcore.vtpsales.AppUtils.AppUtil;
import com.softcore.vtpsales.Model.CommanResorce;
import com.softcore.vtpsales.Model.CusReportWiseModel;
import com.softcore.vtpsales.ViewModel.CusWiseReport_CusOut_ViewModel;
import com.softcore.vtpsales.ViewModel.CusWiseReport_Purchase_ViewModel;
import com.softcore.vtpsales.ViewModel.CusWiseReport_Sales_ViewModel;
import com.softcore.vtpsales.ViewModel.CusWiseReport_VenOut_ViewModel;
import com.softcore.vtpsales.databinding.ActivityReportsBinding;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReportsActivity2 extends AppCompatActivity {

    ActivityReportsBinding binding;
    String FromDatePost;
    String ToDatePost;
    String ViewFromDate;
    String ViewToDate;
//    String SlpName;
//    String selectedSlpName;

    String SalesBldue;
    String PurchaseBldue;
    String EmpType;
    String EmpTypePName;

    List<CusReportWiseModel> MainSalesList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= ActivityReportsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MainSalesList = new ArrayList<>();

        binding.laybar.appbarTextView.setText("Reports");

        binding.laybar.backId.setVisibility(View.VISIBLE);
        binding.laybar.backId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.imgSales.setImageResource(R.drawable.svg_sales);
        binding.txtSalestitle.setText("₹ 0.00");
        binding.txtSalesdesc.setText("Sales-Credit Note (Gross)");

        binding.imgPurchase.setImageResource(R.drawable.svg_purchase);
        binding.txtPurchasetitle.setText("₹ 0.00");
        binding.txtPurchasedesc.setText("Purchase - Debit Note (Gross)");

        binding.imgReceipt.setImageResource(R.drawable.svg_receipt);
        binding.txtReceiptitle.setText("₹ 0.00");
        binding.txtReceiptdesc.setText("Customer Outstanding (Gross)");

        binding.imgOutstandings.setImageResource(R.drawable.svg_outstanding);
        binding.txtOutstandingstitle.setText("₹ 0.00");
        binding.txtOutstandingsdesc.setText("Vendor Outstaning (Gross)");

        // Get the current date
        Calendar today = Calendar.getInstance();
        //today.set(2024, Calendar.APRIL, 5); testing

        Calendar fromCalendar = Calendar.getInstance();
        if (today.get(Calendar.MONTH) < Calendar.APRIL ||
                (today.get(Calendar.MONTH) == Calendar.APRIL && today.get(Calendar.DAY_OF_MONTH) < 2)) {
            // If the current month is before April, or it's April but before the 2nd (inclusive), consider the previous year
            if( today.get(Calendar.MONTH) == Calendar.APRIL&& today.get(Calendar.DAY_OF_MONTH) < 2){
                fromCalendar.set(Calendar.YEAR, today.get(Calendar.YEAR));
            }else {
                fromCalendar.set(Calendar.YEAR, today.get(Calendar.YEAR) - 1);
            }

        } else {
            // Otherwise, consider the current year
            fromCalendar.set(Calendar.YEAR, today.get(Calendar.YEAR));
        }
        fromCalendar.set(Calendar.MONTH, Calendar.APRIL);
        fromCalendar.set(Calendar.DAY_OF_MONTH, 1);
        Date finDate = fromCalendar.getTime();

        //currect date
        Date currentDate = today.getTime();

        SimpleDateFormat postfutureDate = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        SimpleDateFormat viewfutureDate = new SimpleDateFormat("yyyy-MMM-dd", Locale.getDefault());
        String postfuturedateString = postfutureDate.format(finDate);
        String viewfuturedateString = viewfutureDate.format(finDate);

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

        EmpType = AppUtil.getStringData(getApplicationContext(),"EmpType","");
        EmpTypePName = AppUtil.getStringData(getApplicationContext(),"EmpTypePName","");


//        GetSlpList();


    //    selectedSlpName = AppUtil.getStringData(getApplicationContext(), "AllSalesPerson", "");
//        selectedSlpName = "1,2,3,4,5,6,7,8,9,10,11,12,13, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50"; // Testing
//       selectedSlpName = "";
//        SlpName = selectedSlpName;
//        System.out.println("SlpName"+SlpName);

        GetData();


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
                if(binding.txtSalestitle.getText().equals("₹ 0.00")){
                    Toast.makeText(ReportsActivity2.this, "No Data Available", Toast.LENGTH_SHORT).show();
                }else {
                    NextActivity("Sales","Sales_Customer_Wise_Report","Monthly_Sales_Report",binding.txtSalestitle.getText());
                }
            }
        });
        binding.layPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.txtPurchasetitle.getText().equals("₹ 0.00")){
                    Toast.makeText(ReportsActivity2.this, "No Data Available", Toast.LENGTH_SHORT).show();
                }else {
                    NextActivity("Purchase","Purchase_Vendor_Wise_Report","Monthly_Purchase_Report", binding.txtPurchasetitle.getText());
                }
            }
        });
        binding.layReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.txtReceiptitle.getText().equals("₹ 0.00")){
                    Toast.makeText(ReportsActivity2.this, "No Data Available", Toast.LENGTH_SHORT).show();
                }else {
                    NextActivity("Customer Outstanding","CustomerWise_Outstanding_Amount","Monthly_Customer_Outstanding_Amount", binding.txtReceiptitle.getText());
                }
            }
        });
        binding.layOutstandings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.txtOutstandingstitle.getText().equals("₹ 0.00")){
                    Toast.makeText(ReportsActivity2.this, "No Data Available", Toast.LENGTH_SHORT).show();
                }else {
                    NextActivity("Vendor Outstanding","CustomerWise_Vendor_Outstanding_Amount","Monthly_Vendor_Outstanding_Amount", binding.txtOutstandingstitle.getText());
                }
            }
        });



    }

    private void NextActivity(String Type, String Flag,String MFlag, CharSequence amount) {

//        System.out.println("SlpName send to ReportsListActivity: "+SlpName);
        Intent intent = new Intent(ReportsActivity2.this, ReportsListActivity.class);
        intent.putExtra("TYPE", Type);
        intent.putExtra("FromDatePost",FromDatePost);
        intent.putExtra("ToDatePost",ToDatePost);
        intent.putExtra("ViewToDate",ViewToDate);
        intent.putExtra("ViewFromDate",ViewFromDate);
        intent.putExtra("SlpName","");
        intent.putExtra("Flag",Flag);
        intent.putExtra("MFlag",MFlag);
        intent.putExtra("Amount",amount);
        startActivity(intent);
    }

//    void GetSlpList() {
//
//
//        String DbName = AppUtil.getStringData(getApplicationContext(), "DatabaseName", "");
//
//        SlpViewModel slpViewModel = new ViewModelProvider(this).get(SlpViewModel.class);
//        slpViewModel.getSlpData(DbName, "Sales_Employee_List").observe(this, new Observer<CommanResorce<List<SlpResponse>>>() {
//            @Override
//            public void onChanged(CommanResorce<List<SlpResponse>> listCommanResorce) {
//
//                if (listCommanResorce.data != null && !listCommanResorce.data.isEmpty()) {
//
//                    List<String> slpnames = new ArrayList<>();
//
//                    slpnames.add("SELECT NAME");
//
//                    for (SlpResponse database : listCommanResorce.data) {
//                        slpnames.add(database.getSlpName());
//                    }
//                    ArrayAdapter<String> adapter = new ArrayAdapter<>(ReportsActivity2.this,
//                            R.layout.simple_spinner_layout_black, slpnames);
//
//                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//                    binding.spinnerSlpName.setAdapter(adapter);
//
//                    binding.spinnerSlpName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                            selectedSlpName = slpnames.get(position);
//
//                            if (!selectedSlpName.equals("SELECT NAME")) {
//                                SlpName = selectedSlpName;
//                                System.out.println("From Date :"+FromDatePost+"To Date:"+ToDatePost);
//                                GetData();
//                            }
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> parentView) {
//                            // Do nothing
//                        }
//                    });
//
//                } else {
//                    // AppUtil.showTost(getApplicationContext(), "User not found. Please check your username.");
//                    System.out.println("Slp Not found");
//                }
//                AppUtil.hideProgressDialog();
//            }
//        });
//    }

    private void GetData() {
        System.out.println("From Date :"+FromDatePost+"To Date:"+ToDatePost);
        GetCusWiseReportList1(FromDatePost,ToDatePost,"","Sales_Customer_Wise_Report");
        GetCusWiseReportList2(FromDatePost,ToDatePost,"","Purchase_Vendor_Wise_Report");
        GetCusWiseReportList3(FromDatePost,ToDatePost,"","CustomerWise_Outstanding_Amount");
        GetCusWiseReportList4(FromDatePost,ToDatePost,"","CustomerWise_Vendor_Outstanding_Amount");

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


                      //  if(!selectedSlpName.equals("SELECT NAME")){
                            GetData();
                    //    }
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

                     //   if(!selectedSlpName.equals("SELECT NAME")){
                            GetData();
                       // }
                    }
                }, year, month, day);


        // Show the Date Picker dialog
        datePickerDialog.show();
    }


    private void GetCusWiseReportList1(String FromDate, String ToDate, String SlpName, String Flag) {

        String DbName = AppUtil.getStringData(getApplicationContext(), "DatabaseName", "");

        System.out.println("Get CusWise List : " + FromDate + " " + ToDate + " " + SlpName + " " + DbName + " " + Flag);
        CusWiseReport_Sales_ViewModel cusWiseReportViewModel = new ViewModelProvider(this).get(CusWiseReport_Sales_ViewModel.class);
        cusWiseReportViewModel.getCusWiseReportData(FromDate, ToDate, "", DbName, Flag).observe(this, new Observer<CommanResorce<List<CusReportWiseModel>>>() {
            @Override
            public void onChanged(CommanResorce<List<CusReportWiseModel>> listCommanResorce) {

                if (listCommanResorce.data != null && !listCommanResorce.data.isEmpty()) {

                    MainSalesList = listCommanResorce.data;

                    System.out.println(MainSalesList.size());

                    //  EmpTypeFilterList(MainSalesList,ReportTYPE);

                    List<CusReportWiseModel>  filterList ;
                    filterList = new ArrayList<>();

                    for(int i = 0;i < MainSalesList.size();i++){
switch (EmpType) {
                            case "Sales Employee":
                                System.out.println("EmpType "+EmpType +" " +EmpTypePName+" "+MainSalesList.size());
                               if(MainSalesList.get(i).getSalesPerson() != null){
                                   if (EmpTypePName.equals(MainSalesList.get(i).getSalesPerson().toLowerCase()) || EmpTypePName.equals(MainSalesList.get(i).getSalesPerson().toUpperCase())) {
                                       filterList.add(MainSalesList.get(i));
                                   }
                               }
                                break;
                            case "Collection Person":
                                System.out.println("EmpType "+EmpType +" " +EmpTypePName+" "+MainSalesList.size());
                                if(MainSalesList.get(i).getCollectionPerson() != null){
                                    if (EmpTypePName.equals(MainSalesList.get(i).getCollectionPerson().toLowerCase()) || EmpTypePName.equals(MainSalesList.get(i).getCollectionPerson().toUpperCase())) {
                                        filterList.add(MainSalesList.get(i));
                                    } 
                                }
                                break;
                            case "Both":
                                System.out.println("EmpType "+EmpType +" " +EmpTypePName+" "+MainSalesList.size());
                              if(MainSalesList.get(i).getSalesPerson() != null && MainSalesList.get(i).getCollectionPerson() != null){
                                  if ((EmpTypePName.equals(MainSalesList.get(i).getSalesPerson().toLowerCase()) || EmpTypePName.equals(MainSalesList.get(i).getSalesPerson().toUpperCase() ) && (EmpTypePName.equals(MainSalesList.get(i).getCollectionPerson().toLowerCase()) ||EmpTypePName.equals(MainSalesList.get(i).getCollectionPerson().toUpperCase()) ) )){
                                      filterList.add(MainSalesList.get(i));
                                  }
                              }
                              
                                break;
                        }                    }

//                        UpdateList(filterList,ReportTYPE);

                    double Crn = 0;

                    for(int i = 0;i < filterList.size();i++){

                        if (filterList.get(i).getGrossAmtINV_CRN() != null) {
                            Crn += Double.parseDouble(filterList.get(i).getGrossAmtINV_CRN());
                        }

                    }
                    DecimalFormat df = new DecimalFormat("0.00");
                    String formattedCrn = df.format(Crn);
                    System.out.println("Report Report Sales amt "+formattedCrn);

                    binding.txtSalestitle.setText("₹ "+formattedCrn);

                }

                else {
                    binding.txtSalestitle.setText("₹ 0.00");
                }

                //AppUtil.hideProgressDialog();
            }
        });


    }

    private void GetCusWiseReportList2(String FromDate, String ToDate, String SlpName, String Flag) {

        String DbName = AppUtil.getStringData(getApplicationContext(), "DatabaseName", "");

        System.out.println("Get CusWise List : " + FromDate + " " + ToDate + " " + SlpName + " " + DbName + " " + Flag);
        CusWiseReport_Purchase_ViewModel cusWiseReportViewModel = new ViewModelProvider(this).get(CusWiseReport_Purchase_ViewModel.class);
        cusWiseReportViewModel.getCusWiseReportData(FromDate, ToDate, "", DbName, Flag).observe(this, new Observer<CommanResorce<List<CusReportWiseModel>>>() {
            @Override
            public void onChanged(CommanResorce<List<CusReportWiseModel>> listCommanResorce) {

                if (listCommanResorce.data != null && !listCommanResorce.data.isEmpty()) {

                    MainSalesList = listCommanResorce.data;

                    System.out.println(MainSalesList.size());

                    //  EmpTypeFilterList(MainSalesList,ReportTYPE);

                    List<CusReportWiseModel>  filterList ;
                    filterList = new ArrayList<>();

                    for(int i = 0;i < MainSalesList.size();i++){
                        switch (EmpType) {
                            case "Sales Employee":
                                System.out.println("EmpType "+EmpType +" " +EmpTypePName+" "+MainSalesList.size());
                                if(MainSalesList.get(i).getSalesPerson() != null){
                                    if (EmpTypePName.equals(MainSalesList.get(i).getSalesPerson().toLowerCase()) || EmpTypePName.equals(MainSalesList.get(i).getSalesPerson().toUpperCase())) {
                                        filterList.add(MainSalesList.get(i));
                                    }
                                }
                                break;
                            case "Collection Person":
                                System.out.println("EmpType "+EmpType +" " +EmpTypePName+" "+MainSalesList.size());
                                if(MainSalesList.get(i).getCollectionPerson() != null){
                                    if (EmpTypePName.equals(MainSalesList.get(i).getCollectionPerson().toLowerCase()) || EmpTypePName.equals(MainSalesList.get(i).getCollectionPerson().toUpperCase())) {
                                        filterList.add(MainSalesList.get(i));
                                    }
                                }
                                break;
                            case "Both":
                                System.out.println("EmpType "+EmpType +" " +EmpTypePName+" "+MainSalesList.size());
                                if(MainSalesList.get(i).getSalesPerson() != null && MainSalesList.get(i).getCollectionPerson() != null){
                                    if ((EmpTypePName.equals(MainSalesList.get(i).getSalesPerson().toLowerCase()) || EmpTypePName.equals(MainSalesList.get(i).getSalesPerson().toUpperCase() ) && (EmpTypePName.equals(MainSalesList.get(i).getCollectionPerson().toLowerCase()) ||EmpTypePName.equals(MainSalesList.get(i).getCollectionPerson().toUpperCase()) ) )){
                                        filterList.add(MainSalesList.get(i));
                                    }
                                }

                                break;
                        }
                    }

//                        UpdateList(filterList,ReportTYPE);

                    double Crn = 0;

                    for(int i = 0;i < filterList.size();i++){

                        if (filterList.get(i).getGrossAmtApCrn() != null) {
                            Crn += Double.parseDouble(filterList.get(i).getGrossAmtApCrn());
                        }

                    }
                    DecimalFormat df = new DecimalFormat("0.00");
                    String formattedCrn = df.format(Crn);
                    System.out.println("Report Report Purchase amt "+formattedCrn);

                    binding.txtPurchasetitle.setText("₹ "+formattedCrn);

                }

                else {
                    binding.txtPurchasetitle.setText("₹ 0.00");

                }

                //AppUtil.hideProgressDialog();
            }
        });


    }

    private void GetCusWiseReportList3(String FromDate, String ToDate, String SlpName, String Flag) {

        String DbName = AppUtil.getStringData(getApplicationContext(), "DatabaseName", "");

        System.out.println("Get CusWise List : " + FromDate + " " + ToDate + " " + SlpName + " " + DbName + " " + Flag);
        CusWiseReport_CusOut_ViewModel cusWiseReportViewModel = new ViewModelProvider(this).get(CusWiseReport_CusOut_ViewModel.class);
        cusWiseReportViewModel.getCusWiseReportData(FromDate, ToDate, "", DbName, Flag).observe(this, new Observer<CommanResorce<List<CusReportWiseModel>>>() {
            @Override
            public void onChanged(CommanResorce<List<CusReportWiseModel>> listCommanResorce) {

                if (listCommanResorce.data != null && !listCommanResorce.data.isEmpty()) {

                    MainSalesList = listCommanResorce.data;

                    System.out.println(MainSalesList.size());

                    //  EmpTypeFilterList(MainSalesList,ReportTYPE);

                    List<CusReportWiseModel>  filterList ;
                    filterList = new ArrayList<>();

                    for(int i = 0;i < MainSalesList.size();i++){
                        switch (EmpType) {
                            case "Sales Employee":
                                System.out.println("EmpType "+EmpType +" " +EmpTypePName+" "+MainSalesList.size());
                               if(MainSalesList.get(i).getSalesPerson() != null){
                                   if (EmpTypePName.equals(MainSalesList.get(i).getSalesPerson().toLowerCase()) || EmpTypePName.equals(MainSalesList.get(i).getSalesPerson().toUpperCase())) {
                                       filterList.add(MainSalesList.get(i));
                                   }
                               }
                                break;
                            case "Collection Person":
                                System.out.println("EmpType "+EmpType +" " +EmpTypePName+" "+MainSalesList.size());
                                if(MainSalesList.get(i).getCollectionPerson() != null){
                                    if (EmpTypePName.equals(MainSalesList.get(i).getCollectionPerson().toLowerCase()) || EmpTypePName.equals(MainSalesList.get(i).getCollectionPerson().toUpperCase())) {
                                        filterList.add(MainSalesList.get(i));
                                    } 
                                }
                                break;
                            case "Both":
                                System.out.println("EmpType "+EmpType +" " +EmpTypePName+" "+MainSalesList.size());
                              if(MainSalesList.get(i).getSalesPerson() != null && MainSalesList.get(i).getCollectionPerson() != null){
                                  if ((EmpTypePName.equals(MainSalesList.get(i).getSalesPerson().toLowerCase()) || EmpTypePName.equals(MainSalesList.get(i).getSalesPerson().toUpperCase() ) && (EmpTypePName.equals(MainSalesList.get(i).getCollectionPerson().toLowerCase()) ||EmpTypePName.equals(MainSalesList.get(i).getCollectionPerson().toUpperCase()) ) )){
                                      filterList.add(MainSalesList.get(i));
                                  }
                              }
                              
                                break;
                        }                    }

//                        UpdateList(filterList,ReportTYPE);

                    double Crn = 0;

                    for(int i = 0;i < filterList.size();i++){

                        if (filterList.get(i).getGrossAmtINV_ARCRN() != null) {
                            Crn += Double.parseDouble(filterList.get(i).getGrossAmtINV_ARCRN());
                        }

                    }
                    DecimalFormat df = new DecimalFormat("0.00");
                    String formattedCrn = df.format(Crn);
                    System.out.println("Report Report Purchase amt "+formattedCrn);

                    binding.txtReceiptitle.setText("₹ "+formattedCrn);

                }

                else {
                    binding.txtReceiptitle.setText("₹ 0.00");
                }

                //AppUtil.hideProgressDialog();
            }
        });


    }

    private void GetCusWiseReportList4(String FromDate, String ToDate, String SlpName, String Flag) {

        String DbName = AppUtil.getStringData(getApplicationContext(), "DatabaseName", "");

        System.out.println("Get CusWise List : " + FromDate + " " + ToDate + " " + SlpName + " " + DbName + " " + Flag);
        CusWiseReport_VenOut_ViewModel cusWiseReportViewModel = new ViewModelProvider(this).get(CusWiseReport_VenOut_ViewModel.class);
        cusWiseReportViewModel.getCusWiseReportData(FromDate, ToDate, "", DbName, Flag).observe(this, new Observer<CommanResorce<List<CusReportWiseModel>>>() {
            @Override
            public void onChanged(CommanResorce<List<CusReportWiseModel>> listCommanResorce) {

                if (listCommanResorce.data != null && !listCommanResorce.data.isEmpty()) {

                    MainSalesList = listCommanResorce.data;

                    System.out.println(MainSalesList.size());

                    //  EmpTypeFilterList(MainSalesList,ReportTYPE);

                    List<CusReportWiseModel>  filterList ;
                    filterList = new ArrayList<>();

                    for(int i = 0;i < MainSalesList.size();i++){
                        switch (EmpType) {
                            case "Sales Employee":
                                System.out.println("EmpType "+EmpType +" " +EmpTypePName+" "+MainSalesList.size());
                               if(MainSalesList.get(i).getSalesPerson() != null){
                                   if (EmpTypePName.equals(MainSalesList.get(i).getSalesPerson().toLowerCase()) || EmpTypePName.equals(MainSalesList.get(i).getSalesPerson().toUpperCase())) {
                                       filterList.add(MainSalesList.get(i));
                                   }
                               }
                                break;
                            case "Collection Person":
                                System.out.println("EmpType "+EmpType +" " +EmpTypePName+" "+MainSalesList.size());
                                if(MainSalesList.get(i).getCollectionPerson() != null){
                                    if (EmpTypePName.equals(MainSalesList.get(i).getCollectionPerson().toLowerCase()) || EmpTypePName.equals(MainSalesList.get(i).getCollectionPerson().toUpperCase())) {
                                        filterList.add(MainSalesList.get(i));
                                    }
                                }
                                break;
                            case "Both":
                                System.out.println("EmpType "+EmpType +" " +EmpTypePName+" "+MainSalesList.size());
                              if(MainSalesList.get(i).getSalesPerson() != null && MainSalesList.get(i).getCollectionPerson() != null){
                                  if ((EmpTypePName.equals(MainSalesList.get(i).getSalesPerson().toLowerCase()) || EmpTypePName.equals(MainSalesList.get(i).getSalesPerson().toUpperCase() ) && (EmpTypePName.equals(MainSalesList.get(i).getCollectionPerson().toLowerCase()) ||EmpTypePName.equals(MainSalesList.get(i).getCollectionPerson().toUpperCase()) ) )){
                                      filterList.add(MainSalesList.get(i));
                                  }
                              }

                                break;
                        }
                    }

//                        UpdateList(filterList,ReportTYPE);

                    double Crn = 0;

                    for(int i = 0;i < filterList.size();i++){

                        if (filterList.get(i).getGrossAmtApCrn() != null) {
                            Crn += Double.parseDouble(filterList.get(i).getGrossAmtApCrn());
                        }

                    }
                    DecimalFormat df = new DecimalFormat("0.00");
                    String formattedCrn = df.format(Crn);
                    System.out.println("Report Report txtOutstandingstitle amt "+formattedCrn);

                    binding.txtOutstandingstitle.setText("₹ "+formattedCrn);

                }

                else {
                    binding.txtOutstandingstitle.setText("₹ 0.00");
                }

                //AppUtil.hideProgressDialog();
            }
        });


    }


}
