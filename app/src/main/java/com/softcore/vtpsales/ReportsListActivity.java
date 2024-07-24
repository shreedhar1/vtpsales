package com.softcore.vtpsales;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.softcore.vtpsales.Adaptors.AdapterCustWiseReport;
import com.softcore.vtpsales.AppUtils.AppUtil;
import com.softcore.vtpsales.Model.CommanResorce;
import com.softcore.vtpsales.Model.CusReportWiseModel;
import com.softcore.vtpsales.ViewModel.CusWiseReportViewModel;
import com.softcore.vtpsales.ViewModel.MonthWiseReportViewModel;
import com.softcore.vtpsales.databinding.ActivityReportsListBinding;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ReportsListActivity extends AppCompatActivity {
    ActivityReportsListBinding binding;
    String TYPE;
    String ViewFromDate;
    String ViewToDate;
    String PostFromDate;
    String PostToDate;
    String SlpName;
    String Flag;
    String MFlag;
    String TotalAmt;
    String SortBy = "Gross";
    String EmpType;
    String EmpTypePName;
    AdapterCustWiseReport CusAdapter;
    List<CusReportWiseModel> BackUpList;
    List<CusReportWiseModel> Mainlist ;
    List<CusReportWiseModel> MonList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= ActivityReportsListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewFromDate=getIntent().getStringExtra("ViewFromDate");
        ViewToDate=getIntent().getStringExtra("ViewToDate");
        PostFromDate=getIntent().getStringExtra("FromDatePost");
        PostToDate=getIntent().getStringExtra("ToDatePost");
       // SlpName=getIntent().getStringExtra("SlpName");
        SlpName="";

        Flag=getIntent().getStringExtra("Flag");
        MFlag=getIntent().getStringExtra("MFlag");
        TotalAmt=getIntent().getStringExtra("Amount");
        TYPE=getIntent().getStringExtra("TYPE");
        binding.laybar.appbarTextView.setText(TYPE);


        EmpType = AppUtil.getStringData(getApplicationContext(),"EmpType","");
        EmpTypePName = AppUtil.getStringData(getApplicationContext(),"EmpTypePName","");

        binding.laybar.backId.setVisibility(View.VISIBLE);
        binding.laybar.backId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.laybar.print.setVisibility(View.GONE);
        binding.laybar.searchId.setVisibility(View.VISIBLE);
        binding.laybar.shareId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), generalPdfViewerActivity.class);
                intent.putExtra("ReportTYPE", String.valueOf(TYPE));
                intent.putExtra("SortBy", String.valueOf(SortBy));
                intent.putExtra("ReportList", (Serializable) Mainlist);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        binding.laybar.searchId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.laybar.laybar3.setVisibility(View.GONE);
                binding.laybar.SearchLayId.setVisibility(View.VISIBLE);
                binding.laybar.appbarTextView.setVisibility(View.GONE);

                binding.laybar.searchEditText.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(binding.laybar.searchEditText, InputMethodManager.SHOW_IMPLICIT);

                binding.idBarChart.setVisibility(View.GONE);
            }
        });
        binding.laybar.searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed before text is changed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Update the list when the text changes
                updateList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No action needed after text is changed
            }
        });

        binding.laybar.Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.laybar.laybar3.setVisibility(View.VISIBLE);
                binding.laybar.SearchLayId.setVisibility(View.GONE);
                binding.laybar.appbarTextView.setVisibility(View.VISIBLE);
                binding.idBarChart.setVisibility(View.VISIBLE);

                Mainlist = BackUpList;

                updateList(SortBy);
                binding.idBarChart.setVisibility(View.VISIBLE);
            }
        });

        binding.txtDates.setText(ViewFromDate+" to "+ViewToDate);

        binding.txtTotalAmt.setText(TotalAmt);
        Mainlist = new ArrayList<>();
        BackUpList = new ArrayList<>();
        GetCusWiseReportList(PostFromDate,PostToDate,SlpName,Flag);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CusAdapter = new AdapterCustWiseReport();
        binding.recyclerView.setAdapter(CusAdapter);


    }

    private void updateList(String CustomerName) {
        List<CusReportWiseModel> filterList = new ArrayList<>();
        String searchText = CustomerName.toLowerCase();

        for (int i = 0; i < BackUpList.size(); i++) {
            CusReportWiseModel model = BackUpList.get(i);

            if(model.getCustomerName() == null || model.getCustomerName().equals("")){

                if((model.getCustomer_Name() == null || model.getCustomer_Name().equals(""))){
                    if((model.getVendor_Name() == null || model.getVendor_Name().equals(""))){
                        if((model.getVendorName() == null || model.getVendorName().equals(""))){
                        }else{

                            if (BackUpList.get(i).getVendorName().toLowerCase().contains(searchText)) {
                                filterList.add(BackUpList.get(i));
                            }
                        }

                    }else{

                        if (BackUpList.get(i).getVendor_Name().toLowerCase().contains(searchText)) {
                            filterList.add(BackUpList.get(i));
                        }
                    }
                }else{
                    if (BackUpList.get(i).getCustomer_Name().toLowerCase().contains(searchText)) {
                        filterList.add(BackUpList.get(i));
                    }
                }
            }
            else {
                if (BackUpList.get(i).getCustomerName().toLowerCase().contains(searchText)) {
                    filterList.add(BackUpList.get(i));
                }
            }

        }

        if (filterList.isEmpty()) {
            Mainlist = BackUpList;
        } else {
            Mainlist = filterList;
        }


        UpdateList(SortBy);  // Ensure the adapter is updated with the new list
    }

    private void GetBarChart(String fromDate, String toDate, String slpName, String mFlag, String empType, String empTypePName) {
        System.out.println("MFlag:"+mFlag);
        String dbName = AppUtil.getStringData(getApplicationContext(), "DatabaseName", "");

        MonthWiseReportViewModel viewModel = new ViewModelProvider(this).get(MonthWiseReportViewModel.class);
        viewModel.getMonthsWiseReportData(fromDate, toDate, slpName, dbName, mFlag).observe(this, new Observer<CommanResorce<List<CusReportWiseModel>>>() {
            @Override
            public void onChanged(CommanResorce<List<CusReportWiseModel>> listCommanResorce) {
                if (listCommanResorce.data != null && !listCommanResorce.data.isEmpty()) {
                    List<CusReportWiseModel> monList = listCommanResorce.data;




                    List<CusReportWiseModel> filterMonList = new ArrayList<>();

//                    Set<String> EmpName_TYPE_Month = new HashSet<>();
//                    for (CusReportWiseModel model : monList) {
//                        if (!EmpName_TYPE_Month.contains(model.getEmployeeName()+"_"+model.getType()+"_"+model.getMonth())) {
//                            EmpName_TYPE_Month.add(model.getEmployeeName()+"_"+model.getType()+"_"+model.getMonth());
//
//                            filterMonList.add(model);
//                        }
//                    }

                    for(CusReportWiseModel model : monList){
                        if(EmpType.equals(model.getType()) && EmpTypePName.equals(model.getEmployeeName())){
                            filterMonList.add(model);
                        }
                    }




                    MonList = filterMonList;
                    if(!TYPE.equals("Purchase Register")){
                        if(!MonList.isEmpty()){
                            UpdateBarchart(MonList,SortBy);
                        }

                    }


                }
            }
        });
    }

    private void UpdateBarchart(List<CusReportWiseModel> monList, String sortBy) {

        Gson gson = new Gson();
        String jsonArrayString = gson.toJson(monList);
        System.out.println("Month List: "+jsonArrayString);

        Type listType = new TypeToken<List<CusReportWiseModel>>() {}.getType();
        List<CusReportWiseModel> data = gson.fromJson(jsonArrayString, listType);

        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            CusReportWiseModel model = data.get(i);
            float value = 0;
            if(sortBy.equals("Net")){
                if(TYPE.equals("Sales" )){
                    value = Float.parseFloat(model.getNetAmtINV_CRN());
                }
                else if(TYPE.equals("Customer Outstanding")){
                    value = Float.parseFloat(model.getNetAmtINV_ARCRN());
                }
                else if(TYPE.equals("Purchase")|| TYPE.equals("Vendor Outstanding")){
                    value = Float.parseFloat(model.getNetAmtApCrn());
                }
                else if(TYPE.equals("Purchase Register")){
                    value = Float.parseFloat(model.getNetAmt());
                }

            }else {
                if(TYPE.equals("Sales" )){
                    value = Float.parseFloat(model.getGrossAmtINV_CRN());
                } else if( TYPE.equals("Customer Outstanding")){
                    value = Float.parseFloat(model.getGrossAmtINV_ARCRN());
                }
                else if(TYPE.equals("Purchase")|| TYPE.equals("Vendor Outstanding")){
                    value = Float.parseFloat(model.getGrossAmtApCrn());
                }
                else if(TYPE.equals("Purchase Register")){
                    value = Float.parseFloat(model.getGrossAmt());
                }

            }

            if(value != 0){
                entries.add(new BarEntry(i, value));
            }


        }

        BarDataSet dataSet = new BarDataSet(entries, "Sales Amount");

        BarData barData = new BarData(dataSet);
        binding.idBarChart.setData(barData);

        dataSet.setColor(Color.parseColor("#013F8F"));

        XAxis xAxis = binding.idBarChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(getLabels(monList)));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
       // xAxis.setLabelRotationAngle(45f);

