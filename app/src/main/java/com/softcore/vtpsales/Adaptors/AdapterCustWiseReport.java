package com.softcore.vtpsales.Adaptors;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softcore.vtpsales.Model.CusReportWiseModel;
import com.softcore.vtpsales.R;
import com.softcore.vtpsales.ReportsActivity;
import com.softcore.vtpsales.ReportsDetListActivity;
import com.softcore.vtpsales.ReportsListActivity;

import java.util.ArrayList;
import java.util.List;


public class AdapterCustWiseReport extends RecyclerView.Adapter<AdapterCustWiseReport.UserViewHolder> {
    private List<CusReportWiseModel> modelList;
    Context context;
    String ViewFromDate;
    String ViewToDate;
    String PostFromDate;
    String PostToDate;
    String SlpName;

    String TotalAmt;
    String TYPE;

    public AdapterCustWiseReport() {
        this.modelList = new ArrayList<>();
    }

    public void setData(List<CusReportWiseModel> modelList,
    Context context,
    String ViewFromDate,
    String ViewToDate,
    String PostFromDate,
    String PostToDate,
    String SlpName,
    String TotalAmt,
    String TYPE) {
        this.modelList = modelList;
        this.context = context;
        this.ViewFromDate = ViewFromDate;
        this.ViewToDate = ViewToDate;
        this.PostFromDate = PostFromDate;
        this.PostToDate = PostToDate;
        this.SlpName = SlpName;
        this.TotalAmt = TotalAmt;
        this.TYPE = TYPE;
        System.out.println("modelList  Length"+modelList.size());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterCustWiseReport.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adaptor_report_list, parent, false);
        return new AdapterCustWiseReport.UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCustWiseReport.UserViewHolder holder, int position) {
        CusReportWiseModel model = modelList.get(position);

         if(model.getCustomerName() == null || model.getCustomerName().equals("")){

             if((model.getCustomer_Name() == null || model.getCustomer_Name().equals(""))){
                 if((model.getVendor_Name() == null || model.getVendor_Name().equals(""))){
                     holder.TxtCusName.setText("-");
                 }else{
                     holder.TxtCusName.setText(model.getVendor_Name());
                 }
             }else{
                 holder.TxtCusName.setText(model.getCustomer_Name());
             }
         }
         else {
             holder.TxtCusName.setText(model.getCustomerName());
         }
        holder.TxtAmount.setText("₹ "+model.getBalanceDue());





         holder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 System.out.println("hello: "+model.getCustomerName()+model.getCount());

                 String Flag="";
                 if(TYPE.equals("Sales")){
                     Flag = "Sales_Customer_Wise_DetailReport";
                 }else if(TYPE.equals("Purchase")){
                     Flag = "Purchase_Vendor_Wise_DetailReport";
                 }
                 Intent intent = new Intent(context, ReportsDetListActivity.class);
                 intent.putExtra("TYPE", TYPE);
                 intent.putExtra("FromDatePost",PostFromDate);
                 intent.putExtra("ToDatePost",PostToDate);
                 intent.putExtra("ViewToDate",ViewToDate);
                 intent.putExtra("ViewFromDate",ViewFromDate);
                 intent.putExtra("SlpName",SlpName);
                 intent.putExtra("Flag",Flag);
                 intent.putExtra("CusName",holder.TxtCusName.getText());
                 intent.putExtra("Amount",model.getBalanceDue());
                 intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 context.startActivity(intent);
             }
         });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView TxtCusName;
        private TextView TxtAmount;


        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            TxtCusName = itemView.findViewById(R.id.txt_cusName);
            TxtAmount = itemView.findViewById(R.id.txt_cusAmt);
        }
    }
}
