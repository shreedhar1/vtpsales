package com.softcore.vtpsales;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.softcore.vtpsales.Adaptors.AdapterInOutPayReport;
import com.softcore.vtpsales.AppUtils.AppUtil;
import com.softcore.vtpsales.Model.CommanResorce;
import com.softcore.vtpsales.Model.InOutPayModel;
import com.softcore.vtpsales.ViewModel.InOutPayReportViewModel;
import com.softcore.vtpsales.databinding.ActivityInOutPaymentListBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IncomingOutgoingList extends AppCompatActivity {
    ActivityInOutPaymentListBinding binding;

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

    AdapterInOutPayReport adapter;
    String EmpTypePName;
    String EmpType;
    List<InOutPayModel> list;
    List<InOutPayModel> FList;
    List<InOutPayModel> BackupList;
    Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= ActivityInOutPaymentListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EmpType = AppUtil.getStringData(getApplicationContext(),"EmpType","");
        EmpTypePName = AppUtil.getStringData(getApplicationContext(),"EmpTypePName","");

        BackupList = new ArrayList<>();

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
        binding.txtTotalAmt.setText(TotalAmt);
        binding.laybar.print.setVisibility(View.GONE);
        binding.laybar.shareId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(BackupList.size() != 0){
                    Intent intent = new Intent(getApplicationContext(), InOutPdfViewerActivity.class);
                    intent.putExtra("ReportTYPE", String.valueOf(TYPE));
                    intent.putExtra("SortBy", String.valueOf(SortBy));
                    intent.putExtra("ReportList", (Serializable) BackupList);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

            }
        });
        binding.laybar.searchId.setVisibility(View.VISIBLE);
        binding.laybar.searchId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.laybar.laybar3.setVisibility(View.GONE);
                binding.laybar.SearchLayId.setVisibility(View.VISIBLE);
                binding.laybar.appbarTextView.setVisibility(View.GONE);

                binding.laybar.searchEditText.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(binding.laybar.searchEditText, InputMethodManager.SHOW_IMPLICIT);
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
                updateListTxt(s.toString());
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


                FList = BackupList;

                UpdateList();

            }
        });


        // binding.txtTotalAmt.setText(String.format("%.2f", Double.parseDouble(TotalAmt)));



       // binding.txtCustName.setText(CusName);
        System.out.println("SplName From Reports det screen: "+SlpName);

        GetCusWiseReportList(PostFromDate,PostToDate,SlpName,Flag);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterInOutPayReport();
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setNestedScrollingEnabled(false);

//        binding.btnSort.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                List<String> stringList = Arrays.asList("Bills", "Ledger Group", "Voucher Group","Stock Item","Stock Group","Stock Category","Cost Center");
//                //   showBottomSheet(stringList);
//
//            }
//        });

    }

    private void updateListTxt(String CustomerName) {
        List<InOutPayModel> filterList = new ArrayList<>();
        String searchText = CustomerName.toLowerCase();

        for (int i = 0; i < BackupList.size(); i++) {
            InOutPayModel model = BackupList.get(i);

            if((model.getBPName() == null || model.getBPName().equals(""))){
            }else{

                if (BackupList.get(i).getBPName().toLowerCase().contains(searchText)) {
                    filterList.add(BackupList.get(i));
                }
            }


        }

        if (filterList.isEmpty()) {
            FList = BackupList;
        } else {
            FList = filterList;
        }


        UpdateList();  // Ensure the adapter is updated with the new list
    }



    private void GetCusWiseReportList (String FromDate,String ToDate,String SlpName,String Flag){
        list = new ArrayList<>();
        AppUtil.showProgressDialog(binding.getRoot(),"Loading");
        String DbName = AppUtil.getStringData(getApplicationContext(),"DatabaseName","");

        System.out.println("Get CusWise List : "+FromDate+" "+ToDate+" "+ SlpName+" "+ DbName+" "+ Flag);
        InOutPayReportViewModel viewModel= new ViewModelProvider(this).get(InOutPayReportViewModel.class);
        viewModel.getInOutDetails(FromDate,ToDate, SlpName, DbName, Flag).observe(this, new Observer<CommanResorce<List<InOutPayModel>>>() {
            @Override
            public void onChanged(CommanResorce<List<InOutPayModel>> listCommanResorce) {

                if (listCommanResorce.data != null && !listCommanResorce.data.isEmpty()) {

//                    Set<String> bpNameSet = new HashSet<>();
//                    for (InOutPayModel model : listCommanResorce.data) {
//                        if (!bpNameSet.contains(model.getBPName()+"_"+model.getSAP_Doc_No())) {
//                            bpNameSet.add(model.getBPName()+"_"+model.getSAP_Doc_No());
//
//                            list.add(model);
//                        }
//                    }


//                    List<InOutPayModel> listCommanResorce = new ArrayList<>();
                    // Initialize listCommanResorce with your data

                    list = listCommanResorce.data;

                    List<InOutPayModel> filterList = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        String salesPerson = list.get(i).getSalesPerson();
                        String collectionPerson = list.get(i).getCollectionPerson();
                        String empTypePNameLower = EmpTypePName.toLowerCase();

                        switch (EmpType) {
                            case "Sales Employee":
                                System.out.println("EmpType " + EmpType + " " + EmpTypePName + " " + list.size());
                                if (salesPerson != null && empTypePNameLower.equals(salesPerson.toLowerCase())) {
                                    filterList.add(list.get(i));
                                }
                                break;

                            case "Collection Person":
                                System.out.println("EmpType " + EmpType + " " + EmpTypePName + " " + list.size());
                                if (collectionPerson != null && empTypePNameLower.equals(collectionPerson.toLowerCase())) {
                                    filterList.add(list.get(i));
                                }
                                break;

                            case "Both (SE+CP)":
                                System.out.println("EmpType " + EmpType + " " + EmpTypePName + " " + list.size());
                                if ((salesPerson != null && empTypePNameLower.equals(salesPerson.toLowerCase())) ||
                                        (collectionPerson != null && empTypePNameLower.equals(collectionPerson.toLowerCase()))) {
                                    filterList.add(list.get(i));
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

//                    Set<String> BpNames = new HashSet<>();
//                    for (int a = 0; a < INOUTList.size(); a++) {
//                        if (!BpNames.contains(INOUTList.get(a).getBPName())) {
//                            BpNames.add(INOUTList.get(a).getBPName());
//
//                        }
//                    }
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

                    BackupList = finalList2;
                    FList = finalList2;
                    UpdateList();

                }

                AppUtil.hideProgressDialog();
            }
        });

    }


    private void UpdateList() {

        double Tamt = 0;
        for(int i = 0;i<FList.size();i++){
            Tamt += FList.get(i).getTotal();
        }
        String TotalAmt = String.format("%.2f", Tamt);
         binding.txtTotalAmt.setText(TotalAmt);

         gson = new Gson();
        String json = gson.toJson(FList);

        System.out.println("Json list in out pay List:"+json);
        adapter.setData(FList,getApplicationContext(),TYPE,PostFromDate,PostToDate,ViewFromDate,ViewToDate,Flag);
    }


}