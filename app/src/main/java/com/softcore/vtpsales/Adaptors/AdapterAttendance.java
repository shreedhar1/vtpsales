package com.softcore.vtpsales.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.softcore.vtpsales.AppUtils.AppUtil;
import com.softcore.vtpsales.MapScreen;
import com.softcore.vtpsales.Model.AttendanceModel;
import com.softcore.vtpsales.R;

import java.util.ArrayList;
import java.util.List;


public class AdapterAttendance extends RecyclerView.Adapter<AdapterAttendance.UserViewHolder> {
    private List<AttendanceModel> userList;
    Context context;
    String empName;

    public AdapterAttendance() {
        this.userList = new ArrayList<>();

    }

    public void setData(List<AttendanceModel> userList, Context context, String empName) {
        this.userList = userList;
        this.context = context;
        this.empName = empName;
        Gson gson = new Gson();
        String json2 = gson.toJson(userList);
        System.out.println("userList:"+json2);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterAttendance.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_attendence_list, parent, false);
        return new AdapterAttendance.UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAttendance.UserViewHolder holder, int position) {


        AttendanceModel user = userList.get(position);

        String date = "";
        String CheckInTime = "";
        String CheckOutTime = "";

        if( user.getDate() != null && !user.getDate().equals("")){
             date = ", "+AppUtil.convertDateFormat(user.getDate());
        }

        if( user.getCheckIn() != null && !user.getCheckIn().equals("") && !user.getCheckIn().equals("0000")){
            String intime = AppUtil.convertTo12HourFormat(user.getCheckIn());
            CheckInTime = intime+date;
        }
        if( user.getCheckOut() != null && !user.getCheckOut().equals("")&&!user.getCheckOut().equals("0000")&&!user.getCheckOut().equals("07.10")){
            String outtime = AppUtil.convertTo12HourFormat(user.getCheckOut());
            CheckOutTime = outtime+date;
        }

        holder.TxtInTime.setText(CheckInTime);
        holder.TxtOutTime.setText(CheckOutTime);

        holder.TxtInAddress.setText(user.getLocationIn());
        holder.TxtOutAddress.setText(user.getLocationOut());

//        if(user.getCheckIn().equals("0000")){
//            holder.lay_InTime.setVisibility(View.GONE);
//        }
//        if(user.getCheckOut().equals("0000")){
//            holder.lay_OutTime.setVisibility(View.GONE);
//        }

        if (user.getCheckIn().equals("0000")) {
            holder.lay_InTime.setVisibility(View.GONE);
        } else {
            holder.lay_InTime.setVisibility(View.VISIBLE);
        }

        if (user.getCheckOut().equals("0000")) {
            holder.lay_OutTime.setVisibility(View.GONE);
        } else {
            holder.lay_OutTime.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, MapScreen.class);
//                context.startActivity(intent);

                Intent intent = new Intent(context, MapScreen.class);
                intent.putExtra("EmpName",empName);
                intent.putExtra("InLocation", user.getLocationIn());
                intent.putExtra("OutLocation",user.getLocationOut());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

//                Intent intent = new Intent(context, AllMapScreen.class);
//                intent.putExtra("locationsList", (Serializable) user);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView TxtInTime;
        private TextView TxtInAddress;
        private TextView TxtOutTime;
        private TextView TxtOutAddress;
        private LinearLayout lay_InTime;
        private LinearLayout lay_OutTime;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            TxtInTime = itemView.findViewById(R.id.txtInTime);
            TxtInAddress = itemView.findViewById(R.id.txt_InAddress);
            TxtOutTime = itemView.findViewById(R.id.txtOutTime);
            TxtOutAddress = itemView.findViewById(R.id.txt_OutAddress);
            lay_InTime = itemView.findViewById(R.id.lay_InTime);
            lay_OutTime = itemView.findViewById(R.id.lay_OutTime);
        }
    }
}
