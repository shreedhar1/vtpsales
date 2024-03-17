package com.softcore.vtpsales;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.softcore.vtpsales.Adaptors.AdapterCustWiseReport;
import com.softcore.vtpsales.AppUtils.AppUtil;
import com.softcore.vtpsales.Model.CommanResorce;
import com.softcore.vtpsales.Model.CusReportWiseModel;
import com.softcore.vtpsales.ViewModel.CusWiseReportViewModel;
import com.softcore.vtpsales.databinding.ActivityReportsListBinding;

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
    String TotalAmt;

    AdapterCustWiseReport adapter;


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

        binding.txtSlPName.setText(SlpName);

        binding.txtTotalAmt.setText(TotalAmt);

        GetCusWiseReportList(PostFromDate,PostToDate,SlpName,Flag);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterCustWiseReport();
        binding.recyclerView.setAdapter(adapter);

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

                    adapter.setData(listCommanResorce.data,getApplicationContext(),
                     ViewFromDate,
                     ViewToDate,
                     PostFromDate,
                     PostToDate,
                     SlpName,
                     TotalAmt,
                     TYPE);
                }

                AppUtil.hideProgressDialog();
            }
        });


    }
}