package com.softcore.vtpsales.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softcore.vtpsales.Model.InOutPayModel;
import com.softcore.vtpsales.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class AdapterInOutPayBillsReport extends RecyclerView.Adapter<AdapterInOutPayBillsReport.UserViewHolder> {
    private List<InOutPayModel> modelList;
    Context context;
    String TYPE;



    public AdapterInOutPayBillsReport() {
        this.modelList = new ArrayList<>();

    }

    public void setData(List<InOutPayModel> modelList, Context context, String TYPE) {
        this.modelList = modelList;
        this.context = context;
        this.TYPE = TYPE;

        System.out.println("modelList  Length"+modelList.size());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterInOutPayBillsReport.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adaptor_in_out_payment_bills_list, parent, false);
        return new AdapterInOutPayBillsReport.UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterInOutPayBillsReport.UserViewHolder holder, int position) {
        InOutPayModel model = modelList.get(position);


     //  int Tamt += Double.parseDouble(model.getGrossAmtApCrn());





            Double Tamt = model.getAmount();
            DecimalFormat df = new DecimalFormat("0.00");
            String formattedTamt = df.format(Tamt); 

            if(formattedTamt.equals("0.00")){
                holder.TxtTotalAmt.setText("₹ "+df.format(model.getPayment_On_Account()));
            }else{
                holder.TxtTotalAmt.setText("₹ "+df.format(model.getAmount()));

            }


//        holder.TxtCusName.setText(model.getBPName());
//        holder.TxtAmount.setText("Amount: ₹ "+formattedTamt);
//        holder.TxtPOA.setText("Payment on Account: ₹ "+df.format(model.getPayment_On_Account()));


//        holder.TxtDate.setText(model.getPostingDate());
        holder.TxtVTPDocno.setText(model.getVTPDocNo());
        holder.TxtDocno.setText(String.valueOf(model.getSAP_Doc_No()));


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
        }



    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
//        private TextView TxtCusName;
//        private TextView TxtAmount;
//        private TextView TxtPOA;
//        private TextView TxtDate;
        private TextView TxtDocno;
        private TextView TxtVTPDocno;
        private TextView TxtTotalAmt;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
//            TxtCusName = itemView.findViewById(R.id.txt_cusName);
//            TxtAmount = itemView.findViewById(R.id.txt_cusAmt);
        //    TxtPOA = itemView.findViewById(R.id.txtPayment_On_Account);
            TxtTotalAmt = itemView.findViewById(R.id.txtTotalAmt);
          //  TxtDate  = itemView.findViewById(R.id.txt_date);
            TxtDocno  = itemView.findViewById(R.id.txt_Docno);
            TxtVTPDocno  = itemView.findViewById(R.id.txt_VTPDocno);
        }
    }
}
