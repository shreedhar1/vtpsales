package com.softcore.vtpsales.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.softcore.vtpsales.AppUtils.AppUtil;
import com.softcore.vtpsales.BirthdayAndAnniActivity;
import com.softcore.vtpsales.Model.BirthdayModel;
import com.softcore.vtpsales.R;

import java.util.ArrayList;
        import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterViewHolder> {

    // list for storing urls of images.
    private final List<BirthdayModel> mSliderItems;
    private final Context context;
    private final Boolean viewAllVisibility;

    // Constructor
    public SliderAdapter(Context context, List<BirthdayModel> sliderDataArrayList, Boolean viewAllVisibility) {
       this.context = context;
        this.mSliderItems = sliderDataArrayList;
        this.viewAllVisibility = viewAllVisibility;
    }

    // We are inflating the slider_layout
    // inside on Create View Holder method.
    @Override
    public SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slider, null);
        return new SliderAdapterViewHolder(inflate);
    }

    // Inside on bind view holder we will
    // set data to item of Slider View.
    @Override
    public void onBindViewHolder(SliderAdapterViewHolder viewHolder, final int position) {

        if(viewAllVisibility){
            viewHolder.txtviewAll.setVisibility(View.VISIBLE);
        }else {
            viewHolder.txtviewAll.setVisibility(View.GONE);
        }

        final BirthdayModel sliderItem = mSliderItems.get(position);

        int age = AppUtil.calculateAge(sliderItem.getBirthDate());

        if(sliderItem.getType().equals("BirthDay")){
            viewHolder.imageViewBackground.setImageResource(R.drawable.cake_candles_solid);
            viewHolder.textType.setText("Happy Birthday");
            viewHolder.textCusName.setText(sliderItem.getCustomerName()+" - "+String.valueOf(age));

        }else if(sliderItem.getType().equals("Anniversary")){
            viewHolder.imageViewBackground.setImageResource(R.drawable.gift_solid);
            viewHolder.textType.setText("Happy Anniversary");

            viewHolder.textCusName.setText(sliderItem.getCustomerName());
        }




            viewHolder.txtviewAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, BirthdayAndAnniActivity.class);
                    context.startActivity(intent);
                }
            });

    }

    // this method will return
    // the count of our list.
    @Override
    public int getCount() {
        return mSliderItems.size();
    }

    static class SliderAdapterViewHolder extends SliderViewAdapter.ViewHolder {
        // Adapter class for initializing
        // the views of our slider view.
        View itemView;
        ImageView imageViewBackground;
        TextView textCusName;
        TextView textType;
        TextView txtviewAll;


        public SliderAdapterViewHolder(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.image_view);
            textCusName = itemView.findViewById(R.id.text_customer_name);
            textType = itemView.findViewById(R.id.text_type);
            txtviewAll = itemView.findViewById(R.id.txt_viewAll);
        }
    }
}
