package com.softcore.vtpsales;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softcore.vtpsales.databinding.AdapterQrBinding;

public class AdapterQR  extends RecyclerView.Adapter<AdapterQR.HolderQR> {

    @NonNull
    @Override
    public HolderQR onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterQrBinding binding=AdapterQrBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new HolderQR(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderQR holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 6;
    }

    class HolderQR extends RecyclerView.ViewHolder {
        AdapterQrBinding binding;
        public HolderQR(@NonNull AdapterQrBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;
        }
    }
}
