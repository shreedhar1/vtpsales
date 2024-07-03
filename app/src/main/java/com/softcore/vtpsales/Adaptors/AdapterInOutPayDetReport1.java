package com.softcore.vtpsales.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softcore.vtpsales.IncomingOutgoingInvoice;
import com.softcore.vtpsales.Model.InOutPayModel;
import com.softcore.vtpsales.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class AdapterInOutPayDetReport1 extends RecyclerView.Adapter<AdapterInOutPayDetReport1.UserViewHolder> {
    private List<InOutPayModel> modelList;
    Context context;
    String TYPE;
    String PostFromDate;
    String PostToDate;
    String ViewFromDate;
    String ViewToDate;
    String Flag;




    public AdapterInOutPayDetReport1() {
        this.modelList = new ArrayList<>();

    }

    public void setData(List<InOutPayModel> modelList, Context context, String TYPE,String PostFromDate,String PostToDate,String ViewFromDate,String ViewToDate,String Flag) {
        this.modelList = modelList;
        this.context = context;
        this.TYPE = TYPE;
        this.PostFromDate = PostFromDate;
        this.PostToDate = PostToDate;
        this.ViewFromDate = ViewFromDate;
        this.ViewToDate = ViewToDate;
        this.Flag = Flag;

        System.out.println("modelList  Length"+modelList.size());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterInOutPayDetReport1.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adaptor_in_out_payment_list, parent, false);
        return new AdapterInOutPayDetReport1.UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterInOutPayDetReport1.UserViewHolder holder, int position) {
        InOutPayModel model = modelList.get(position);


     //  int Tamt += Double.parseDouble(model.getGrossAmtApCrn());





            Double Tamt = model.getTotal();
            DecimalFormat df = new DecimalFormat("0.00");
            String formattedTamt = df.format(Tamt); 

//            if(formattedTamt.equals("0.00")){
//                holder.TxtTotalAmt.setText("₹ "+df.format(model.getPayment_On_Account()));
//            }else{
//                holder.TxtTotalAmt.setText("₹ "+df.format(model.getAmount()));
//
//            }
        holder.TxtTotalAmt.setText(formattedTamt);
        holder.TxtCusName.setText(model.getBPName());
        holder.TxtVTPDocno.setText(model.getVTP_Doc_No());
        holder.TxtDocno.setText(String.valueOf(model.getSAP_Doc_No()));
        holder.TxtDate.setText(String.valueOf(model.getPostingDate()));

//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    Intent intent = new Intent(context, InOutInvoiceView.class);
//                    intent.putExtra("DocNo", model.getSAP_Doc_No());
//                    intent.putExtra("VTPDocNo", model.getVTPDocNo());
//                    intent.putExtra("VTP_Doc_No", model.getVTP_Doc_No());
//                    intent.putExtra("DocEntry", model.getSAP_Doc_No());
//                    intent.putExtra("CusName", model.getBPName());
//                    intent.putExtra("PostingDate", model.getPostingDate());
//                    intent.putExtra("Reference", model.getReference());
//                    intent.putExtra("Payment_On_Account", model.getPayment_On_Account());
//                    intent.putExtra("Inv_Doc_No", model.getInv_Doc_No());
//                    intent.putExtra("Amount", model.getAmount());
//                    intent.putExtra("Total", model.getTotal());
//                    intent.putExtra("Cheque_No", model.getChequeNo());
//                    intent.putExtra("Cheque_Date", model.getChequeDate());
//                    intent.putExtra("Mode_Of_Payment", model.getModeOfPayment());
//                    intent.putExtra("Remarks", model.getRemarks());
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(intent);
//                }
//
//            });

holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, IncomingOutgoingInvoice.class);
        //       }
        intent.putExtra("TYPE", TYPE);
        intent.putExtra("FromDatePost",PostFromDate);
        intent.putExtra("ToDatePost",PostToDate);
        intent.putExtra("ViewToDate",ViewToDate);
        intent.putExtra("ViewFromDate",ViewFromDate);
        intent.putExtra("SlpName","");
        intent.putExtra("Flag",Flag);
        intent.putExtra("TotalAmt",formattedTamt);
        intent.putExtra("HeaderDocNo",String.valueOf(model.getSAP_Doc_No()));

        System.out.println("Flag ::1"+Flag);


        intent.putExtra("CusName", model.getBPName());
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
        private TextView TxtDate;
        private TextView TxtDocno;
        private TextView TxtVTPDocno;
        private TextView TxtTotalAmt;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            TxtCusName = itemView.findViewById(R.id.txt_BPName);
            TxtTotalAmt = itemView.findViewById(R.id.txtTotalAmt);
            TxtDate  = itemView.findViewById(R.id.txt_PostingDate);
            TxtDocno  = itemView.findViewById(R.id.txt_Docno);
            TxtVTPDocno  = itemView.findViewById(R.id.txt_VTPDocno);
        }
    }
}
