package com.softcore.vtpsales.Adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softcore.vtpsales.Model.BirthdayModel;
import com.softcore.vtpsales.Model.MyTeamModel;
import com.softcore.vtpsales.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterMyTeam extends RecyclerView.Adapter<AdapterMyTeam.UserViewHolder> {
    private List<MyTeamModel> userList;

    public AdapterMyTeam() {
        this.userList = new ArrayList<>();
    }

    public void setData(List<MyTeamModel> teamList) {
        this.userList = teamList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_myteam, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        MyTeamModel user = userList.get(position);
        holder.TxtEmpName.setText(user.getName());
        holder.TxtEmpRole.setText(user.getRole());

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        private TextView TxtEmpName;
        private TextView TxtEmpRole;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            TxtEmpName = itemView.findViewById(R.id.txt_EmpName);
            TxtEmpRole = itemView.findViewById(R.id.txt_EmpRole);

        }
    }
}
