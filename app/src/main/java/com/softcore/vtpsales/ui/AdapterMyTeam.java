package com.softcore.vtpsales.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softcore.vtpsales.databinding.AdapterMyteamBinding;

public class AdapterMyTeam extends RecyclerView.Adapter<AdapterMyTeam.HolderMyTeam> {

    @NonNull
    @Override
    public HolderMyTeam onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterMyteamBinding binding=AdapterMyteamBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new HolderMyTeam(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderMyTeam holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 9;
    }

    class HolderMyTeam extends RecyclerView.ViewHolder {
        AdapterMyteamBinding binding;
        public HolderMyTeam(@NonNull AdapterMyteamBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;
        }
    }
}
