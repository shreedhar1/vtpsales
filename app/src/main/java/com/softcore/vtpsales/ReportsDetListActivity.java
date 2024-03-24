package com.softcore.vtpsales;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.softcore.vtpsales.Adaptors.AdapterCustWiseDetReport;
import com.softcore.vtpsales.AppUtils.AppUtil;
import com.softcore.vtpsales.Model.CommanResorce;
import com.softcore.vtpsales.Model.CusReportWiseDetModel;
import com.softcore.vtpsales.Model.MonthModel;
import com.softcore.vtpsales.ViewModel.CusWiseDetReportViewModel;
import com.softcore.vtpsales.databinding.ActivityReportsDetListBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportsDetListActivity extends AppCompatActivity {
    ActivityReportsDetListBinding binding;

    String ViewFromDate;
    String ViewToDate;
    String PostFromDate;
    String PostToDate;
    String SlpName;
    String Flag;
    String TotalAmt;
    String TYPE;
    String CusName;

    AdapterCustWiseDetReport adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= ActivityReportsDetListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewFromDate=getIntent().getStringExtra("ViewFromDate");
        ViewToDate=getIntent().getStringExtra("ViewToDate");
        PostFromDate=getIntent().getStringExtra("FromDatePost");
        PostToDate=getIntent().getStringExtra("ToDatePost");
        SlpName=getIntent().getStringExtra("SlpName");
        Flag=getIntent().getStringExtra("Flag");
        TotalAmt=getIntent().getStringExtra("Amount");
        TYPE=getIntent().getStringExtra("TYPE");
        CusName =getIntent().getStringExtra("CusName");
        binding.laybar.appbarTextView.setText(TYPE);

        binding.laybar.backId.setVisibility(View.VISIBLE);
        binding.laybar.backId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.txtDates.setText(ViewFromDate+" to "+ViewToDate);

//        binding.txtSlPName.setText(SlpName);

        binding.txtTotalAmt.setText("₹ "+TotalAmt);

        binding.txtCustName.setText(CusName);

        GetCusWiseReportList(PostFromDate,PostToDate,SlpName,Flag);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterCustWiseDetReport();
        binding.recyclerView.setAdapter(adapter);

        binding.btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> stringList = Arrays.asList("Bills", "Ledger Group", "Voucher Group","Stock Item","Stock Group","Stock Category","Cost Center");
                showBottomSheet(stringList);

            }
        });

    }


    private void GetCusWiseReportList (String FromDate,String ToDate,String SlpName,String Flag){

        AppUtil.showProgressDialog(binding.getRoot(),"Loading");
        String DbName = AppUtil.getStringData(getApplicationContext(),"DatabaseName","");

        System.out.println("Get CusWise List : "+FromDate+" "+ToDate+" "+ SlpName+" "+ DbName+" "+ Flag);
        CusWiseDetReportViewModel cusWiseDetReportViewModel= new ViewModelProvider(this).get(CusWiseDetReportViewModel.class);
        cusWiseDetReportViewModel.getCusWiseReportData(FromDate,ToDate, SlpName, DbName, Flag).observe(this, new Observer<CommanResorce<List<CusReportWiseDetModel>>>() {
            @Override
            public void onChanged(CommanResorce<List<CusReportWiseDetModel>> listCommanResorce) {

                if (listCommanResorce.data != null && !listCommanResorce.data.isEmpty()) {


                    List<CusReportWiseDetModel> list = new ArrayList<>();
                    for (CusReportWiseDetModel model : listCommanResorce.data) {
                         if (CusName.equalsIgnoreCase(model.getCustomerName())) {
                             list.add(model);
                         }else if(CusName.equalsIgnoreCase(model.getCustomer_Name())){
                             list.add(model);
                         }else if(CusName.equalsIgnoreCase(model.getVendor_Name())){
                             list.add(model);
                         }
                    }
                    adapter.setData(list,getApplicationContext());



                   // we Need to customise list properly like in Jan 23 and Amount
                    CustomizeList(list);
                   // adapter.setData(listCommanResorce.data,getApplicationContext());
                }

                AppUtil.hideProgressDialog();
            }
        });



    }

    private void CustomizeList(List<CusReportWiseDetModel> list) {
        Map<String, Double> monthYearToBalanceMap = new HashMap<>();

        // Iterate over the list and sum up the BalanceDue values for each month-year combination
        for (CusReportWiseDetModel model : list) {
            String postingDate = model.getPostingDate();
            double balanceDue = Double.parseDouble(model.getBalanceDue());

            // Extract the month and year from the postingDate
            String[] dateParts = postingDate.split("-");
            if (dateParts.length >= 2) {
                String monthYear = dateParts[0] + "-" + dateParts[1];

                String formetedDate =  AppUtil.convertMonthYearFormat(monthYear);

                monthYearToBalanceMap.put(formetedDate, monthYearToBalanceMap.getOrDefault(monthYear, 0.0) + balanceDue);
            }
        }

        JSONArray jsonArray =  convertMapToJsonArray(monthYearToBalanceMap);
        System.out.println("month And Amount result: "+jsonArray.toString());


        JsonListSort(jsonArray.toString());

//        ShowBar(monList);
    }

    private void JsonListSort(String jsonString) {

        // Parse the JSON array into a list of objects
        List<JSONObject> dataList = new ArrayList<>();
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(jsonString);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                dataList.add(jsonArray.getJSONObject(i));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

// Sort the list by date
        Collections.sort(dataList, new Comparator<JSONObject>() {
            DateFormat dateFormat = new SimpleDateFormat("MMM yy");

            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                try {
                    Date date1 = dateFormat.parse(o1.getString("MonthYear"));
                    Date date2 = dateFormat.parse(o2.getString("MonthYear"));
                    return date1.compareTo(date2);
                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        });

// Convert the sorted list back to a JSON array
        JSONArray sortedJsonArray = new JSONArray(dataList);

// Create a horizontal bar chart
        //HorizontalBarChart barChart = findViewById(R.id.barchart);

        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        for (int i = 0; i < sortedJsonArray.length(); i++) {
            try {
                JSONObject item = sortedJsonArray.getJSONObject(i);
                double balanceDue = item.getDouble("BalanceDue");
                String monthYear = item.getString("MonthYear");
                entries.add(new BarEntry(i, (float) balanceDue));
                labels.add(monthYear);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        BarDataSet dataSet = new BarDataSet(entries, "Balance Due");
        BarData barData = new BarData(dataSet);
        binding.idBarChart.setData(barData);
        dataSet.setColor(Color.parseColor("#013F8F")); // Dark blue color
        XAxis xAxis = binding.idBarChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setGranularity(1f);
        dataSet.setValueTextColor(Color.parseColor("#013F8F"));

        Legend legend = binding.idBarChart.getLegend();

        LegendEntry[] legendEntries = new LegendEntry[1];
        LegendEntry entry = new LegendEntry();
        entry.label = "Balance Due"; // Your label
        entry.formColor = Color.parseColor("#013F8F"); // Dark blue color
        entry.form = Legend.LegendForm.SQUARE; // Use a square form
        legendEntries[0] = entry;

// Set the custom legend entry
        legend.setCustom(legendEntries);

        YAxis rightYAxis = binding.idBarChart.getAxisRight();

// Disable drawing of Y-axis labels on the right side
        rightYAxis.setDrawLabels(false);

      // Dark blue color
        binding.idBarChart.getXAxis().setDrawGridLines(false);
        binding.idBarChart.getAxisLeft().setDrawGridLines(false);
        rightYAxis.setDrawGridLines(false);
        binding.idBarChart.getDescription().setEnabled(false);
        binding.idBarChart.setTouchEnabled(true);
        binding.idBarChart.setDragEnabled(true);
        binding.idBarChart.setScaleEnabled(true);

        binding.idBarChart.invalidate(); // refresh

    }

//    private void ShowBar(List<MonthModel> monList) {
//        List<BarEntry> entries = new ArrayList<>();
//        for (int i = 0; i < monList.size(); i++) {
//            MonthModel monthModel = monList.get(i);
//
//            if (monthModel.getJanAmt() != null) {
//                entries.add(new BarEntry(i, Float.parseFloat(monthModel.getJanAmt())));
//            }
//            if (monthModel.getFebAmt() != null) {
//                entries.add(new BarEntry(i, Float.parseFloat(monthModel.getFebAmt())));
//            }
//            if (monthModel.getMarAmt() != null) {
//                entries.add(new BarEntry(i, Float.parseFloat(monthModel.getMarAmt())));
//            }
//            if (monthModel.getAprAmt() != null) {
//                entries.add(new BarEntry(i, Float.parseFloat(monthModel.getAprAmt())));
//            }
//            if (monthModel.getMayAmt() != null) {
//                entries.add(new BarEntry(i, Float.parseFloat(monthModel.getMayAmt())));
//            }
//            if (monthModel.getJunAmt() != null) {
//                entries.add(new BarEntry(i, Float.parseFloat(monthModel.getJunAmt())));
//            }
//            if (monthModel.getJulAmt() != null) {
//                entries.add(new BarEntry(i, Float.parseFloat(monthModel.getJulAmt())));
//            }
//            if (monthModel.getAugAmt() != null) {
//                entries.add(new BarEntry(i, Float.parseFloat(monthModel.getAugAmt())));
//            }
//            if (monthModel.getSepAmt() != null) {
//                entries.add(new BarEntry(i, Float.parseFloat(monthModel.getSepAmt())));
//            }
//            if (monthModel.getOctAmt() != null) {
//                entries.add(new BarEntry(i, Float.parseFloat(monthModel.getOctAmt())));
//            }
//            if (monthModel.getNovAmt() != null) {
//                entries.add(new BarEntry(i, Float.parseFloat(monthModel.getNovAmt())));
//            }
//            if (monthModel.getDecAmt() != null) {
//                entries.add(new BarEntry(i, Float.parseFloat(monthModel.getDecAmt())));
//            }
//        }
//
//        BarDataSet dataSet = new BarDataSet(entries, "Months");
//        BarData barData = new BarData(dataSet);
//        binding.idBarChart.setData(barData);
//
//        // Customize the appearance of the chart
//        binding.idBarChart.getDescription().setEnabled(false);
//        binding.idBarChart.getLegend().setEnabled(false);
//        binding.idBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
//        binding.idBarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(getMonthLabels()));
//        binding.idBarChart.invalidate(); // Refresh the chart
//    }

    private ArrayList<String> getMonthLabels() {
        ArrayList<String> labels = new ArrayList<>();
        labels.add("Jan");
        labels.add("Feb");
        labels.add("Mar");
        labels.add("Apr");
        labels.add("May");
        labels.add("Jun");
        labels.add("Jul");
        labels.add("Aug");
        labels.add("Sep");
        labels.add("Oct");
        labels.add("Nov");
        labels.add("Dec");
        return labels;

    }
    private JSONArray convertMapToJsonArray(Map<String, Double> monthYearToBalanceMap) {
        JSONArray jsonArray = new JSONArray();
        for (Map.Entry<String, Double> entry : monthYearToBalanceMap.entrySet()) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("MonthYear", entry.getKey());
                jsonObject.put("BalanceDue", entry.getValue());
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        return jsonArray;
    }
    private void showBottomSheet(List<String> stringList) {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this); // Apply custom style

        View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_layout, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        RecyclerView recyclerView = bottomSheetView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)); // Optional: Add divider

        // Create a custom adapter for the RecyclerView
        RecyclerView.Adapter adapter = new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bottomsheet_design, parent, false);
                return new RecyclerView.ViewHolder(itemView) {};
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                TextView textView = holder.itemView.findViewById(R.id.names);
                textView.setText(stringList.get(position));
                textView.setTextColor(Color.WHITE); // Set text color to white

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String selectedString = stringList.get(position);
                        binding.txtselect.setText(selectedString);
                        bottomSheetDialog.dismiss(); // Dismiss the bottom
                    }
                });

            }

            @Override
            public int getItemCount() {
                return stringList.size();
            }
        };

        recyclerView.setAdapter(adapter);

        bottomSheetDialog.show();
    }

//    private void showBottomSheet(List<String> stringList) {
//        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
//        View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_layout, null);
//        bottomSheetDialog.setContentView(bottomSheetView);
//
//        ListView listView = bottomSheetView.findViewById(R.id.list_view);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stringList);
//        listView.setAdapter(adapter);
//
//
//        listView.setOnItemClickListener((parent, view, position, id) -> {
//            String selectedString = stringList.get(position);
//            binding.txtselect.setText(selectedString);
//            bottomSheetDialog.dismiss(); // Dismiss the bottom sheet after selecting an item
//        });
//        bottomSheetDialog.show();
//    }

}