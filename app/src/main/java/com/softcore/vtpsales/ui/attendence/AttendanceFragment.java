package com.softcore.vtpsales.ui.attendence;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.softcore.vtpsales.databinding.FragmentAttendanceBinding;

public class AttendanceFragment extends Fragment {

private FragmentAttendanceBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        AttendanceViewModel attendanceViewModel =
                new ViewModelProvider(this).get(AttendanceViewModel.class);

    binding = FragmentAttendanceBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        final TextView textView = binding.textHome;
        attendanceViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}