package com.softcore.vtpsales;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softcore.vtpsales.Model.MyTeamMember;

import java.util.List;

public class MyTeamAdapter extends RecyclerView.Adapter<MyTeamAdapter.ViewHolder> {
    private Context context;
    private List<MyTeamMember> teamMembers;

    public MyTeamAdapter(Context context, List<MyTeamMember> teamMembers) {
        this.context = context;
        this.teamMembers = teamMembers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_team, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyTeamMember member = teamMembers.get(position);
        holder.nameTextView.setText(member.getName());
        holder.roleTextView.setText(member.getRole());
    }

    @Override
    public int getItemCount() {
        return teamMembers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView roleTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            roleTextView = itemView.findViewById(R.id.roleTextView);
        }
    }
}

