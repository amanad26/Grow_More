package com.makedir.grow.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.makedir.grow.Model.FaqModel;
import com.makedir.grow.R;
import com.makedir.grow.RetrofitApis.ApiInterface;
import com.makedir.grow.RetrofitApis.RatrofitClient;
import com.makedir.grow.databinding.ActivityFaqBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FaqActivity extends AppCompatActivity {
    ActivityFaqBinding binding;
    Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFaqBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;

        binding.backBtn.setOnClickListener(view -> onBackPressed());


    }

    private void termsCondition() {

        ApiInterface apiInterface = RatrofitClient.getClient(activity);
        apiInterface.get_faq().enqueue(new Callback<FaqModel>() {
            @Override
            public void onResponse(Call<FaqModel> call, Response<FaqModel> response) {
                if (response.code() == 200) {

                    Log.e("TAG", "onResponse() called with: call = [" + call + "], response = [" + response + "]");

                    if (response.body() != null) {

                        Log.e("TAG", "onResponse() called with: call = [" + call + "], response = [" + response + "]");

                        if (response.body().getResult().equalsIgnoreCase("")) {
                            Log.e("TAG", "onResponse() called with: call = [" + call + "], response = [" + response + "]");

                           // binding.faq.setText(response.body().getTermsCondition().getTermConditionText());

                        } else
                            Toast.makeText(activity, "sadwef", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<FaqModel> call, Throwable t) {

                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });
    }

}