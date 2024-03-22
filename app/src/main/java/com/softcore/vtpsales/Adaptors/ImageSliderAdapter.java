package com.softcore.vtpsales.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.softcore.vtpsales.AppUtils.AppUtil;
import com.softcore.vtpsales.BirthdayAndAnniActivity;
import com.softcore.vtpsales.Model.BirthdayModel;
import com.softcore.vtpsales.R;

import java.util.List;

public class ImageSliderAdapter extends PagerAdapter {

    private Context context;
    private List<BirthdayModel> sliderItems;
    private Boolean viewAllVisibility;

    public ImageSliderAdapter(Context context, List<BirthdayModel> sliderItems, Boolean viewAllVisibility) {
        this.context = context;
        this.sliderItems = sliderItems;
        this.viewAllVisibility = viewAllVisibility;
    }

    @Override
    public int getCount() {
        return sliderItems.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_slider, container, false);

        TextView textType = view.findViewById(R.id.text_type);
        TextView textCustomerName = view.findViewById(R.id.text_customer_name);
        TextView textViewAll = view.findViewById(R.id.txt_viewAll);

        if(viewAllVisibility){
            textViewAll.setVisibility(View.VISIBLE);
        }else {
            textViewAll.setVisibility(View.GONE);
        }

        BirthdayModel user = sliderItems.get(position);

      //  String[] parts = sliderItems.get(position).split(" - ");



        int age = AppUtil.calculateAge(user.getBirthDate());

        if(user.getType().equals("BirthDay")){
            textType.setText("Happy Birthday");
            textCustomerName.setText(user.getCustomerName()+" - "+String.valueOf(age));
        }else if(user.getType().equals("Anniversary")){
            textType.setText("Happy Anniversary");
            textCustomerName.setText(user.getCustomerName());
        }

        container.addView(view);

        textViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BirthdayAndAnniActivity.class);
                context.startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
