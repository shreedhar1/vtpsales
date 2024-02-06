package com.softcore.vtpsales;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softcore.vtpsales.databinding.AdapterBirthandanniBinding;

public class AdapterBirth extends RecyclerView.Adapter<AdapterBirth.HolderBirth> {

    @NonNull
    @Override
    public HolderBirth onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterBirthandanniBinding binding=AdapterBirthandanniBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new HolderBirth(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderBirth holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class HolderBirth extends RecyclerView.ViewHolder {
        AdapterBirthandanniBinding binding;
        public HolderBirth(@NonNull AdapterBirthandanniBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;
        }
    }
}