//                    binding.idBarChart.getDescription().setEnabled(false);
//                    binding.idBarChart.setFitBars(true);

//                    binding.idBarChart.invalidate();


        Legend legend = binding.idBarChart.getLegend();

        LegendEntry[] legendEntries = new LegendEntry[1];
        LegendEntry entry = new LegendEntry();
        if(sortBy.equals("Net")){
            if(TYPE.equals("Sales")){
                entry.label = "Net Sales Amount";
            }else if(TYPE.equals("Purchase")){
                entry.label = "Net Purchase Amount";
            }

        }else {
            if(TYPE.equals("Sales")){
                entry.label = "Gross Sales Amount";
            }else if(TYPE.equals("Purchase")){
                entry.label = "Gross Purchase Amount";
            }

        }
        // Your label
        entry.formColor = Color.parseColor("#013F8F"); // Dark blue color
        entry.form = Legend.LegendForm.SQUARE; // Use a square form
        legendEntries[0] = entry;

// Set the custom legend entry
        legend.setCustom(legendEntries);

        YAxis rightYAxis = binding.idBarChart.getAxisRight();

// Disable drawing of Y-axis labels on the right side
        rightYAxis.setDrawLabels(false);

        // Dark blue color
        binding.idBarChart.animateY(1000);
        binding.idBarChart.getXAxis().setDrawGridLines(false);
        binding.idBarChart.getAxisLeft().setDrawGridLines(false);
        rightYAxis.setDrawGridLines(false);
        binding.idBarChart.getDescription().setEnabled(false);
        binding.idBarChart.setTouchEnabled(false);
        binding.idBarChart.setDragEnabled(false);
        binding.idBarChart.setScaleEnabled(false);
        binding.idBarChart.setPinchZoom(false); // Disable pinch zoom
        binding.idBarChart.invalidate(); // refresh

    }

    private String[] getLabels(List<CusReportWiseModel> data) {
        String[] labels = new String[data.size()];
        for (int i = 0; i < data.size(); i++) {

            labels[i] = data.get(i).getMonth();
        }
        return labels;
    }



    private void GetCusWiseReportList (String FromDate,String ToDate,String SlpName,String Flag){

        AppUtil.showProgressDialog(binding.getRoot(),"Loading");
        String DbName = AppUtil.getStringData(getApplicationContext(),"DatabaseName","");

        System.out.println("Get CusWise List : "+FromDate+" "+ToDate+" "+ SlpName+" "+ DbName+" "+ Flag);
        CusWiseReportViewModel cusWiseReportViewModel= new ViewModelProvider(this).get(CusWiseReportViewModel.class);
        cusWiseReportViewModel.getCusWiseReportData(FromDate,ToDate, SlpName, DbName, Flag).observe(this, new Observer<CommanResorce<List<CusReportWiseModel>>>() {
            @Override
            public void onChanged(CommanResorce<List<CusReportWiseModel>> listCommanResorce) {

                if (listCommanResorce.data != null && !listCommanResorce.data.isEmpty()) {

                    List<CusReportWiseModel> list = new ArrayList<>() ;

                    list = listCommanResorce.data;


                    List<CusReportWiseModel>  filterList ;
                    filterList = new ArrayList<>();


                    for(int i = 0;i < list.size();i++){
                        switch (EmpType) {
                            case "Sales Employee":
                                if (EmpTypePName.equals(list.get(i).getSalesPerson())) {
                                    filterList.add(list.get(i));
                                }
                                break;
                            case "Collection Person":
                                if (EmpTypePName.equals(list.get(i).getCollectionPerson())) {
                                    filterList.add(list.get(i));
                                }
                                break;
                            case "Both (SE+CP)":
//                                if (EmpTypePName.equals(list.get(i).getSalesPerson()) && EmpTypePName.equals(list.get(i).getCollectionPerson())) {
//                                    filterList.add(list.get(i));
//                                }
                                System.out.println("EmpType "+EmpType +" " +EmpTypePName+" "+list.size());
                                String SP = "";
                                String CP = "";
                                if(list.get(i).getSalesPerson() != null){
                                    SP = list.get(i).getSalesPerson();
                                }
                                if(list.get(i).getCollectionPerson() != null){
                                    CP = list.get(i).getCollectionPerson();
                                }

                                if ((EmpTypePName.equals(SP.toLowerCase()) || EmpTypePName.equals(SP.toUpperCase() ) || (EmpTypePName.equals(CP.toLowerCase()) ||EmpTypePName.equals(CP.toUpperCase()) ) )){
                                    filterList.add(list.get(i));
                                }
                                break;
                        }

                    }

                    Mainlist = filterList;
                    BackUpList = filterList;

                    UpdateList("Gross");

                    GetBarChart(PostFromDate,PostToDate,SlpName,MFlag,EmpType,EmpTypePName);
                }

                AppUtil.hideProgressDialog();
            }
        });

//        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerAmt.setAdapter(adapter);

        binding.spinnerAmt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                String selectedItem = parentView.getItemAtPosition(position).toString();
                if (selectedItem.equals("Gross")) {
                    SortBy = "Gross";
                    UpdateList("Gross");
                    if(MonList != null){
                        if(!TYPE.equals("Purchase Register")){
                            if(!MonList.isEmpty()) {
                                UpdateBarchart(MonList, "Gross");
                            }
                        }


                    }

                }
                else if (selectedItem.equals("Net")) {
                    SortBy = "Net";
                    UpdateList("Net");
                    if(MonList != null) {
                        if(!TYPE.equals("Purchase Register")) {
                            if (!MonList.isEmpty()) {
                                UpdateBarchart(MonList, "Net");
                            }
                        }

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing if nothing is selected
            }
        });

        int arrayResource = -1;

