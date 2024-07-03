package com.softcore.vtpsales;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
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
import com.softcore.vtpsales.Adaptors.PR_AdapterCustWiseDetReport;
import com.softcore.vtpsales.AppUtils.AppUtil;
import com.softcore.vtpsales.Model.CommanResorce;
import com.softcore.vtpsales.Model.CusReportWiseModel;
import com.softcore.vtpsales.ViewModel.CusWiseReportViewModel;
import com.softcore.vtpsales.databinding.ActivityReportsDetListBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PR_ReportsDetListActivity extends AppCompatActivity {
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
    String SortBy;
    String EmpType;
    String EmpTypePName;

    PR_AdapterCustWiseDetReport adapter;

    List<CusReportWiseModel> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= ActivityReportsDetListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.layAmtBar.setVisibility(View.GONE);
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

        EmpType = AppUtil.getStringData(getApplicationContext(),"EmpType","");
        EmpTypePName = AppUtil.getStringData(getApplicationContext(),"EmpTypePName","");


        binding.laybar.backId.setVisibility(View.VISIBLE);
        binding.laybar.backId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.txtDates.setText(ViewFromDate+" to "+ViewToDate);

//        binding.txtSlPName.setText(SlpName);
        binding.txtTotalAmt.setText(TotalAmt);

       // binding.txtTotalAmt.setText(String.format("%.2f", Double.parseDouble(TotalAmt)));



        binding.txtCustName.setText(CusName);
        System.out.println("SplName From Reports det screen: "+SlpName);

        GetCusWiseReportList(PostFromDate,PostToDate,SlpName,Flag);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PR_AdapterCustWiseDetReport();
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setNestedScrollingEnabled(false);

        binding.btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> stringList = Arrays.asList("Bills", "Ledger Group", "Voucher Group","Stock Item","Stock Group","Stock Category","Cost Center");
             //   showBottomSheet(stringList);

            }
        });

    }


    private void GetCusWiseReportList (String FromDate,String ToDate,String SlpName,String Flag){

        AppUtil.showProgressDialog(binding.getRoot(),"Loading");
        String DbName = AppUtil.getStringData(getApplicationContext(),"DatabaseName","");

        System.out.println("Get CusWise List : "+FromDate+" "+ToDate+" "+ SlpName+" "+ DbName+" "+ Flag);
        CusWiseReportViewModel cusWiseDetReportViewModel= new ViewModelProvider(this).get(CusWiseReportViewModel.class);
        cusWiseDetReportViewModel.getCusWiseReportData(FromDate,ToDate, SlpName, DbName, Flag).observe(this, new Observer<CommanResorce<List<CusReportWiseModel>>>() {
            @Override
            public void onChanged(CommanResorce<List<CusReportWiseModel>> listCommanResorce) {

                if (listCommanResorce.data != null && !listCommanResorce.data.isEmpty()) {

                     list = new ArrayList<>();
                    for (CusReportWiseModel model : listCommanResorce.data) {
                         if (CusName.equalsIgnoreCase(model.getCustomerName())) {
                             list.add(model);
                         }else if(CusName.equalsIgnoreCase(model.getCustomer_Name())){
                             list.add(model);
                         }else if(CusName.equalsIgnoreCase(model.getVendor_Name())){
                             list.add(model);
                         }
                         else if(CusName.equalsIgnoreCase(model.getVendorName())){
                             list.add(model);
                         }
                    }
//                    Gson gson = new Gson();
//                    String json = gson.toJson(list);
//
//                    System.out.println("Json list barchart:"+json);

//                    List<CusReportWiseModel> list = new ArrayList<>() ;
//
//                    list = listCommanResorce.data;


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

                    List<CusReportWiseModel>  newfilterList = new ArrayList<>();


                    Set<String> DocEntries = new HashSet<>();
                    for (int a = 0; a < filterList.size(); a++) {
                        if (!DocEntries.contains(filterList.get(a).getDocEntry())) {
                            DocEntries.add(filterList.get(a).getDocEntry());
                            newfilterList.add(filterList.get(a));
                        }
                    }



















                   list =  newfilterList;

                    UpdateList(newfilterList,"Gross");



                   // we Need to customise list properly like in Jan 23 and Amount
                  if(TYPE.equals("Vendor Outstanding") || TYPE.equals("Customer Outstanding") || TYPE.equals("Purchase Register")  ) {

                  }else{
                      CustomizeList(list,"Gross");
                  };

                   // adapter.setData(listCommanResorce.data,getApplicationContext());
                }

                AppUtil.hideProgressDialog();
            }
        });


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerAmt.setAdapter(adapter);

        binding.spinnerAmt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                String selectedItem = parentView.getItemAtPosition(position).toString();

                if (selectedItem.equals("Gross")) {
                    SortBy = "Gross sales Amount";

                    if(list != null){
                        UpdateList(list,"Gross");
                        if(TYPE.equals("Vendor Outstanding") || TYPE.equals("Customer Outstanding") || TYPE.equals("Purchase Register")  ) {

                        }else {
                            CustomizeList(list, "Gross");
                        }
                    }
                }
                else if (selectedItem.equals("Net")) {
                    SortBy = "Net sales Amount";
                    if(list != null){
                        UpdateList(list,"Net");
                        if(TYPE.equals("Vendor Outstanding") || TYPE.equals("Customer Outstanding") || TYPE.equals("Purchase Register")  ) {

                        }else {
                            CustomizeList(list, "Net");
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

        if (TYPE.equals("Sales")) {
            arrayResource = R.array.SalesOptions;
        } else if (TYPE.equals("Purchase") || TYPE.equals("Vendor Outstanding")) {
            arrayResource = R.array.PurchaseOptions;
        } else if (TYPE.equals("Customer Outstanding")) {
            arrayResource = R.array.CustomerOutstanding;
        }
        else if (TYPE.equals("Purchase Register")) {
            arrayResource = R.array.CustomerOutstanding;
        }

        if (arrayResource != -1) {
            ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource(this,
                    arrayResource, android.R.layout.simple_spinner_item);

            sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.spinnerGroupby.setAdapter(sortAdapter);
        }


        binding.spinnerGroupby.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {


                if(list != null){
                    String selectedItem = parentView.getItemAtPosition(position).toString();

                    if (selectedItem.equals("Gross")) {
                        SortBy = "Gross sales Amount";

                        if(list != null){
                            UpdateList(list,"Gross");
                            if(TYPE.equals("Vendor Outstanding") || TYPE.equals("Customer Outstanding") || TYPE.equals("Purchase Register")  ) {

                            }else {
                                CustomizeList(list, "Gross");
                            }
                        }
                    }
                    else if (selectedItem.equals("Net")) {
                        SortBy = "Net sales Amount";
                        if(list != null){
                            UpdateList(list,"Net");
                            if(TYPE.equals("Vendor Outstanding") || TYPE.equals("Customer Outstanding") || TYPE.equals("Purchase Register")  ) {

                            }else {
                                CustomizeList(list, "Net");
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

//    private void UpdateListContent(String selectedItem) {
//
//
//        adapter.setData(list,getApplicationContext(),TYPE,selectedItem);
//
//        if(selectedItem.contains("Gross")){
//            UpdateList(list,"Gross");
//        }else {
//            UpdateList(list,"Net");
//        }
//
//    }

    private void UpdateList(List<CusReportWiseModel> list, String value) {

        String sortBy = "";
        if(value.equals("Gross")) {
            sortBy = value;
        } else if(value.equals("Net")){
            sortBy = value;
        }


        adapter.setData(list,getApplicationContext(),TYPE,sortBy);

        double Tamt = 0;
        double Crn = 0;
        double CDAmt = 0;



        for(int i = 0;i < list.size();i++) {

            if(value.equals("Gross")) {
                Tamt += Double.parseDouble(list.get(i).getGrossAmt());
            }
            else if(value.equals("Net")){
                Tamt += Double.parseDouble(list.get(i).getNetAmt());

            }
        }

        
        for(int i = 0;i < list.size();i++){

//            if(value.equals("Gross")){
//                if(TYPE.equals("Sales" )|| TYPE.equals("Customer Outstanding")){
//                    if(list.get(i).getGrossSalesAmt() != null){
//                        Tamt += Double.parseDouble(list.get(i).getGrossSalesAmt());
//                    }
//                }
//                else if(TYPE.equals("Purchase")|| TYPE.equals("Vendor Outstanding")){
//                    if(list.get(i).getGrossPurchaseAmt() != null){
//                        Tamt += Double.parseDouble(list.get(i).getGrossPurchaseAmt());
//                    }
//                }
//
//                if (TYPE.equals("Sales")) {
//                    if (list.get(i).getGrossAmtINV_CRN() != null) {
//                        Crn += Double.parseDouble(list.get(i).getGrossAmtINV_CRN());
//                    }
//                }
//                else if (TYPE.equals("Customer Outstanding")|| TYPE.equals("Vendor Outstanding")) {
//                    if (list.get(i).getGrossAmtINV_ARCRN() != null) {
//                        Crn += Double.parseDouble(list.get(i).getGrossAmtINV_ARCRN());
//                    }
//                }
//                else if(TYPE.equals("Purchase")){
//                    if (list.get(i).getGrossAmtApCrn() != null) {
//                        Crn += Double.parseDouble(list.get(i).getGrossAmtApCrn());
//                    }
//                }
//
//
//                if (TYPE.equals("Sales")) {
//                    if (list.get(i).getGrossCrditAmt() != null) {
//                        CDAmt += Double.parseDouble(list.get(i).getGrossCrditAmt());
//                    }
//
//                }
////                else if (TYPE.equals("Customer Outstanding")) {
////                    if (list.get(i).getGrossCrdAmt() != null) {
////                        CDAmt += Double.parseDouble(list.get(i).getGrossCrdAmt());
////                    }
////                }
//                else if(TYPE.equals("Purchase")|| TYPE.equals("Vendor Outstanding")){
//                    if (list.get(i).getGrossDebitAmt() != null) {
//                        CDAmt += Double.parseDouble(list.get(i).getGrossDebitAmt());
//                    }
//                }
//
//            }
//            else if(value.equals("Net")){
//
//                if(TYPE.equals("Sales" )|| TYPE.equals("Customer Outstanding")){
//                    if(list.get(i).getNetSalesAmt() != null){
//                        Tamt += Double.parseDouble(list.get(i).getNetSalesAmt());
//                    }
//                }
//                else if(TYPE.equals("Purchase")|| TYPE.equals("Vendor Outstanding")){
//                    if(list.get(i).getNetPurchaseAmt() != null){
//                        Tamt += Double.parseDouble(list.get(i).getNetPurchaseAmt());
//                    }
//                }
//
//                if(TYPE.equals("Sales")){
//                    if(list.get(i).getNetAmtINV_CRN() != null) {
//                        Crn += Double.parseDouble(list.get(i).getNetAmtINV_CRN());
//                    }
//                }
//                else if(TYPE.equals("Customer Outstanding")|| TYPE.equals("Vendor Outstanding")) {
//                    if (list.get(i).getNetAmtINV_ARCRN() != null) {
//                        Crn += Double.parseDouble(list.get(i).getNetAmtINV_ARCRN());
//                    }
//                }
//                else if(TYPE.equals("Purchase")){
//                    if(list.get(i).getNetAmtApCrn() != null){
//                        Crn += Double.parseDouble(list.get(i).getNetAmtApCrn());
//                    }
//                }
//
//                if(TYPE.equals("Sales" )|| TYPE.equals("Customer Outstanding")){
//                    if(list.get(i).getNetCrdAmt() != null){
//                        CDAmt += Double.parseDouble(list.get(i).getNetCrdAmt());
//                    }
//                }
//                else if(TYPE.equals("Purchase")|| TYPE.equals("Vendor Outstanding")){
//                    if(list.get(i).getNetDebitAmt() != null){
//                        CDAmt += Double.parseDouble(list.get(i).getNetDebitAmt());
//                        System.out.println("Type and Debit :" + TYPE+" "+String.valueOf(CDAmt));
//                    }
//                }
//
//            }

            DecimalFormat df = new DecimalFormat("0.00");
            String formattedTamt = df.format(Tamt);
//            String formattedCrn = df.format(Crn);
//            String formattedCDAmt = df.format(CDAmt);

            binding.txtTotalAmt.setText("₹ "+formattedTamt);
//            binding.CrnAmt.setText("₹ "+formattedTamt);
//            binding.CDAmt.setText("₹ "+formattedCDAmt);

            binding.CrnName.setText(TYPE);
            binding.CDName.setText("Return / Credit Note");


        }
    }

    private void CustomizeList(List<CusReportWiseModel> list, String value) {
        Map<String, Double> monthYearToTotalBalanceMap = new HashMap<>();
        Map<String, List<Double>> monthYearToBalanceListMap = new HashMap<>();

        // Iterate over the list and sum up the BalanceDue values for each month-year combination
        for (CusReportWiseModel model : list) {

            double balanceDue = 0;
            if (value.equals("Gross")) {
                if (TYPE.equals("Sales") || TYPE.equals("Customer Outstanding")) {
                    if (model.getGrossAmtINV_CRN() != null) {
                        balanceDue = Double.parseDouble(model.getGrossAmtINV_CRN());
                    }
                } else if (TYPE.equals("Purchase") || TYPE.equals("Vendor Outstanding")) {
                    if (model.getGrossAmtApCrn() != null) {
                        balanceDue = Double.parseDouble(model.getGrossAmtApCrn());
                    }
                }
            } else if (value.equals("Net")) {
                if (TYPE.equals("Sales") || TYPE.equals("Customer Outstanding")) {
                    if (model.getNetAmtINV_CRN() != null) {
                        balanceDue = Double.parseDouble(model.getNetAmtINV_CRN());
                    }
                } else if (TYPE.equals("Purchase") || TYPE.equals("Vendor Outstanding")) {
                    if (model.getNetAmtApCrn() != null) {
                        balanceDue = Double.parseDouble(model.getNetAmtApCrn());
                    }
                }
            }

            if (balanceDue != 0) {
                String postingDate = model.getPostingDate();

                // Extract the month and year from the postingDate
                String[] dateParts = postingDate.split("-");
                if (dateParts.length >= 2) {
                    String monthYear = dateParts[0] + "-" + dateParts[1];

                    String formatedDate = AppUtil.convertMonthYearFormat(monthYear);

                    // Sum the BalanceDue values for the same month-year
                    double currentBalance = monthYearToTotalBalanceMap.getOrDefault(formatedDate, 0.0);
                    monthYearToTotalBalanceMap.put(formatedDate, currentBalance + balanceDue);

                    // Add the BalanceDue value to the list for the same month-year
                    List<Double> balanceList = monthYearToBalanceListMap.getOrDefault(formatedDate, new ArrayList<>());
                    balanceList.add(balanceDue);
                    monthYearToBalanceListMap.put(formatedDate, balanceList);
                }
            }
        }

        // Convert the total balance map to a JSON array
        JSONArray jsonArray = convertMapToJsonArray(monthYearToTotalBalanceMap);
        System.out.println("month And Amount result: " + jsonArray.toString());

        // Sort the JSON array
        JsonListSort(jsonArray.toString());

        // Print individual amounts for each month-year
        for (Map.Entry<String, List<Double>> entry : monthYearToBalanceListMap.entrySet()) {
            System.out.println("Month-Year: " + entry.getKey() + ", Balance Due: " + entry.getValue());
        }
    }


    private void JsonListSort(String jsonString) {

        System.out.println("JsonListSort"+jsonString);

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


        JSONArray sortedJsonArray = new JSONArray(dataList);

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
        binding.idBarChart.animateY(1000);
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
        binding.idBarChart.setTouchEnabled(false);
        binding.idBarChart.setDragEnabled(false);
        binding.idBarChart.setScaleEnabled(false);
        binding.idBarChart.setPinchZoom(false); // Disable pinch zoom
        binding.idBarChart.invalidate(); // refresh

    }



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
//    private void showBottomSheet(List<String> stringList) {
//
//        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this); // Apply custom style
//
//        View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_layout, null);
//        bottomSheetDialog.setContentView(bottomSheetView);
//
//        RecyclerView recyclerView = bottomSheetView.findViewById(R.id.recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)); // Optional: Add divider
//
//        // Create a custom adapter for the RecyclerView
//        RecyclerView.Adapter adapter = new RecyclerView.Adapter() {
//            @NonNull
//            @Override
//            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bottomsheet_design, parent, false);
//                return new RecyclerView.ViewHolder(itemView) {};
//            }
//
//            @Override
//            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//                TextView textView = holder.itemView.findViewById(R.id.names);
//                textView.setText(stringList.get(position));
//                textView.setTextColor(Color.WHITE); // Set text color to white
//
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        String selectedString = stringList.get(position);
//                        binding.spinnerGroupby.setText(selectedString);
//                        bottomSheetDialog.dismiss(); // Dismiss the bottom
//                    }
//                });
//
//            }
//
//            @Override
//            public int getItemCount() {
//                return stringList.size();
//            }
//        };
//
//        recyclerView.setAdapter(adapter);
//
//        bottomSheetDialog.show();
//    }


}