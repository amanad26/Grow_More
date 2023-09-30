package com.makedir.grow.ui;

import static com.makedir.grow.RetrofitApis.BaseUrls.IMAGE_URL;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.makedir.grow.Adapters.PlanListAdapter;
import com.makedir.grow.Model.LiveSliderModel;
import com.makedir.grow.Model.PlanListModel;
import com.makedir.grow.Model.UpdateModel;
import com.makedir.grow.R;
import com.makedir.grow.RetrofitApis.RatrofitClient;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.makedir.grow.databinding.FragmentHomeBinding;
import com.thecode.aestheticdialogs.AestheticDialog;
import com.thecode.aestheticdialogs.DialogAnimation;
import com.thecode.aestheticdialogs.DialogStyle;
import com.thecode.aestheticdialogs.DialogType;


public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    Session session;
    Activity activity;
    ArrayList<SlideModel> imageList = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(getLayoutInflater());

        activity = requireActivity();
        session = new Session(activity);


        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (session.getIsPlayStore()) {
            binding.linearMainData.setVisibility(View.GONE);
        } else {
            checkCurrentDate();
        }
        getSliders();
        getPlans();


    }

    private void checkCurrentDate() {

        Calendar currentDate = Calendar.getInstance();
        int currentYear = currentDate.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH);
        int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);

        String startDate = currentDay + "-" + (currentMonth + 1) + "-" + currentYear;

        if (session.getCheckIn().equalsIgnoreCase("")) {
            loadCheckInDialog(startDate);
        } else {
            if (!session.getCheckIn().equalsIgnoreCase(startDate)) {
                loadCheckInDialog(startDate);
            }
        }


    }

    private void loadCheckInDialog(String startDate) {

        new AestheticDialog.Builder(activity, DialogStyle.FLAT, DialogType.WARNING)
                .setTitle("Daily Check In ")
                .setMessage("Check in and get Rs.5 free ")
                .setCancelable(false)
                .setDarkMode(true)
                .setGravity(Gravity.CENTER)
                .setAnimation(DialogAnimation.SHRINK)
                .setOnClickListener(builder -> {
                    checkIn(startDate);
                    builder.dismiss();
                }).show();


    }

    private void checkIn(String startDate) {
        RatrofitClient.getClient(activity).checkIn(session.getUserId()).enqueue(new Callback<UpdateModel>() {
            @Override
            public void onResponse(@NonNull Call<UpdateModel> call, @NonNull Response<UpdateModel> response) {
                if (response.code() == 200)
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            session.setCheckIn(startDate);
                        }
                    }
            }

            @Override
            public void onFailure(@NonNull Call<UpdateModel> call, @NonNull Throwable t) {

            }
        });

    }

    private void getSliders() {
        RatrofitClient.getClient(activity).getAllSliders().enqueue(new Callback<LiveSliderModel>() {
            @Override
            public void onResponse(@NonNull Call<LiveSliderModel> call, @NonNull Response<LiveSliderModel> response) {
                if (response.code() == 200)
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            imageList.clear();
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                imageList.add(new SlideModel(IMAGE_URL + response.body().getData().get(i).getUrl(), ScaleTypes.FIT));
                            }
                            binding.imageSlider.setImageList(imageList);
                        }
                    }
            }

            @Override
            public void onFailure(@NonNull Call<LiveSliderModel> call, @NonNull Throwable t) {

            }
        });
    }

    private void getPlans() {
        binding.progress.setVisibility(View.VISIBLE);
        RatrofitClient.getClient(activity).getAllPlans().enqueue(new Callback<PlanListModel>() {
            @Override
            public void onResponse(@NonNull Call<PlanListModel> call, @NonNull Response<PlanListModel> response) {
                binding.progress.setVisibility(View.GONE);
                if (response.code() == 200)
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            binding.planListRecycler.setLayoutManager(new LinearLayoutManager(activity));
                            binding.planListRecycler.setAdapter(new PlanListAdapter(activity, response.body().getData()));
                        }
                    }
            }

            @Override
            public void onFailure(@NonNull Call<PlanListModel> call, @NonNull Throwable t) {
                binding.progress.setVisibility(View.GONE);
            }
        });
    }
}