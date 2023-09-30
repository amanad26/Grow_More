package com.makedir.grow.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.makedir.grow.Model.ForgotPasswordModel;
import com.makedir.grow.R;
import com.makedir.grow.RetrofitApis.ApiInterface;
import com.makedir.grow.RetrofitApis.RatrofitClient;
import com.makedir.grow.databinding.ActivityForgotPasswordBinding;
import com.makedir.grow.utils.ProgressDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    ForgotPasswordActivity activity;
    ActivityForgotPasswordBinding binding;
    ApiInterface apiInterface;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;
        pd = new ProgressDialog(activity);
        apiInterface = RatrofitClient.getClient(activity);

        binding.cardLogin.setOnClickListener(view -> {
            if (binding.edtEmail.getText().toString().equalsIgnoreCase("")) {
                binding.edtEmail.setError("Enter Your Email...");
                binding.edtEmail.requestFocus();
            } else {
                forgotPassword();
            }
        });


    }

    private void forgotPassword() {
        pd.show();
        apiInterface.forgotPassword(binding.edtEmail.getText().toString()).enqueue(new Callback<ForgotPasswordModel>() {
            @Override
            public void onResponse(@NonNull Call<ForgotPasswordModel> call, @NonNull Response<ForgotPasswordModel> response) {
                pd.dismiss();
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            Toast.makeText(activity, "Check Your Email...!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(activity, LoginActivity.class));
                            finish();
                        } else {
                            Toast.makeText(activity, "Failed....!", Toast.LENGTH_SHORT).show();
                        }
            }

            @Override
            public void onFailure(@NonNull Call<ForgotPasswordModel> call, @NonNull Throwable t) {
                pd.dismiss();
            }
        });

    }
}