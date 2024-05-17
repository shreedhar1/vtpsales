package com.softcore.vtpsales.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.softcore.vtpsales.AppUtils.AppUtil;
import com.softcore.vtpsales.LeadGenerationActivity;
import com.softcore.vtpsales.LoginActivity;
import com.softcore.vtpsales.Network.ServiceLayerApi;
import com.softcore.vtpsales.R;

public class LogoutAlertFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_logout_aleart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showLogoutDialog();
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Logout")
                .setMessage("Do you want to log out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        AppUtil.showProgressDialog(getView(),"Loading");
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                        ServiceLayerApi.doSapLogout(getActivity(), new ServiceLayerApi.ApiCallback() {
                            @Override
                            public void onLoginSuccess() {

                            }

                            @Override
                            public void onLoginFailure() {
                           //     Toast.makeText(getContext(), "Logout Failed!", Toast.LENGTH_SHORT).show();
                            }
                        });

                        AppUtil.hideProgressDialog();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        getParentFragmentManager().popBackStack();
                        dialog.dismiss();
                    }
                })
                .setCancelable(false) // Prevent dialog from being dismissed by tapping outside
                .show();
    }
}
