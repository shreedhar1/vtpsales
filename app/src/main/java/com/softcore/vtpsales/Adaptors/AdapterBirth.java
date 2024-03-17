package com.softcore.vtpsales.Adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softcore.vtpsales.Model.BirthdayModel;
import com.softcore.vtpsales.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterBirth extends RecyclerView.Adapter<AdapterBirth.UserViewHolder> {
    private List<BirthdayModel> userList;

    public AdapterBirth() {
        this.userList = new ArrayList<>();
    }

    public void setData(List<BirthdayModel> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_birthandanni, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        BirthdayModel user = userList.get(position);
        holder.TxtNameAge.setText(user.getCustomerName());
        holder.TxtDesig.setText("");
        if(user.getType().equals("Anniversary")){
            holder.TxtDob.setText(user.getAnniversary());
        }else if(user.getType().equals("Birthday")){
            holder.TxtDob.setText(user.getType()+"-"+user.getBirthDate());
        }

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView TxtNameAge;
        private TextView TxtDesig;
        private TextView TxtDob;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            TxtNameAge = itemView.findViewById(R.id.txt_Name_Age);
            TxtDesig = itemView.findViewById(R.id.txt_Designation);
            TxtDob = itemView.findViewById(R.id.txt_DOB);

        }
    }
}
