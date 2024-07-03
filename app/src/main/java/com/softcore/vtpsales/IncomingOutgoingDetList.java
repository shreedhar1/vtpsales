package com.softcore.vtpsales;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.softcore.vtpsales.Adaptors.AdapterInOutPayDetReport1;
import com.softcore.vtpsales.AppUtils.AppUtil;
import com.softcore.vtpsales.Model.CommanResorce;
import com.softcore.vtpsales.Model.InOutPayModel;
import com.softcore.vtpsales.ViewModel.InOutPayReportViewModel;
import com.softcore.vtpsales.databinding.ActivityInOutPaymentListBinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IncomingOutgoingDetList extends AppCompatActivity {
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

    AdapterInOutPayDetReport1 adapter;
    String EmpTypePName;
    String EmpType;
    List<InOutPayModel> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= ActivityInOutPaymentListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EmpType = AppUtil.getStringData(getApplicationContext(),"EmpType","");
        EmpTypePName = AppUtil.getStringData(getApplicationContext(),"EmpTypePName","");


        ViewFromDate=getIntent().getStringExtra("ViewFromDate");
        ViewToDate=getIntent().getStringExtra("ViewToDate");
        PostFromDate=getIntent().getStringExtra("FromDatePost");
        PostToDate=getIntent().getStringExtra("ToDatePost");
        SlpName=getIntent().getStringExtra("SlpName");
        Flag=getIntent().getStringExtra("Flag");
        System.out.println("Flag ::"+Flag);
        TotalAmt=getIntent().getStringExtra("TotalAmt");
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

        // binding.txtTotalAmt.setText(String.format("%.2f", Double.parseDouble(TotalAmt)));



       // binding.txtCustName.setText(CusName);
        System.out.println("SplName From Reports det screen: "+SlpName);

        GetCusWiseReportList(PostFromDate,PostToDate,SlpName,Flag);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterInOutPayDetReport1();
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


    private void GetCusWiseReportList (String FromDate,String ToDate,String SlpName,String Flag){
        list = new ArrayList<>();
        AppUtil.showProgressDialog(binding.getRoot(),"Loading");
        String DbName = AppUtil.getStringData(getApplicationContext(),"DatabaseName","");

        System.out.println("Get CusWise List : "+FromDate+" "+ToDate+" "+ DbName+" "+ Flag);
        InOutPayReportViewModel viewModel= new ViewModelProvider(this).get(InOutPayReportViewModel.class);
        viewModel.getInOutDetails(FromDate,ToDate, SlpName, DbName, Flag).observe(this, new Observer<CommanResorce<List<InOutPayModel>>>() {
            @Override
            public void onChanged(CommanResorce<List<InOutPayModel>> listCommanResorce) {

                if (listCommanResorce.data != null && !listCommanResorce.data.isEmpty()) {

//                    list = new ArrayList<>();
//                    for (InOutPayModel model : listCommanResorce.data) {
//                        if (CusName.equalsIgnoreCase(model.getBPName())) {
//                            list.add(model);
//                        }
//                    }

                    list = new ArrayList<>();
                    for (InOutPayModel model : listCommanResorce.data) {
                        if (CusName.equalsIgnoreCase(model.getBPName())) {
                            list.add(model);
                        }
                    }



                    List<InOutPayModel> filterList = new ArrayList<>();


                    Gson gson = new Gson();
                    String json = gson.toJson(list);

                    System.out.println("Json list in out pay List:"+json);



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


                    UpdateList(INOUTList);

                }

                AppUtil.hideProgressDialog();
            }
        });

    }


    private void UpdateList(List<InOutPayModel> list) {


        adapter.setData(list,getApplicationContext(),TYPE,PostFromDate,PostToDate,ViewFromDate,ViewToDate,Flag);

    }


}