//        if (TYPE.equals("Sales")) {
//            arrayResource = R.array.SalesOptions;
//        } else if (TYPE.equals("Purchase") || TYPE.equals("Vendor Outstanding")) {
//            arrayResource = R.array.PurchaseOptions;
//        } else if (TYPE.equals("Customer Outstanding")) {
//            arrayResource = R.array.CustomerOutstanding;
//        }
        arrayResource = R.array.options;
        if (arrayResource != -1) {
            ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource(this,
                    arrayResource, android.R.layout.simple_spinner_item);

            sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.spinnerGroupby.setAdapter(sortAdapter);
        }


        binding.spinnerGroupby.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {


                String selectedItem = parentView.getItemAtPosition(position).toString();
                if (selectedItem.equals("Gross")) {
                    SortBy = "Gross";
                    UpdateList("Gross");
                    if(MonList != null){
                        if(!TYPE.equals("Purchase Register")){
                            if(!MonList.isEmpty()) {
                                UpdateBarchart(MonList, "Gross");
                            }
                        }

                    }

                }
                else if (selectedItem.equals("Net")) {
                    SortBy = "Net";
                    UpdateList("Net");
                    if(MonList != null) {
                        if(!TYPE.equals("Purchase Register")){
                            if(!MonList.isEmpty()) {
                                UpdateBarchart(MonList, "Net");
                            }
                        }

                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing if nothing is selected
            }
        });

    }

    private void UpdateListContent(String selectedItem) {

        CusAdapter.setData(Mainlist,getApplicationContext(),
                ViewFromDate,
                ViewToDate,
                PostFromDate,
                PostToDate,
                SlpName,
                TotalAmt,
                TYPE,
                selectedItem);

        if(selectedItem.contains("Gross")){
            UpdateList("Gross");
        }else {
            UpdateList("Net");
        }

    }

    private void UpdateList(String value) {


        String sortBy = "";
        if(value.equals("Gross")) {
            sortBy = "Gross";
        } else if(value.equals("Net")){
            sortBy = "Net";
        }

        CusAdapter.setData(Mainlist,getApplicationContext(),
                ViewFromDate,
                ViewToDate,
                PostFromDate,
                PostToDate,
                SlpName,
                TotalAmt,
                TYPE,
                sortBy);


        double Tamt = 0;
        double Crn = 0;
        double CDAmt = 0;
        for(int i = 0;i < Mainlist.size();i++){

            if(value.equals("Gross")){

                if(TYPE.equals("Sales" )|| TYPE.equals("Customer Outstanding")){
                    if(Mainlist.get(i).getGrossSalesAmt() != null){
                        Tamt += Double.parseDouble(Mainlist.get(i).getGrossSalesAmt());
                    }
                }
                else if(TYPE.equals("Purchase")|| TYPE.equals("Vendor Outstanding")){
                    if(Mainlist.get(i).getGrossPurchaseAmt() != null){
                        Tamt += Double.parseDouble(Mainlist.get(i).getGrossPurchaseAmt());
                    }
                }

                if (TYPE.equals("Sales")) {
                    if (Mainlist.get(i).getGrossAmtINV_CRN() != null) {
                        Crn += Double.parseDouble(Mainlist.get(i).getGrossAmtINV_CRN());
                    }
                } else if (TYPE.equals("Customer Outstanding")) {
                    if (Mainlist.get(i).getGrossAmtINV_ARCRN() != null) {
                        Crn += Double.parseDouble(Mainlist.get(i).getGrossAmtINV_ARCRN());
                    }
                } else if(TYPE.equals("Purchase")|| TYPE.equals("Vendor Outstanding")){
                    if (Mainlist.get(i).getGrossAmtApCrn() != null) {
                        Crn += Double.parseDouble(Mainlist.get(i).getGrossAmtApCrn());
                    }
                }


                if (TYPE.equals("Sales")) {
                    if (Mainlist.get(i).getGrossCrditAmt() != null) {
                        CDAmt += Double.parseDouble(Mainlist.get(i).getGrossCrditAmt());
                    }

                } else if (TYPE.equals("Customer Outstanding")) {
                    if (Mainlist.get(i).getGrossCrdAmt() != null) {
                        CDAmt += Double.parseDouble(Mainlist.get(i).getGrossCrdAmt());
                    }
                }
                else if(TYPE.equals("Purchase")|| TYPE.equals("Vendor Outstanding")){
                    if (Mainlist.get(i).getGrossDebitAmt() != null) {
                        CDAmt += Double.parseDouble(Mainlist.get(i).getGrossDebitAmt());
                    }
                }

            }
            else if(value.equals("Net")){

                if(TYPE.equals("Sales" )|| TYPE.equals("Customer Outstanding")){
                    if(Mainlist.get(i).getNetSalesAmt() != null){
                        Tamt += Double.parseDouble(Mainlist.get(i).getNetSalesAmt());
                    }
                }
                else if(TYPE.equals("Purchase")|| TYPE.equals("Vendor Outstanding")){
                    if(Mainlist.get(i).getNetPurchaseAmt() != null){
                        Tamt += Double.parseDouble(Mainlist.get(i).getNetPurchaseAmt());
                    }
                }

                if(TYPE.equals("Sales")){
                    if(Mainlist.get(i).getNetAmtINV_CRN() != null) {
                        Crn += Double.parseDouble(Mainlist.get(i).getNetAmtINV_CRN());
                    }
                }
                else if(TYPE.equals("Customer Outstanding")) {
                    if (Mainlist.get(i).getNetAmtINV_ARCRN() != null) {
                        Crn += Double.parseDouble(Mainlist.get(i).getNetAmtINV_ARCRN());
                    }
                }
                else if(TYPE.equals("Purchase")|| TYPE.equals("Vendor Outstanding")){
                    if(Mainlist.get(i).getNetAmtApCrn() != null){
                        Crn += Double.parseDouble(Mainlist.get(i).getNetAmtApCrn());
                    }
                }






                if(TYPE.equals("Sales" )|| TYPE.equals("Customer Outstanding")){
                    if(Mainlist.get(i).getNetCrdAmt() != null){
                        CDAmt += Double.parseDouble(Mainlist.get(i).getNetCrdAmt());
                    }
                }
                else if(TYPE.equals("Purchase")|| TYPE.equals("Vendor Outstanding")){
                    if(Mainlist.get(i).getNetDebitAmt() != null){
                        CDAmt += Double.parseDouble(Mainlist.get(i).getNetDebitAmt());
                    }
                }
            }

            DecimalFormat df = new DecimalFormat("0.00");
            String formattedTamt = df.format(Tamt);

            String formattedCrn = df.format(Crn);

            String formattedCDAmt = df.format(CDAmt);

            binding.txtTotalAmt.setText("₹ "+formattedCrn);
            binding.CrnAmt.setText("₹ "+formattedTamt);
            binding.CDAmt.setText("₹ "+formattedCDAmt);

            binding.CrnName.setText(TYPE);

            if(TYPE.equals("Sales") ||TYPE.equals("Customer Outstanding")){
                binding.CDName.setText("Return / Credit Note");
            }
            else if(TYPE.equals("Purchase") || TYPE.equals("Vendor Outstanding")){
                binding.CDName.setText("Return / Debit Note");
            }

        }




    }

    public String convertToMonth(int monthNumber) {
        String monthName;
        switch (monthNumber) {
            case 1:
                monthName = "Jan";
                break;
            case 2:
                monthName = "Feb";
                break;
            case 3:
                monthName = "Mar";
                break;
            case 4:
                monthName = "Apr";
                break;
            case 5:
                monthName = "May";
                break;
            case 6:
                monthName = "Jun";
                break;
            case 7:
                monthName = "Jul";
                break;
            case 8:
                monthName = "Aug";
                break;
            case 9:
                monthName = "Sep";
                break;
            case 10:
                monthName = "Oct";
                break;
            case 11:
                monthName = "Nov";
                break;
            case 12:
                monthName = "Dec";
                break;
            default:
                throw new IllegalArgumentException("Invalid month number: " + monthNumber);
        }
        return monthName;
    }

