package com.softcore.vtpsales.ui.attendence;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.softcore.vtpsales.AttendenceDashActivity;
import com.softcore.vtpsales.databinding.ActivityAttendenceHomeBinding;
import com.softcore.vtpsales.databinding.FragmentAttendanceBinding;

public class AttendanceFragment extends Fragment {

private FragmentAttendanceBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        AttendanceViewModel attendanceViewModel =
                new ViewModelProvider(this).get(AttendanceViewModel.class);

    binding = FragmentAttendanceBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

//        final TextView textView = binding.textHome;
 //       attendanceViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

                binding.cardDailyAttendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getContext(), AttendenceDashActivity.class);
                intent.putExtra("type","emp");
                startActivity(intent);
            }
        });
        binding.cardCustomerAttendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), AttendenceDashActivity.class);
                intent.putExtra("type","cust");
                startActivity(intent);
            }
        });



        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

