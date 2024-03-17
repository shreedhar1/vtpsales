package com.softcore.vtpsales;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.softcore.vtpsales.Adaptors.AdapterCustWiseDetReport;
import com.softcore.vtpsales.Adaptors.AdapterCustWiseReport;
import com.softcore.vtpsales.AppUtils.AppUtil;
import com.softcore.vtpsales.Model.BirthdayModel;
import com.softcore.vtpsales.Model.CommanResorce;
import com.softcore.vtpsales.Model.CusReportWiseDetModel;
import com.softcore.vtpsales.Model.CusReportWiseModel;
import com.softcore.vtpsales.ViewModel.CusWiseDetReportViewModel;
import com.softcore.vtpsales.ViewModel.CusWiseReportViewModel;
import com.softcore.vtpsales.databinding.ActivityReportsDetListBinding;
import com.softcore.vtpsales.databinding.ActivityReportsListBinding;

import java.util.ArrayList;
import java.util.List;

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

        binding.txtSlPName.setText(SlpName);

        binding.txtTotalAmt.setText("₹ "+TotalAmt);

        binding.txtCustName.setText(CusName);

        GetCusWiseReportList(PostFromDate,PostToDate,SlpName,Flag);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterCustWiseDetReport();
        binding.recyclerView.setAdapter(adapter);

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
                   // adapter.setData(listCommanResorce.data,getApplicationContext());
                }

                AppUtil.hideProgressDialog();
            }
        });


    }
}