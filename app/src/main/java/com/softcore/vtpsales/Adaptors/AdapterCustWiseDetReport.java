package com.softcore.vtpsales.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softcore.vtpsales.Model.CusReportWiseDetModel;
import com.softcore.vtpsales.Model.CusReportWiseModel;
import com.softcore.vtpsales.R;
import com.softcore.vtpsales.ReportsDetListActivity;

import java.util.ArrayList;
import java.util.List;


public class AdapterCustWiseDetReport extends RecyclerView.Adapter<AdapterCustWiseDetReport.UserViewHolder> {
    private List<CusReportWiseDetModel> modelList;
    Context context;


    public AdapterCustWiseDetReport() {
        this.modelList = new ArrayList<>();

    }

    public void setData(List<CusReportWiseDetModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
        System.out.println("modelList  Length"+modelList.size());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterCustWiseDetReport.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adaptor_report_det_list, parent, false);
        return new AdapterCustWiseDetReport.UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCustWiseDetReport.UserViewHolder holder, int position) {
        CusReportWiseDetModel model = modelList.get(position);

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
        holder.TxtDate.setText(model.getPostingDate());
        holder.TxtDocno.setText(model.getDocNo());


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView TxtCusName;
        private TextView TxtAmount;
        private TextView TxtDate;
        private TextView TxtDocno;


        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            TxtCusName = itemView.findViewById(R.id.txt_cusName);
            TxtAmount = itemView.findViewById(R.id.txt_cusAmt);
            TxtDate  = itemView.findViewById(R.id.txt_date);
            TxtDocno  = itemView.findViewById(R.id.txt_Docno);
        }
    }
}
