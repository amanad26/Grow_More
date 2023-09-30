package com.makedir.grow.ui;

import static com.makedir.grow.utils.Constants.getErrorToast;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.makedir.grow.Model.AboutusModel;
import com.makedir.grow.RetrofitApis.ApiInterface;
import com.makedir.grow.RetrofitApis.RatrofitClient;
import com.makedir.grow.databinding.ActivityAboutusBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutusActivity extends AppCompatActivity {
    ActivityAboutusBinding binding;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;

        binding.backBtn.setOnClickListener(view -> onBackPressed());

        aboutUs();
    }


    private void aboutUs() {

        ApiInterface apiInterface = RatrofitClient.getClient(activity);
        apiInterface.getAboutUs().enqueue(new Callback<AboutusModel>() {
            @Override
            public void onResponse(Call<AboutusModel> call, Response<AboutusModel> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            Log.e("TAG", "onResponse() called with: call = [" + call + "], response = [" + response + "]");

                            binding.aboutUs.setText(Html.fromHtml(response.body().getAbout().getDescription()));

                        } else
                            getErrorToast(activity, "Failed");
                    }
                }
            }

            @Override
            public void onFailure(Call<AboutusModel> call, Throwable t) {
                getErrorToast(activity, "Server Not Respond.!");
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });
    }

}