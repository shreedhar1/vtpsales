package com.softcore.vtpsales;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

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
import com.softcore.vtpsales.databinding.ActivityReportsListBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    AdapterCustWiseReport CusAdapter;

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
        SlpName=getIntent().getStringExtra("SlpName");
        Flag=getIntent().getStringExtra("Flag");
        MFlag=getIntent().getStringExtra("MFlag");
        TotalAmt=getIntent().getStringExtra("Amount");
        TYPE=getIntent().getStringExtra("TYPE");
        binding.laybar.appbarTextView.setText(TYPE);

        binding.laybar.backId.setVisibility(View.VISIBLE);
        binding.laybar.backId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.txtDates.setText(ViewFromDate+" to "+ViewToDate);

        binding.txtTotalAmt.setText(TotalAmt);
        Mainlist = new ArrayList<>();
        GetCusWiseReportList(PostFromDate,PostToDate,SlpName,Flag);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CusAdapter = new AdapterCustWiseReport();
        binding.recyclerView.setAdapter(CusAdapter);

    }
    private void GetBarChart(String fromDate, String toDate, String slpName, String mFlag) {
        System.out.println("MFlag:"+mFlag);
        String dbName = AppUtil.getStringData(getApplicationContext(), "DatabaseName", "");

        CusWiseReportViewModel cusWiseReportViewModel = new ViewModelProvider(this).get(CusWiseReportViewModel.class);
        cusWiseReportViewModel.getCusWiseReportData(fromDate, toDate, slpName, dbName, mFlag).observe(this, new Observer<CommanResorce<List<CusReportWiseModel>>>() {
            @Override
            public void onChanged(CommanResorce<List<CusReportWiseModel>> listCommanResorce) {
                if (listCommanResorce.data != null && !listCommanResorce.data.isEmpty()) {
                    List<CusReportWiseModel> monList = listCommanResorce.data;

                    MonList = monList;
                    UpdateBarchart(MonList,SortBy);

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

            }else {
                if(TYPE.equals("Sales" )){
                    value = Float.parseFloat(model.getGrossAmtINV_CRN());
                } else if( TYPE.equals("Customer Outstanding")){
                    value = Float.parseFloat(model.getGrossAmtINV_ARCRN());
                }
                else if(TYPE.equals("Purchase")|| TYPE.equals("Vendor Outstanding")){
                    value = Float.parseFloat(model.getGrossAmtApCrn());
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
        xAxis.setLabelRotationAngle(45f);

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
                    Mainlist = listCommanResorce.data;

                    UpdateList("Gross");

                    GetBarChart(PostFromDate,PostToDate,SlpName,MFlag);
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
                        UpdateBarchart(MonList,"Gross");
                    }

                }
                else if (selectedItem.equals("Net")) {
                    SortBy = "Net";
                    UpdateList("Net");
                    if(MonList != null) {
                        UpdateBarchart(MonList, "Net");
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
                        UpdateBarchart(MonList,"Gross");
                    }

                }
                else if (selectedItem.equals("Net")) {
                    SortBy = "Net";
                    UpdateList("Net");
                    if(MonList != null) {
                        UpdateBarchart(MonList, "Net");
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


}