//    private void createPDF() {
//        // Create a new document
//        PdfDocument document = new PdfDocument();
//
//        // Create a page description
//        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
//
//        // Start a page
//        PdfDocument.Page page = document.startPage(pageInfo);
//
//        // Check if the page is null
//        if (page == null) {
//            Log.e("MainActivity", "Page is null");
//            return;
//        }
//
//        Canvas canvas = page.getCanvas();
//        Paint paint = new Paint();
//        paint.setColor(Color.BLACK);
//        paint.setTextSize(16);
//
//        // Write text on the PDF
//
//        // Random data generation
//        Random random = new Random();
//        String companyAddress = "Company Address: " + (random.nextInt(999) + 1) + " Random St, City, Country";
//        String contact = "Contact: +1 " + (random.nextInt(900000000) + 100000000);
//        String email = "Email: contact" + (random.nextInt(9999)) + "@company.com";
//
//        // Draw the company information
//        canvas.drawText(companyAddress, 80, 50, paint);
//        canvas.drawText(contact, 80, 70, paint);
//        canvas.drawText(email, 80, 90, paint);
//
//        // Draw item list header
//        canvas.drawText("Item Name", 80, 130, paint);
//        canvas.drawText("Amount", 400, 130, paint);
//
//        // Generate and draw random items
//        int totalAmt = 0;
//        int startY = 160;
//        for (int i = 1; i <= 10; i++) {
//            String itemName = "Item " + i;
//            int amount = random.nextInt(500) + 1;
//            totalAmt += amount;
//            canvas.drawText(itemName, 80, startY, paint);
//            canvas.drawText("$" + amount, 400, startY, paint);
//            startY += 30;
//        }
//
//        // Draw total amount
//        paint.setFakeBoldText(true);
//        canvas.drawText("Total Amount", 80, startY + 30, paint);
//        canvas.drawText("$" + totalAmt, 400, startY + 30, paint);
//
//        // Finish the page
//        document.finishPage(page);
//
//        // Write the document content
//        File directory = getCacheDir();
//        File file = new File(directory, "sample.pdf");
//
//        try {
//            document.writeTo(new FileOutputStream(file));
//            Log.d("MainActivity", "PDF created at: " + file.getAbsolutePath());
//        } catch (IOException e) {
//            Log.e("MainActivity", "Error writing PDF: " + e.toString());
//        } finally {
//            // Close the document
//            document.close();
//        }
//
//        // View the PDF
//        Uri uri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);
//        Intent viewIntent = new Intent(Intent.ACTION_VIEW);
//        viewIntent.setDataAndType(uri, "application/pdf");
//        viewIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//        // Share the PDF
//        Intent shareIntent = new Intent(Intent.ACTION_SEND);
//        shareIntent.setType("application/pdf");
//        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
//        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//        // Create chooser intent
//        Intent chooserIntent = Intent.createChooser(shareIntent, "Share PDF");
//        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { viewIntent });
//
//        // Start the chooser intent
//        startActivity(chooserIntent);
//    }
//
//    private void createPDF2() {
//        // Create a new document
//        PdfDocument document = new PdfDocument();
//
//        // Create a page description
//        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
//
//        // Start a page
//        PdfDocument.Page page = document.startPage(pageInfo);
//
//        // Check if the page is null
//        if (page == null) {
//            Log.e("MainActivity", "Page is null");
//            return;
//        }
//
//        Canvas canvas = page.getCanvas();
//        Paint paint = new Paint();
//        paint.setColor(Color.BLACK);
//        paint.setTextSize(16);
//
//        // Write text on the PDF
//        Random random = new Random();
//        String companyAddress = "Company Address: " + (random.nextInt(999) + 1) + " Random St, City, Country";
//        String contact = "Contact: +1 " + (random.nextInt(900000000) + 100000000);
//        String email = "Email: contact" + (random.nextInt(9999)) + "@company.com";
//
//        // Draw the company information
//        canvas.drawText(companyAddress, 80, 50, paint);
//        canvas.drawText(contact, 80, 70, paint);
//        canvas.drawText(email, 80, 90, paint);
//
//        // Draw item list header
//        canvas.drawText("Item Name", 80, 130, paint);
//        canvas.drawText("Amount", 400, 130, paint);
//
//        // Generate and draw random items
//        int totalAmt = 0;
//        int startY = 160;
//        for (int i = 1; i <= 10; i++) {
//            String itemName = "Item " + i;
//            int amount = random.nextInt(500) + 1;
//            totalAmt += amount;
//            canvas.drawText(itemName, 80, startY, paint);
//            canvas.drawText("$" + amount, 400, startY, paint);
//            startY += 30;
//        }
//
//        // Draw total amount
//        paint.setFakeBoldText(true);
//        canvas.drawText("Total Amount", 80, startY + 30, paint);
//        canvas.drawText("$" + totalAmt, 400, startY + 30, paint);
//
//        // Finish the page
//        document.finishPage(page);
//
//        // Write the document content to a temporary file
////        File directory = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
////
//        File file = new File(getCacheDir(), "sample.pdf");
//
//        try {
//            document.writeTo(new FileOutputStream(file));
//            Log.d("MainActivity", "PDF created at: " + file.getAbsolutePath());
//            viewPDF(file);
//            sharePDF(file);
//        } catch (IOException e) {
//            Log.e("MainActivity", "Error writing PDF: " + e.toString());
//        } finally {
//            // Close the document
//            document.close();
//        }
//    }
//
//    private void viewPDF(File file) {
//        Uri uri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);
//
//        //Uri uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", file);
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setDataAndType(uri, "application/pdf");
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        try {
//            startActivity(intent);
//        } catch (ActivityNotFoundException e) {
//            Toast.makeText(this, "No application available to view PDF", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void sharePDF(File file) {
//        Uri uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", file);
//        Intent shareIntent = new Intent(Intent.ACTION_SEND);
//        shareIntent.setType("application/pdf");
//        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
//        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        startActivity(Intent.createChooser(shareIntent, "Share PDF using"));
//    }


}


