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
import com.softcore.vtpsales.Model.InOutPayModel;
import com.softcore.vtpsales.ViewModel.CusWiseReport_CusOut_ViewModel;
import com.softcore.vtpsales.ViewModel.CusWiseReport_Purchase_Reg_ViewModel;
import com.softcore.vtpsales.ViewModel.CusWiseReport_Purchase_ViewModel;
import com.softcore.vtpsales.ViewModel.CusWiseReport_Sales_ViewModel;
import com.softcore.vtpsales.ViewModel.CusWiseReport_VenOut_ViewModel;
import com.softcore.vtpsales.ViewModel.InOutPayReportViewModel;
import com.softcore.vtpsales.ViewModel.OutgoingPayReportViewModel;
import com.softcore.vtpsales.databinding.ActivityReportsBinding;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

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
    List<InOutPayModel> InOutPayList ;

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
        binding.txtOutstandingsdesc.setText("Vendor Outstanding (Gross)");

        binding.imgIncoming.setImageResource(R.drawable.baseline_arrow_circle_down_24);
        binding.txtIncomingtitle.setText("₹ 0.00");
        binding.txtIncomingdesc.setText("Incoming Payment");

        binding.imgOutgoing.setImageResource(R.drawable.baseline_arrow_circle_down_24);
        binding.txtOutgoingtitle.setText("₹ 0.00");
        binding.txtOutgoingdesc.setText("Outgoing Payment");

        binding.imgPurchaseReg.setImageResource(R.drawable.baseline_point_of_sale_24);
        binding.txtPurchaseRegTitle.setText("₹ 0.00");
        binding.txtPurchaseRegDesc.setText("Purchase Register");

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
        binding.layPurchaseReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.txtPurchaseRegTitle.getText().equals("₹ 0.00")){
                    Toast.makeText(ReportsActivity2.this, "No Data Available", Toast.LENGTH_SHORT).show();
                }else {
                    NextActivity3("Purchase Register","Purchase_Register_Report","", binding.txtPurchaseRegTitle.getText());
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


        binding.layIncoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             NextActivity2("Incoming","Incoming_Payment_Report");
            }
        });
        binding.layOutgoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextActivity2("Outgoing","Outgoing_Payment_Report");
            }
        });

    }

    private void NextActivity2(String TYPE,String Flag) {
        Intent intent = new Intent(ReportsActivity2.this, IncomingOutgoingList.class);
        intent.putExtra("TYPE", TYPE);
        intent.putExtra("FromDatePost",FromDatePost);
        intent.putExtra("ToDatePost",ToDatePost);
        intent.putExtra("ViewToDate",ViewToDate);
        intent.putExtra("ViewFromDate",ViewFromDate);
        intent.putExtra("SlpName","");
        intent.putExtra("Flag",Flag);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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
    private void NextActivity3(String Type, String Flag,String MFlag, CharSequence amount) {

//        System.out.println("SlpName send to ReportsListActivity: "+SlpName);
        Intent intent = new Intent(ReportsActivity2.this, ReportsListActivity2.class);
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
        GetCusWiseReportList5(FromDatePost,ToDatePost,"","Incoming_Payment_Report");
        GetCusWiseReportList6(FromDatePost,ToDatePost,"","Outgoing_Payment_Report");
        GetCusWiseReportList7(FromDatePost,ToDatePost,"","Purchase_Register_Report");

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

                    for (int i = 0; i < MainSalesList.size(); i++) {
                        String salesPerson = MainSalesList.get(i).getSalesPerson();
                        String collectionPerson = MainSalesList.get(i).getCollectionPerson();
                        String empTypePNameLower = EmpTypePName.toLowerCase();

                        switch (EmpType) {
                            case "Sales Employee":
                                System.out.println("EmpType " + EmpType + " " + EmpTypePName + " " + MainSalesList.size());
                                if (salesPerson != null && empTypePNameLower.equals(salesPerson.toLowerCase())) {
                                    System.out.println("MainSalesList Amt: " + MainSalesList.get(i).getGrossAmtINV_ARCRN());
                                    filterList.add(MainSalesList.get(i));
                                }
                                break;

                            case "Collection Person":
                                System.out.println("EmpType " + EmpType + " " + EmpTypePName + " " + MainSalesList.size());
                                if (collectionPerson != null && empTypePNameLower.equals(collectionPerson.toLowerCase())) {
                                    filterList.add(MainSalesList.get(i));
                                }
                                break;

                            case "Both (SE+CP)":
                                System.out.println("EmpType " + EmpType + " " + EmpTypePName + " " + MainSalesList.size());
                                if ((salesPerson != null && empTypePNameLower.equals(salesPerson.toLowerCase())) ||
                                        (collectionPerson != null && empTypePNameLower.equals(collectionPerson.toLowerCase()))) {
                                    filterList.add(MainSalesList.get(i));
                                }
                                break;
                        }
                    }


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

                    for (int i = 0; i < MainSalesList.size(); i++) {
                        String salesPerson = MainSalesList.get(i).getSalesPerson();
                        String collectionPerson = MainSalesList.get(i).getCollectionPerson();
                        String empTypePNameLower = EmpTypePName.toLowerCase();

                        switch (EmpType) {
                            case "Sales Employee":
                                System.out.println("EmpType " + EmpType + " " + EmpTypePName + " " + MainSalesList.size());
                                if (salesPerson != null && empTypePNameLower.equals(salesPerson.toLowerCase())) {
                                    System.out.println("MainSalesList Amt: " + MainSalesList.get(i).getGrossAmtINV_ARCRN());
                                    filterList.add(MainSalesList.get(i));
                                }
                                break;

                            case "Collection Person":
                                System.out.println("EmpType " + EmpType + " " + EmpTypePName + " " + MainSalesList.size());
                                if (collectionPerson != null && empTypePNameLower.equals(collectionPerson.toLowerCase())) {
                                    filterList.add(MainSalesList.get(i));
                                }
                                break;

                            case "Both (SE+CP)":
                                System.out.println("EmpType " + EmpType + " " + EmpTypePName + " " + MainSalesList.size());
                                if ((salesPerson != null && empTypePNameLower.equals(salesPerson.toLowerCase())) ||
                                        (collectionPerson != null && empTypePNameLower.equals(collectionPerson.toLowerCase()))) {
                                    filterList.add(MainSalesList.get(i));
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

                    for (int i = 0; i < MainSalesList.size(); i++) {
                        String salesPerson = MainSalesList.get(i).getSalesPerson();
                        String collectionPerson = MainSalesList.get(i).getCollectionPerson();
                        String empTypePNameLower = EmpTypePName.toLowerCase();

                        switch (EmpType) {
                            case "Sales Employee":
                                System.out.println("EmpType " + EmpType + " " + EmpTypePName + " " + MainSalesList.size());
                                if (salesPerson != null && empTypePNameLower.equals(salesPerson.toLowerCase())) {
                                    System.out.println("MainSalesList Amt: " + MainSalesList.get(i).getGrossAmtINV_ARCRN());
                                    filterList.add(MainSalesList.get(i));
                                }
                                break;

                            case "Collection Person":
                                System.out.println("EmpType " + EmpType + " " + EmpTypePName + " " + MainSalesList.size());
                                if (collectionPerson != null && empTypePNameLower.equals(collectionPerson.toLowerCase())) {
                                    filterList.add(MainSalesList.get(i));
                                }
                                break;

                            case "Both (SE+CP)":
                                System.out.println("EmpType " + EmpType + " " + EmpTypePName + " " + MainSalesList.size());
                                if ((salesPerson != null && empTypePNameLower.equals(salesPerson.toLowerCase())) ||
                                        (collectionPerson != null && empTypePNameLower.equals(collectionPerson.toLowerCase()))) {
                                    filterList.add(MainSalesList.get(i));
                                }
                                break;
                        }
                    }

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

                    for (int i = 0; i < MainSalesList.size(); i++) {
                        String salesPerson = MainSalesList.get(i).getSalesPerson();
                        String collectionPerson = MainSalesList.get(i).getCollectionPerson();
                        String empTypePNameLower = EmpTypePName.toLowerCase();

                        switch (EmpType) {
                            case "Sales Employee":
                                System.out.println("EmpType " + EmpType + " " + EmpTypePName + " " + MainSalesList.size());
                                if (salesPerson != null && empTypePNameLower.equals(salesPerson.toLowerCase())) {
                                    System.out.println("MainSalesList Amt: " + MainSalesList.get(i).getGrossAmtINV_ARCRN());
                                    filterList.add(MainSalesList.get(i));
                                }
                                break;

                            case "Collection Person":
                                System.out.println("EmpType " + EmpType + " " + EmpTypePName + " " + MainSalesList.size());
                                if (collectionPerson != null && empTypePNameLower.equals(collectionPerson.toLowerCase())) {
                                    filterList.add(MainSalesList.get(i));
                                }
                                break;

                            case "Both (SE+CP)":
                                System.out.println("EmpType " + EmpType + " " + EmpTypePName + " " + MainSalesList.size());
                                if ((salesPerson != null && empTypePNameLower.equals(salesPerson.toLowerCase())) ||
                                        (collectionPerson != null && empTypePNameLower.equals(collectionPerson.toLowerCase()))) {
                                    filterList.add(MainSalesList.get(i));
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

    private void GetCusWiseReportList5(String FromDate, String ToDate, String SlpName, String Flag) {

        String DbName = AppUtil.getStringData(getApplicationContext(), "DatabaseName", "");

        System.out.println("Get Incoming Payment List : " + FromDate + " " + ToDate + " " + SlpName + " " + DbName + " " + Flag);
        InOutPayReportViewModel inOutPayReportViewModel = new ViewModelProvider(this).get(InOutPayReportViewModel.class);
        inOutPayReportViewModel.getInOutDetails(FromDate, ToDate, "", DbName, Flag).observe(this, new Observer<CommanResorce<List<InOutPayModel>>>() {
            @Override
            public void onChanged(CommanResorce<List<InOutPayModel>> listCommanResorce) {

                if (listCommanResorce.data != null && !listCommanResorce.data.isEmpty()) {

                    InOutPayList = listCommanResorce.data;

                    System.out.println(InOutPayList.size());

                    //  EmpTypeFilterList(MainSalesList,ReportTYPE);

                    List<InOutPayModel>  filterList ;
                    filterList = new ArrayList<>();

                    for (int i = 0; i < InOutPayList.size(); i++) {
                        String salesPerson = InOutPayList.get(i).getSalesPerson();
                        String collectionPerson = InOutPayList.get(i).getCollectionPerson();
                        String empTypePNameLower = EmpTypePName.toLowerCase();

                        switch (EmpType) {
                            case "Sales Employee":
                                System.out.println("EmpType " + EmpType + " " + EmpTypePName + " " + InOutPayList.size());
                                if (salesPerson != null && empTypePNameLower.equals(salesPerson.toLowerCase())) {
                                    filterList.add(InOutPayList.get(i));
                                }
                                break;

                            case "Collection Person":
                                System.out.println("EmpType " + EmpType + " " + EmpTypePName + " " + InOutPayList.size());
                                if (collectionPerson != null && empTypePNameLower.equals(collectionPerson.toLowerCase())) {
                                    filterList.add(InOutPayList.get(i));
                                }
                                break;

                            case "Both (SE+CP)":
                                System.out.println("EmpType " + EmpType + " " + EmpTypePName + " " + InOutPayList.size());
                                if ((salesPerson != null && empTypePNameLower.equals(salesPerson.toLowerCase())) ||
                                        (collectionPerson != null && empTypePNameLower.equals(collectionPerson.toLowerCase()))) {
                                    filterList.add(InOutPayList.get(i));
                                }
                                break;
                        }
                    }

                    List<InOutPayModel> INOUTList = new ArrayList<>();

                    Set<String> BpNameAndDoc = new HashSet<>();
                    for (InOutPayModel model : filterList) {
                        if (!BpNameAndDoc.contains(model.getBPName()+"_"+model.getSAP_Doc_No())) {
                            BpNameAndDoc.add(model.getBPName()+"_"+model.getSAP_Doc_No());

                            INOUTList.add(model);
                        }
                    }

                    Set<String> BpNames = new HashSet<>();
                    for (int a = 0; a < INOUTList.size(); a++) {
                        if (!BpNames.contains(INOUTList.get(a).getBPName())) {
                            BpNames.add(INOUTList.get(a).getBPName());

                        }
                    }
                    List<InOutPayModel> finalList = new ArrayList<>();

                    for(int i = 0; i<INOUTList.size();i++){
                        InOutPayModel model = new InOutPayModel();
                        String SPName = INOUTList.get(i).getBPName();
                        double Tamt = 0;
                        for(int j = 0; j<INOUTList.size();j++){
                            if(SPName.equals(INOUTList.get(j).getBPName())){
                                Tamt += INOUTList.get(j).getTotal();
                            }
                        }
                        model.setTotal(Tamt);
                        model.setBPName(SPName);
                        finalList.add(model);
                    }

                    List<InOutPayModel> finalList2 = new ArrayList<>();

                    Set<String> BpNames2 = new HashSet<>();
                    for (int a = 0; a < finalList.size(); a++) {
                        if (!BpNames2.contains(finalList.get(a).getBPName())) {
                            BpNames2.add(finalList.get(a).getBPName());
                            finalList2.add(finalList.get(a));
                        }
                    }

                    double Tamt = 0;
                    for(int i = 0;i<finalList2.size();i++){
                        Tamt += finalList2.get(i).getTotal();
                    }

                    binding.txtIncomingtitle.setText("₹ "+String.format("%.2f", Tamt));

                }

                else {
                    binding.txtIncomingtitle.setText("₹ 0.00");
                }

                //AppUtil.hideProgressDialog();
            }
        });


    }

    private void GetCusWiseReportList6(String FromDate, String ToDate, String SlpName, String Flag) {

        String DbName = AppUtil.getStringData(getApplicationContext(), "DatabaseName", "");

        System.out.println("Get Outgoing Payment List : " + FromDate + " " + ToDate + " " + SlpName + " " + DbName + " " + Flag);
        OutgoingPayReportViewModel outPayReportViewModel = new ViewModelProvider(this).get(OutgoingPayReportViewModel.class);
        outPayReportViewModel.getOutDetails(FromDate, ToDate, "", DbName, Flag).observe(this, new Observer<CommanResorce<List<InOutPayModel>>>() {
            @Override
            public void onChanged(CommanResorce<List<InOutPayModel>> listCommanResorce) {

                if (listCommanResorce.data != null && !listCommanResorce.data.isEmpty()) {

                    InOutPayList = listCommanResorce.data;

                    System.out.println(InOutPayList.size());

                    //  EmpTypeFilterList(MainSalesList,ReportTYPE);

                    List<InOutPayModel>  filterList ;
                    filterList = new ArrayList<>();

                    for (int i = 0; i < InOutPayList.size(); i++) {
                        String salesPerson = InOutPayList.get(i).getSalesPerson();
                        String collectionPerson = InOutPayList.get(i).getCollectionPerson();
                        String empTypePNameLower = EmpTypePName.toLowerCase();

                        switch (EmpType) {
                            case "Sales Employee":
                                System.out.println("EmpType " + EmpType + " " + EmpTypePName + " " + InOutPayList.size());
                                if (salesPerson != null && empTypePNameLower.equals(salesPerson.toLowerCase())) {
                                    filterList.add(InOutPayList.get(i));
                                }
                                break;

                            case "Collection Person":
                                System.out.println("EmpType " + EmpType + " " + EmpTypePName + " " + InOutPayList.size());
                                if (collectionPerson != null && empTypePNameLower.equals(collectionPerson.toLowerCase())) {
                                    filterList.add(InOutPayList.get(i));
                                }
                                break;

                            case "Both (SE+CP)":
                                System.out.println("EmpType " + EmpType + " " + EmpTypePName + " " + InOutPayList.size());
                                if ((salesPerson != null && empTypePNameLower.equals(salesPerson.toLowerCase())) ||
                                        (collectionPerson != null && empTypePNameLower.equals(collectionPerson.toLowerCase()))) {
                                    filterList.add(InOutPayList.get(i));
                                }
                                break;
                        }
                    }

//                        UpdateList(filterList,ReportTYPE);

                    List<InOutPayModel> INOUTList = new ArrayList<>();

                    Set<String> BpNameAndDoc = new HashSet<>();
                    for (InOutPayModel model : filterList) {
                        if (!BpNameAndDoc.contains(model.getBPName()+"_"+model.getSAP_Doc_No())) {
                            BpNameAndDoc.add(model.getBPName()+"_"+model.getSAP_Doc_No());

                            INOUTList.add(model);
                        }
                    }

                    Set<String> BpNames = new HashSet<>();
                    for (int a = 0; a < INOUTList.size(); a++) {
                        if (!BpNames.contains(INOUTList.get(a).getBPName())) {
                            BpNames.add(INOUTList.get(a).getBPName());

                        }
                    }
                    List<InOutPayModel> finalList = new ArrayList<>();

                    for(int i = 0; i<INOUTList.size();i++){
                        InOutPayModel model = new InOutPayModel();
                        String SPName = INOUTList.get(i).getBPName();
                        double Tamt = 0;
                        for(int j = 0; j<INOUTList.size();j++){
                            if(SPName.equals(INOUTList.get(j).getBPName())){
                                Tamt += INOUTList.get(j).getTotal();
                            }
                        }
                        model.setTotal(Tamt);
                        model.setBPName(SPName);
                        finalList.add(model);
                    }

                    List<InOutPayModel> finalList2 = new ArrayList<>();

                    Set<String> BpNames2 = new HashSet<>();
                    for (int a = 0; a < finalList.size(); a++) {
                        if (!BpNames2.contains(finalList.get(a).getBPName())) {
                            BpNames2.add(finalList.get(a).getBPName());
                            finalList2.add(finalList.get(a));
                        }
                    }

                    double Tamt = 0;
                    for(int i = 0;i<finalList2.size();i++){
                        Tamt += finalList2.get(i).getTotal();
                    }

                    binding.txtOutgoingtitle.setText("₹ "+String.format("%.2f", Tamt));

                }

                else {
                    binding.txtOutgoingtitle.setText("₹ 0.00");


                }


                //AppUtil.hideProgressDialog();
            }
        });


    }


    private void GetCusWiseReportList7(String FromDate, String ToDate, String SlpName, String Flag) {

        String DbName = AppUtil.getStringData(getApplicationContext(), "DatabaseName", "");

        System.out.println("Purchase reg Data  : " + FromDate + " " + ToDate + " " + SlpName + " " + DbName + " " + Flag);
        CusWiseReport_Purchase_Reg_ViewModel cusWiseReportViewModel = new ViewModelProvider(this).get(CusWiseReport_Purchase_Reg_ViewModel.class);
        cusWiseReportViewModel.getCusWiseReportData(FromDate, ToDate, "", DbName, Flag).observe(this, new Observer<CommanResorce<List<CusReportWiseModel>>>() {
            @Override
            public void onChanged(CommanResorce<List<CusReportWiseModel>> listCommanResorce) {

                if (listCommanResorce.data != null && !listCommanResorce.data.isEmpty()) {

                    MainSalesList = listCommanResorce.data;

                    System.out.println("Purchase reg list"+MainSalesList.size());

                    List<CusReportWiseModel>  filterList ;
                    filterList = new ArrayList<>();

                    for (int i = 0; i < MainSalesList.size(); i++) {
                        String salesPerson = MainSalesList.get(i).getSalesPerson();
                        String collectionPerson = MainSalesList.get(i).getCollectionPerson();
                        String empTypePNameLower = EmpTypePName.toLowerCase();

                        switch (EmpType) {
                            case "Sales Employee":
                                System.out.println("EmpType " + EmpType + " " + EmpTypePName + " " + MainSalesList.size());
                                if (salesPerson != null && empTypePNameLower.equals(salesPerson.toLowerCase())) {
                                    System.out.println("MainSalesList Amt: " + MainSalesList.get(i).getGrossAmtINV_ARCRN());
                                    filterList.add(MainSalesList.get(i));
                                }
                                break;

                            case "Collection Person":
                                System.out.println("EmpType " + EmpType + " " + EmpTypePName + " " + MainSalesList.size());
                                if (collectionPerson != null && empTypePNameLower.equals(collectionPerson.toLowerCase())) {
                                    filterList.add(MainSalesList.get(i));
                                }
                                break;

                            case "Both (SE+CP)":
                                System.out.println("EmpType " + EmpType + " " + EmpTypePName + " " + MainSalesList.size());
                                if ((salesPerson != null && empTypePNameLower.equals(salesPerson.toLowerCase())) ||
                                        (collectionPerson != null && empTypePNameLower.equals(collectionPerson.toLowerCase()))) {
                                    filterList.add(MainSalesList.get(i));
                                }
                                break;
                        }
                    }

                    List<CusReportWiseModel>  newfilterList ;
                    newfilterList = new ArrayList<>();

                        Set<String> DocEntries = new HashSet<>();
                        for (int a = 0; a < filterList.size(); a++) {
                            if (!DocEntries.contains(filterList.get(a).getDocEntry())) {
                                DocEntries.add(filterList.get(a).getDocEntry());
                                newfilterList.add(filterList.get(a));
                            }
                        }

                    double Crn = 0;

                        System.out.println("newfilterList.size() "+newfilterList.size());
                    for(int i = 0;i < newfilterList.size();i++){

                        if (newfilterList.get(i).getGrossAmt() != null) {
                            Crn += Double.parseDouble(newfilterList.get(i).getGrossAmt());
                        }

                    }

                    DecimalFormat df = new DecimalFormat("0.00");
                    String formattedCrn = df.format(Crn);
                    System.out.println("Report Report Purchase amt "+formattedCrn);

                    binding.txtPurchaseRegTitle.setText("₹ "+formattedCrn);

                }

                else {
                    binding.txtPurchaseRegTitle.setText("₹ 0.00");

                }

                //AppUtil.hideProgressDialog();
            }
        });


    }

}
