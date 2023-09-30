package com.makedir.grow.ui;

import static com.makedir.grow.utils.Constants.getErrorToast;
import static com.makedir.grow.utils.Constants.server;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.makedir.grow.Model.SignUpModel;
import com.makedir.grow.R;
import com.makedir.grow.RetrofitApis.ApiInterface;
import com.makedir.grow.RetrofitApis.RatrofitClient;
import com.makedir.grow.databinding.ActivitySignupBinding;
import com.makedir.grow.utils.ProgressDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private Activity activity;
    private ActivitySignupBinding binding;
    Session session;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;

        session = new Session(SignupActivity.this);
        pd = new ProgressDialog(activity);

        binding.cardRegister.setOnClickListener(v -> {
            if (isValidate()) {

                if (!binding.edtInvitation.getText().toString().equalsIgnoreCase("")) {

                    if (binding.edtInvitation.getText().toString().equalsIgnoreCase(binding.edtPhone.getText().toString())) {
                        binding.edtInvitation.setError("Invite code and phone number can not same!");
                        binding.edtInvitation.requestFocus();
                    } else if (binding.edtInvitation.getText().toString().length() < 10) {
                        binding.edtInvitation.setError("Invite code must be 10 digits!");
                        binding.edtInvitation.requestFocus();
                    } else {
                        signUp();
                    }

                } else {
                    signUp();
                }


            }
        });

        binding.loginNow.setOnClickListener(view -> {
            startActivity(new Intent(activity, LoginActivity.class));
            finish();
        });

        binding.privacy.setOnClickListener(view -> {
            startActivity(new Intent(activity, PrivacyPolicyActivity.class));
        });
        binding.termsCondition.setOnClickListener(view -> {
            startActivity(new Intent(activity, TermsConditionActivity.class));
        });

    }


    private boolean isValidate() {
        if (binding.edtPassword.getText().toString().equalsIgnoreCase("")) {
            binding.edtPassword.setError("Enter Your Password..!");
            binding.edtPassword.requestFocus();
            return false;
        } else if (binding.confirmPassword.getText().toString().equalsIgnoreCase("")) {
            binding.confirmPassword.setError("Enter Your Confirm  Password..!");
            binding.confirmPassword.requestFocus();
            return false;
        } else if (binding.phoneLogin.getText().toString().equalsIgnoreCase("")) {
            binding.phoneLogin.setError("Enter Your Phone Number..!");
            binding.phoneLogin.requestFocus();
            return false;
        } else if (binding.name.getText().toString().equalsIgnoreCase("")) {
            binding.name.setError("Enter Your Name..!");
            binding.name.requestFocus();
            return false;
        } else if (binding.withPassword.getText().toString().equalsIgnoreCase("")) {
            binding.withPassword.setError("Enter Your Withdraw Password..!");
            binding.withPassword.requestFocus();
            return false;
        } else if (!binding.confirmPassword.getText().toString().equalsIgnoreCase(binding.edtPassword.getText().toString())) {
            binding.confirmPassword.setError("Password not match ..!");
            binding.confirmPassword.requestFocus();
            return false;
        } else if (!binding.checkPrivacy.isChecked()) {
            Toast.makeText(activity, "Please Accept Terms And Conditions.!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }


    }

    private void signUp() {
        signUpWithMobile();
    }

    private void signUpWithMobile() {

        pd.show();

        String inviteCode = "no";
        if (!binding.edtInvitation.getText().toString().equalsIgnoreCase("")) {
            inviteCode = binding.edtInvitation.getText().toString();
        }


        ApiInterface apiInterface = RatrofitClient.getClient(SignupActivity.this);
        apiInterface.signupWithPhone(
                binding.edtPhone.getText().toString(),
                binding.edtPassword.getText().toString(),
                "fcm", binding.withPassword.getText().toString(), binding.name.getText().toString(), inviteCode).enqueue(new Callback<SignUpModel>() {
            @Override
            public void onResponse(@NonNull Call<SignUpModel> call, @NonNull Response<SignUpModel> response) {
                pd.dismiss();
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            Log.e("TAG", "onResponse() called with: call = [" + call + "], response = [" + response + "]");
                            session.setUserId(response.body().getData().getId());

                            session.setLogin(true);
                            session.setEmail(response.body().getResult());
                            session.setPassword(binding.edtPassword.getText().toString());
                            session.setWithdrawPsswordh(binding.withPassword.getText().toString());
                            session.setIsWithdrable(false);
                            session.setIsPrime(false);
                            session.setIsVerified(false);
                            session.setMyWallet(response.body().getData().getWallet());
                            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            Log.d("TAG", "onResponse() called with: call = [" + call + "], response = [" + response + "]");

                        } else
                            Toast.makeText(SignupActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<SignUpModel> call, @NonNull Throwable t) {
                pd.dismiss();
                getErrorToast(activity, server);
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });
    }


}