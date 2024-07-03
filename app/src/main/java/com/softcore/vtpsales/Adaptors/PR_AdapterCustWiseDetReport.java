package com.softcore.vtpsales.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softcore.vtpsales.ApInvoiceView;
import com.softcore.vtpsales.Ap_Credit_Note_InvoiceView;
import com.softcore.vtpsales.ArInvoiceView;
import com.softcore.vtpsales.Ar_Credit_Note_InvoiceView;
import com.softcore.vtpsales.Model.CusReportWiseModel;
import com.softcore.vtpsales.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class PR_AdapterCustWiseDetReport extends RecyclerView.Adapter<PR_AdapterCustWiseDetReport.UserViewHolder> {
    private List<CusReportWiseModel> modelList;
    Context context;
    String TYPE;
    String SortBy;


    public PR_AdapterCustWiseDetReport() {
        this.modelList = new ArrayList<>();

    }

    public void setData(List<CusReportWiseModel> modelList, Context context, String TYPE, String sortBy) {
        this.modelList = modelList;
        this.context = context;
        this.TYPE = TYPE;
        this.SortBy = sortBy;
        System.out.println("modelList  Length"+modelList.size());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PR_AdapterCustWiseDetReport.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adaptor_report_det_list, parent, false);
        return new PR_AdapterCustWiseDetReport.UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PR_AdapterCustWiseDetReport.UserViewHolder holder, int position) {
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
                else if(TYPE.equals("Customer Outstanding")|| TYPE.equals("Vendor Outstanding")){
                    Tamt += Double.parseDouble(model.getNetAmtINV_ARCRN());
                }
                else if(TYPE.equals("Purchase")){
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
                else if(TYPE.equals("Customer Outstanding")|| TYPE.equals("Vendor Outstanding")){
                    Tamt += Double.parseDouble(model.getGrossAmtINV_ARCRN());
                }
                else if(TYPE.equals("Purchase")){
                    Tamt += Double.parseDouble(model.getGrossAmtApCrn());
                }
                else if(TYPE.equals("Purchase Register")){
                    Tamt += Double.parseDouble(model.getGrossAmt());
                }
                break;
        }
        DecimalFormat df = new DecimalFormat("0.00");
        String formattedTamt = df.format(Tamt);

        holder.TxtAmount.setText("₹ "+formattedTamt);
        holder.TxtDate.setText(model.getPostingDate());
        holder.TxtVTPDocno.setText(model.getVTP_Doc_No());


        String DocNo = "";



        if(model.getDocNum() == null){
                    DocNo="";
                }else {
                    DocNo = model.getDocNum();
                    //  System.out.println("DocNo "+model.getDocNum());
                }

            holder.TxtDocno.setText(DocNo);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override 
                public void onClick(View v) {

                    String DocNo = "";

                    if(model.getDocNum() == null){
                        DocNo="";
                    }else {
                        DocNo = model.getDocNum();
                        //  System.out.println("DocNo "+model.getDocNum());
                    }


                    if(!DocNo.equals("") && model.getDocEntry() != null) {
                        String dn = DocNo;
                        System.out.println("DocNum " + dn + " " + TYPE);
                        Intent intent;

//                        if (TYPE.equals("Vendor Outstanding")) {
//                            intent = new Intent(context, Ar_Credit_Note_InvoiceView.class);
//                        } else {
//                            intent = new Intent(context, ArInvoiceView.class);
                 //       }


                        if(model.getRef_Document().equals("Credit Note") || model.getRef_Document().equals("AR Credit Note") ){
                            intent = new Intent(context, Ar_Credit_Note_InvoiceView.class);
                            intent.putExtra("DocNo", dn);
                            intent.putExtra("VTPDocNo", model.getVTP_Doc_No());
                            intent.putExtra("DocEntry", model.getDocEntry());
                            intent.putExtra("CusName", model.getCustomerName());
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                        else if(model.getRef_Document().equals("AR Invoice") || model.getRef_Document().equals("AR Inv")){
                            intent = new Intent(context, ArInvoiceView.class);
                            intent.putExtra("DocNo", dn);
                            intent.putExtra("VTPDocNo", model.getVTP_Doc_No());
                            intent.putExtra("DocEntry", model.getDocEntry());
                            intent.putExtra("CusName", model.getCustomerName());
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                        else if(model.getRef_Document().equals("AP Invoice")){
                            intent = new Intent(context, ApInvoiceView.class);
                            intent.putExtra("DocNo", dn);
                            intent.putExtra("VTPDocNo", model.getVTP_Doc_No());
                            intent.putExtra("DocEntry", model.getDocEntry());
                            intent.putExtra("CusName", model.getCustomerName());
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                        else if(model.getRef_Document().equals("AP Credit Note")){
                            intent = new Intent(context, Ap_Credit_Note_InvoiceView.class);
                            intent.putExtra("DocNo", dn);
                            intent.putExtra("VTPDocNo", model.getVTP_Doc_No());
                            intent.putExtra("DocEntry", model.getDocEntry());
                            intent.putExtra("CusName", model.getCustomerName());
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }


                    }
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
        private TextView TxtDate;
        private TextView TxtDocno;
        private TextView TxtVTPDocno;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            TxtCusName = itemView.findViewById(R.id.txt_cusName);
            TxtAmount = itemView.findViewById(R.id.txt_cusAmt);
            TxtDate  = itemView.findViewById(R.id.txt_date);
            TxtDocno  = itemView.findViewById(R.id.txt_Docno);
            TxtVTPDocno  = itemView.findViewById(R.id.txt_VTPDocno);
        }
    }
}
