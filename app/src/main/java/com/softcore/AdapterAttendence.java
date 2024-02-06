package com.softcore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softcore.vtpsales.databinding.AdapterAttendenceListBinding;

public class AdapterAttendence extends RecyclerView.Adapter<AdapterAttendence.HolderAttendence> {

    @NonNull
    @Override
    public HolderAttendence onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterAttendenceListBinding binding=AdapterAttendenceListBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new HolderAttendence(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderAttendence holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class HolderAttendence extends RecyclerView.ViewHolder {
        AdapterAttendenceListBinding binding;
        public HolderAttendence(@NonNull AdapterAttendenceListBinding itemView) {
            super(itemView.getRoot());
            this.binding=itemView;
        }
    }
}
