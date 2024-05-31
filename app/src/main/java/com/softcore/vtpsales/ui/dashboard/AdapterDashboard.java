package com.softcore.vtpsales.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softcore.vtpsales.AppUtils.AppUtil;
import com.softcore.vtpsales.AttendanceHomeActivity;
import com.softcore.vtpsales.BirthdayAndAnniActivity;
import com.softcore.vtpsales.LeadGenerationActivity;
import com.softcore.vtpsales.MyTeamActivity;
import com.softcore.vtpsales.Qr_Screen;
import com.softcore.vtpsales.ReportsActivity2;
import com.softcore.vtpsales.databinding.AdapterDashboardBinding;

import java.util.List;

public class AdapterDashboard extends RecyclerView.Adapter<AdapterDashboard.HolderDashboard> {
    List<DashboardModel> list;
    Context context;

    public AdapterDashboard(List<DashboardModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderDashboard onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_dashboard,parent,false);
        AdapterDashboardBinding binding=AdapterDashboardBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new HolderDashboard(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderDashboard holder, int position) {
        holder.setIsRecyclable(false);
        holder.binding.img.setImageResource(list.get(position).img);
        holder.binding.txtTitle.setText(list.get(position).title);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((AppUtil.isNetworkAvailable(context.getApplicationContext()))){
                    Intent intent=null;
                    switch (position){
                        case 0:
                            intent=new Intent(holder.itemView.getContext(), AttendanceHomeActivity.class);
                            break;
                        case 1:
                            intent=new Intent(holder.itemView.getContext(), BirthdayAndAnniActivity.class);
                            break;
                        case 2:
                            intent=new Intent(holder.itemView.getContext(), Qr_Screen.class);
                            break;
                        case 3:

                            intent=new Intent(holder.itemView.getContext(), LeadGenerationActivity.class);
                            break;
                        case 4:
                            intent=new Intent(holder.itemView.getContext(), ReportsActivity2.class);
                            break;
                        case 5:
                            intent=new Intent(holder.itemView.getContext(), MyTeamActivity.class);
                            break;

                    }
                    if (intent!=null){

                        holder.itemView.getContext().startActivity(intent);
                    }
                }else{
                    Toast.makeText(context, "No Internet Available", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class HolderDashboard extends RecyclerView.ViewHolder {
        AdapterDashboardBinding binding;
        public HolderDashboard(@NonNull AdapterDashboardBinding binding1) {
            super(binding1.getRoot());
            this.binding=binding1;

        }
    }
}
