package com.softcore.vtpsales.Adaptors;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.softcore.vtpsales.AttendenceListActivity;
import com.softcore.vtpsales.Model.MyTeamMember;
import com.softcore.vtpsales.MyTeamActivity;
import com.softcore.vtpsales.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterMyTeam extends RecyclerView.Adapter<AdapterMyTeam.UserViewHolder> {
    private List<MyTeamMember> userList;
    private MyTeamActivity activity;

    public AdapterMyTeam() {
        this.userList = new ArrayList<>();
    }

    public void setData(List<MyTeamMember> teamList, MyTeamActivity activity) {
        this.userList = teamList;
        this.activity = activity;
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
        MyTeamMember user = userList.get(position);
        holder.TxtEmpName.setText(user.getName());
        holder.TxtEmpRole.setText(user.getRole());
        holder.TxtEmail.setText(user.getEmail());
        holder.TxtMob.setText(user.getMobile());

        holder.CardCusVistbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  String EmpCode = AppUtil.getStringData(getApplicationContext(),"EmpCode","");

                Intent intent = new Intent(activity, AttendenceListActivity.class);
                intent.putExtra("EmpName",user.getName());
                intent.putExtra("type","cust");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);

              //  Toast.makeText(activity, "EmpName :"+user.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        private TextView TxtEmpName;
        private TextView TxtEmpRole;
        private TextView TxtEmail;
        private TextView TxtMob;
        private LinearLayout CardCusVistbtn;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            TxtEmpName = itemView.findViewById(R.id.txt_EmpName);
            TxtEmpRole = itemView.findViewById(R.id.txt_EmpRole);
            TxtEmail = itemView.findViewById(R.id.txt_EmpEmail);
            TxtMob = itemView.findViewById(R.id.txt_EmpMob);

            CardCusVistbtn = itemView.findViewById(R.id.card_ViewCheckInOut);

        }
    }
}
