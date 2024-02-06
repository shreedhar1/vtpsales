package com.softcore.vtpsales.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.softcore.vtpsales.R;
import com.softcore.vtpsales.databinding.FragmentDashboardBinding;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    AdapterDashboard adapterDashboard;
    List<DashboardModel> list=new ArrayList<>();
private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

    binding = FragmentDashboardBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

    list.clear();
    list.add(new DashboardModel(R.drawable.ic_att,"Attendence"));
    list.add(new DashboardModel(R.drawable.ic_birthday,"Birthday & Anniversary"));
    list.add(new DashboardModel(R.drawable.ic_qr,"QR Code"));
    list.add(new DashboardModel(R.drawable.ic_lead,"Lead Generation"));
    list.add(new DashboardModel(R.drawable.ic_reports,"Reports"));
    list.add(new DashboardModel(R.drawable.ic_team,"My Team"));
    adapterDashboard=new AdapterDashboard(list);
    binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
    binding.recyclerView.setAdapter(adapterDashboard);

        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}