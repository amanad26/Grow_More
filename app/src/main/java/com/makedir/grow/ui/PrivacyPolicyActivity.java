package com.makedir.grow.ui;

import static com.makedir.grow.utils.Constants.getErrorToast;
import static com.makedir.grow.utils.Constants.getSuccessToast;
import static com.makedir.grow.utils.Constants.server;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import com.makedir.grow.Model.PravicyPolicyModel;
import com.makedir.grow.R;
import com.makedir.grow.RetrofitApis.ApiInterface;
import com.makedir.grow.RetrofitApis.RatrofitClient;
import com.makedir.grow.databinding.ActivityPrivacyPolicyBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrivacyPolicyActivity extends AppCompatActivity {
    ActivityPrivacyPolicyBinding binding;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrivacyPolicyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;

        binding.backBtn.setOnClickListener(view -> onBackPressed());


    }

    @Override
    protected void onResume() {
        super.onResume();
        privacyPolicy();
    }

    private void privacyPolicy() {

        ApiInterface apiInterface = RatrofitClient.getClient(activity);
        apiInterface.getPrivacyPolicy().enqueue(new Callback<PravicyPolicyModel>() {
            @Override
            public void onResponse(Call<PravicyPolicyModel> call, Response<PravicyPolicyModel> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            Log.e("TAG", "onResponse() called with: call = [" + call + "], response = [" + response + "]");
                            binding.privacyPolicy.setText(Html.fromHtml(response.body().getPrivacyPolicy().getContent()));

                        } else
                        {
                            getErrorToast(activity, "Failed..!");
                           // Toast.makeText(activity, "sadwef", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<PravicyPolicyModel> call, Throwable t) {
                getErrorToast(activity, server);
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });
    }

}