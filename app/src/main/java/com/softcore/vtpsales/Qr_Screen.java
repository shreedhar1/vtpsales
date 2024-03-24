package com.softcore.vtpsales;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.softcore.vtpsales.AppUtils.AppUtil;
import com.softcore.vtpsales.databinding.ActivityQrScreenBinding;

public class Qr_Screen extends AppCompatActivity {

    ActivityQrScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityQrScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.laybar.appbarTextView.setText("QR Code");

        binding.laybar.backId.setVisibility(View.VISIBLE);
        binding.laybar.backId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String dbName = AppUtil.getStringData(getApplicationContext(), "DatabaseName", "");


       if(dbName.equals("ARRHUM_LIVE")){
           binding.img.setImageResource(R.drawable.img_arrhum);
           binding.txtDbname.setText("Arrhum Enterprises");
       }else if(dbName.equals("VTP_LIVE")){
           binding.txtDbname.setText("V T PALRESHA & CO PRIVATE LIMITED");
           binding.img.setImageResource(R.drawable.img_v_t_palresha);
       }else if(dbName.equals("WELWORTH_LIVE")){
           binding.txtDbname.setText("Welworth Enterprises");
           binding.img.setImageResource(R.drawable.img_welworth);
       }else  if(dbName.equals("ENVIIRO_LIVE")){
           binding.txtDbname.setText("ENVIIRO BUILDMATE PRIVATE LIMITED");
           binding.img.setImageResource(R.drawable.img_enviiro_buildamte);
       }
    }
}