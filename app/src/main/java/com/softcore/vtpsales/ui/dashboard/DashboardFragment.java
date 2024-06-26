package com.softcore.vtpsales.ui.dashboard;

import static com.softcore.vtpsales.AppUtils.AppUtil.isToday;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.smarteist.autoimageslider.SliderView;
import com.softcore.vtpsales.Adaptors.ImageSliderAdapter;
import com.softcore.vtpsales.Adaptors.SliderAdapter;
import com.softcore.vtpsales.AppUtils.AppUtil;
import com.softcore.vtpsales.Model.BirthdayModel;
import com.softcore.vtpsales.Model.CommanResorce;
import com.softcore.vtpsales.R;
import com.softcore.vtpsales.ViewModel.BirthdayListViewModel;
import com.softcore.vtpsales.databinding.FragmentDashboardBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DashboardFragment extends Fragment {

    AdapterDashboard adapterDashboard;
    List<DashboardModel> list=new ArrayList<>();
    String DbName;
private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {


    binding = FragmentDashboardBinding.inflate(inflater, container, false);
    View root = binding.getRoot();



        list.clear();
    list.add(new DashboardModel(R.drawable.svg_attendace_check,"Attendance"));
    list.add(new DashboardModel(R.drawable.svg_celeb,"Birthday & Anniversary"));
    list.add(new DashboardModel(R.drawable.svg_qr,"QR Code"));
    list.add(new DashboardModel(R.drawable.svg_lead,"Lead Generation"));
    list.add(new DashboardModel(R.drawable.svg_report,"Reports"));
    list.add(new DashboardModel(R.drawable.svg_myteams,"My Team"));

    adapterDashboard=new AdapterDashboard(list,getContext());
    binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
    binding.recyclerView.setAdapter(adapterDashboard);


        getData();

        return root;
    }
    private void getData() {
        DbName = AppUtil.getStringData(getContext(),"DatabaseName","");

        AppUtil.showProgressDialog(binding.getRoot(),"Loading");
        System.out.println("Database Name: "+DbName);
        BirthdayListViewModel birthdayListViewModel = new ViewModelProvider(this).get(BirthdayListViewModel.class);
        birthdayListViewModel.getBirthdayListinfo(DbName,"Birthday_Anniversary").observe(getViewLifecycleOwner(), new Observer<CommanResorce<List<BirthdayModel>>>() {
            @Override
            public void onChanged(CommanResorce<List<BirthdayModel>> listCommanResorce) {
                // Check if the fragment's view is still attached
                if (getView() == null) {
                    return;
                }

                if (listCommanResorce.data != null && !listCommanResorce.data.isEmpty()) {

                    List<BirthdayModel> filteredList = new ArrayList<>();


                    for (BirthdayModel model : listCommanResorce.data) {


                        if (isToday(model.getBirthDate())) {
                            filteredList.add(model);
                        }

                    }

                    if(!filteredList.isEmpty()){

                     //   ArrayList<BirthdayModel> sliderDataArrayList = new ArrayList<>();

                        // initializing the slider view.
                      //  SliderView sliderView = findViewById(R.id.slider);

                        // adding the urls inside array list
//                        sliderDataArrayList.add(new SliderData(url1));
//                        sliderDataArrayList.add(new SliderData(url2));
//                        sliderDataArrayList.add(new SliderData(url3));

                        // passing this array list inside our adapter class.
                        SliderAdapter adapter = new SliderAdapter(getContext(), filteredList,true);

                        // below method is used to set auto cycle direction in left to
                        // right direction you can change according to requirement.
                        binding.slider.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);

                        // below method is used to
                        // setadapter to sliderview.
                        binding.slider.setSliderAdapter(adapter);

                        // below method is use to set
                        // scroll time in seconds.
                        binding.slider.setScrollTimeInSec(5);

                        // to set it scrollable automatically
                        // we use below method.
                        binding.slider.setAutoCycle(true);

                        // to start autocycle below method is used.
                        binding.slider.startAutoCycle();


//                        ImageSliderAdapter adapter = new ImageSliderAdapter(getContext(), filteredList,true);
//                        if (binding.viewPager != null) {
//                            binding.viewPager.setAdapter(adapter);
//                            final Handler handler = new Handler();
//                            final Runnable update = new Runnable() {
//                                public void run() {
//                                    int currentPage = binding.viewPager.getCurrentItem();
//                                    int nextPage = currentPage + 1;
//                                    if (nextPage >= adapter.getCount()) {
//                                        nextPage = 0;
//                                    }
//                                    binding.viewPager.setCurrentItem(nextPage, true);
//                                    handler.postDelayed(this, 8000); // Repeat every 3 seconds
//                                }
//                            };
//
//                            // Start auto-slide
//                            handler.postDelayed(update, 3000); // Delayed start after 3 seconds
//                        } else {
//                            Log.e("getData", "ViewPager is null");
//                        }




                    }

                }

                AppUtil.hideProgressDialog();
            }
        });


    }


   // }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}