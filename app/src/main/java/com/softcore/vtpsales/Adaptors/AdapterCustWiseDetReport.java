package com.softcore.vtpsales.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softcore.vtpsales.ArInvoiceView;
import com.softcore.vtpsales.Model.CusReportWiseDetModel;
import com.softcore.vtpsales.PdfViewerActivity;
import com.softcore.vtpsales.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class AdapterCustWiseDetReport extends RecyclerView.Adapter<AdapterCustWiseDetReport.UserViewHolder> {
    private List<CusReportWiseDetModel> modelList;
    Context context;
    String TYPE;
    String SortBy;


    public AdapterCustWiseDetReport() {
        this.modelList = new ArrayList<>();

    }

    public void setData(List<CusReportWiseDetModel> modelList, Context context, String TYPE, String sortBy) {
        this.modelList = modelList;
        this.context = context;
        this.TYPE = TYPE;
        this.SortBy = sortBy;
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


//        double Tamt = 0;
//        switch (SortBy) {
//            case "Net Amount INV+CRN":
//                if(TYPE.equals("Sales")){
//                    Tamt += Double.parseDouble(model.getNetAmtINV_CRN());
//                }
////                else if(TYPE.equals("Customer Outstanding")){
////                    Tamt += Double.parseDouble(model.getNetAmtINV_ARCRN());
////                }
//
//                break;
//            case "Gross Amount INV+CRN":
//                if(TYPE.equals("Sales")){
//                    Tamt += Double.parseDouble(model.getGrossAmtINV_CRN());
//                }
////                else if(TYPE.equals("Customer Outstanding")){
////                    Tamt += Double.parseDouble(model.getGrossAmtINV_ARCRN());
////                }
//                break;
//            case "Net sales Amount":
//                System.out.println("Net Sales Amount: "+model.getNetSalesAmt());
//                Tamt += Double.parseDouble(model.getNetSalesAmt());
//                break;
//            case "Gross sales Amount":
//
//                Tamt += Double.parseDouble(model.getGrossSalesAmt());
//                break;
//            case "Net Credit Amount":
//                Tamt += Double.parseDouble(model.getNetCrdAmt());
//                break;
//            case "Gross Credit Amount":
//                if(TYPE.equals("Sales")){
//                    Tamt += Double.parseDouble(model.getGrossCrditAmt());
//                }
////                else if(TYPE.equals("Customer Outstanding")){
////                    Tamt += Double.parseDouble(model.getGrossCrdAmt());
////                }
//                break;
//        }
        double Tamt = 0;
        switch (SortBy) {
            case "Net":
                if(TYPE.equals("Sales")){
                    Tamt += Double.parseDouble(model.getNetAmtINV_CRN());
                }
//                else if(TYPE.equals("Customer Outstanding")){
//                    Tamt += Double.parseDouble(model.getNetAmtINV_ARCRN());
//                }
                else if(TYPE.equals("Purchase")|| TYPE.equals("Vendor Outstanding")){
                    Tamt += Double.parseDouble(model.getNetAmtApCrn());
                }

                break;
            case "Gross":
                if(TYPE.equals("Sales")){
                    Tamt += Double.parseDouble(model.getGrossAmtINV_CRN());
                }
//                else if(TYPE.equals("Customer Outstanding")){
//                    Tamt += Double.parseDouble(model.getGrossAmtINV_ARCRN());
//                }
                else if(TYPE.equals("Purchase")|| TYPE.equals("Vendor Outstanding")){
                    Tamt += Double.parseDouble(model.getGrossAmtApCrn());
                }
                break;
        }
        DecimalFormat df = new DecimalFormat("0.00");
        String formattedTamt = df.format(Tamt);

        holder.TxtAmount.setText("₹ "+formattedTamt);
        holder.TxtDate.setText(model.getPostingDate());
        if(model.getDocNo() != null && model.getDocEntry() != null){
            holder.TxtDocno.setText(model.getDocNo());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override 
                public void onClick(View v) {

                    Intent intent = new Intent(context, ArInvoiceView.class);
                    intent.putExtra("DocNo", model.getDocNo());
                    intent.putExtra("DocEntry", model.getDocEntry());
                    intent.putExtra("CusName", model.getCustomerName());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });
        }





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
