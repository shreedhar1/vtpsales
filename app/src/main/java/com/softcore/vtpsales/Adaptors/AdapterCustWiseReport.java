package com.softcore.vtpsales.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softcore.vtpsales.Model.CusReportWiseModel;
import com.softcore.vtpsales.PR_ReportsDetListActivity;
import com.softcore.vtpsales.R;
import com.softcore.vtpsales.ReportsDetListActivity;

import java.text.DecimalFormat;
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
    String Sortby;

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
                        String TYPE,
                        String sortBy) {
        this.modelList = modelList;
        this.context = context;
        this.ViewFromDate = ViewFromDate;
        this.ViewToDate = ViewToDate;
        this.PostFromDate = PostFromDate;
        this.PostToDate = PostToDate;
        this.SlpName = SlpName;
        this.TotalAmt = TotalAmt;
        this.TYPE = TYPE;
        this.Sortby = sortBy;
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
                    if((model.getVendorName() == null || model.getVendorName().equals(""))){
                        holder.TxtCusName.setText("-");
                    }else{
                        holder.TxtCusName.setText(model.getVendorName());
                    }
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


        double Tamt = 0;
        switch (Sortby) {
            case "Net":
                if(TYPE.equals("Sales")){
                    Tamt += Double.parseDouble(model.getNetAmtINV_CRN());
                }
                else if(TYPE.equals("Customer Outstanding")){
                    Tamt += Double.parseDouble(model.getNetAmtINV_ARCRN());
                }
                else if(TYPE.equals("Purchase")|| TYPE.equals("Vendor Outstanding")){
                    Tamt += Double.parseDouble(model.getNetAmtApCrn());
                }
                else if(TYPE.equals("Purchase Register")){
                    Tamt += Double.parseDouble(model.getNetAmt());
                }
                break;
            case "Gross":
                if(TYPE.equals("Sales")){
                    Tamt += Double.parseDouble(model.getGrossAmtINV_CRN());
                }
                else if(TYPE.equals("Customer Outstanding")){
                    Tamt += Double.parseDouble(model.getGrossAmtINV_ARCRN());
                }
                else if(TYPE.equals("Purchase")|| TYPE.equals("Vendor Outstanding")){
                    Tamt += Double.parseDouble(model.getGrossAmtApCrn());
                }
                else if(TYPE.equals("Purchase Register")){
                    Tamt += Double.parseDouble(model.getGrossAmt());
                }
                break;
        }
        System.out.println("TotalAmt"+TotalAmt);
        DecimalFormat df = new DecimalFormat("0.00");
        String formattedTamt = df.format(Tamt);


        holder.TxtAmount.setText("₹ "+formattedTamt);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                String Flag="";
                if(TYPE.equals("Sales")){
                    Flag = "Sales_Customer_Wise_DetailReport";
                     intent = new Intent(context, ReportsDetListActivity.class);
                }else if(TYPE.equals("Purchase")){
                    Flag = "Purchase_Vendor_Wise_DetailReport";
                     intent = new Intent(context, ReportsDetListActivity.class);
                }
                else if(TYPE.equals("Customer Outstanding")){
                    Flag = "Customer_Outstanding_Report";
                     intent = new Intent(context, ReportsDetListActivity.class);
                }
                else if(TYPE.equals("Vendor Outstanding")){
                    Flag = "Vendor_Outstanding_Report";
                     intent = new Intent(context, ReportsDetListActivity.class);
                }
                else if(TYPE.equals("Purchase Register")){
                    Flag = "Purchase_Register_Report";
                     intent = new Intent(context, PR_ReportsDetListActivity.class);
                }else{
                    return;
                }

                intent.putExtra("TYPE", TYPE);
                intent.putExtra("FromDatePost",PostFromDate);
                intent.putExtra("ToDatePost",PostToDate);
                intent.putExtra("ViewToDate",ViewToDate);
                intent.putExtra("ViewFromDate",ViewFromDate);
                intent.putExtra("SlpName",SlpName);
                intent.putExtra("Flag",Flag);
                intent.putExtra("CusName",holder.TxtCusName.getText());
                //   intent.putExtra("Amount",holder.TxtAmount);
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
