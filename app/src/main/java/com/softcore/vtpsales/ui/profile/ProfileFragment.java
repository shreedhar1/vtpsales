package com.softcore.vtpsales.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.softcore.vtpsales.AppUtils.AppUtil;
import com.softcore.vtpsales.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

    binding = FragmentProfileBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

       String EmpName = AppUtil.getStringData(getActivity(),"EmpName","");
       String EmpCode = AppUtil.getStringData(getActivity(),"EmpCode","");
       String EmpEmail = AppUtil.getStringData(getActivity(),"EmpEmail","");
       String EmpMob = AppUtil.getStringData(getActivity(),"EmpMob","");
       String EmpCompany = AppUtil.getStringData(getActivity(),"DatabaseName","");

       binding.txtEmpName.setText(EmpName);
       binding.txtEmpCode.setText(EmpCode);
        binding.txtEmpCom.setText(EmpCompany);
        binding.txtEmpMob.setText(EmpMob);
        binding.txtEmpEmailid.setText(EmpEmail);
        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}