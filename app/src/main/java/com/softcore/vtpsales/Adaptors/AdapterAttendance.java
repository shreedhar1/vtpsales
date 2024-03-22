package com.softcore.vtpsales.Adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softcore.vtpsales.AppUtils.AppUtil;
import com.softcore.vtpsales.Model.AttendanceModel;
import com.softcore.vtpsales.R;

import java.util.ArrayList;
import java.util.List;


public class AdapterAttendance extends RecyclerView.Adapter<AdapterAttendance.UserViewHolder> {
    private List<AttendanceModel> userList;

    public AdapterAttendance() {
        this.userList = new ArrayList<>();
    }

    public void setData(List<AttendanceModel> userList) {
        this.userList = userList;
        System.out.println("Attendance List Length"+userList.size());
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

        if( user.getCheckIn() != null && !user.getCheckIn().equals("")){
            String intime = AppUtil.convertTo12HourFormat(user.getCheckIn());
            CheckInTime = intime+date;
        }

        if( user.getCheckOut() != null && !user.getCheckOut().equals("")&&!user.getCheckOut().equals("07.10")){
            String outtime = AppUtil.convertTo12HourFormat(user.getCheckOut());
            CheckOutTime = outtime+date;
        }

        holder.TxtInTime.setText(CheckInTime);
        holder.TxtOutTime.setText(CheckOutTime);

        holder.TxtInAddress.setText(user.getLocation());
        holder.TxtOutAddress.setText(user.getLocation());

        if(user.getCheckIn().equals("0000")){
            holder.lay_InTime.setVisibility(View.GONE);
        }
        if(user.getCheckOut().equals("0000")){
            holder.lay_OutTime.setVisibility(View.GONE);
        }
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
