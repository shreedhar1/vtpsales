package com.softcore.vtpsales.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softcore.vtpsales.IncomingOutgoingDetList;
import com.softcore.vtpsales.Model.InOutPayModel;
import com.softcore.vtpsales.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class AdapterInOutPayReport extends RecyclerView.Adapter<AdapterInOutPayReport.UserViewHolder> {
    private List<InOutPayModel> modelList;
    Context context;
    String TYPE;
    String PostFromDate;
    String PostToDate;
    String ViewFromDate;
    String ViewToDate;
    String Flag;




    public AdapterInOutPayReport() {
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
    public AdapterInOutPayReport.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adaptor_report_list, parent, false);
        return new AdapterInOutPayReport.UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterInOutPayReport.UserViewHolder holder, int position) {
        InOutPayModel model = modelList.get(position);

     //  int Tamt += Double.parseDouble(model.getGrossAmtApCrn());

            Double Tamt = model.getTotal();
            DecimalFormat df = new DecimalFormat("0.00");
            String formattedTamt = df.format(Tamt);


        holder.TxtCusName.setText(model.getBPName());
        holder.TxtAmount.setText("₹ "+formattedTamt);
//        holder.TxtDate.setText(model.getPostingDate());
//        holder.TxtVTPDocno.setText(model.getVTP_Doc_No());
//        holder.TxtDocno.setText(String.valueOf(model.getSAP_Doc_No()));





            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

              Intent intent = new Intent(context, IncomingOutgoingDetList.class);
                 //       }
                    intent.putExtra("TYPE", TYPE);
                    intent.putExtra("FromDatePost",PostFromDate);
                    intent.putExtra("ToDatePost",PostToDate);
                    intent.putExtra("ViewToDate",ViewToDate);
                    intent.putExtra("ViewFromDate",ViewFromDate);
                    intent.putExtra("SlpName","");
                    intent.putExtra("Flag",Flag);
                    intent.putExtra("TotalAmt",holder.TxtAmount.getText().toString());
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
        private TextView TxtAmount;
//        private TextView TxtDate;
//        private TextView TxtDocno;
//        private TextView TxtVTPDocno;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            TxtCusName = itemView.findViewById(R.id.txt_cusName);
            TxtAmount = itemView.findViewById(R.id.txt_cusAmt);
//            TxtDate  = itemView.findViewById(R.id.txt_date);
//            TxtDocno  = itemView.findViewById(R.id.txt_Docno);
//            TxtVTPDocno  = itemView.findViewById(R.id.txt_VTPDocno);
        }
    }
}
