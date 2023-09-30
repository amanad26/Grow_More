package com.makedir.grow.ui;

import static android.content.ContentValues.TAG;

import static com.makedir.grow.utils.Constants.getErrorToast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.makedir.grow.Model.LoginModel;
import com.makedir.grow.R;
import com.makedir.grow.RetrofitApis.ApiInterface;
import com.makedir.grow.RetrofitApis.RatrofitClient;
import com.makedir.grow.databinding.ActivityLoginBinding;
import com.makedir.grow.utils.ProgressDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private Activity activity;
    Session session;
    boolean isPhoneSelected = false;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        session = new Session(LoginActivity.this);
        activity = this;
        pd = new ProgressDialog(activity);

        binding.cardLogin.setOnClickListener(view -> {
            if (isValidate()) Login();
        });


        if (!session.getRememberPhone().equalsIgnoreCase(""))
            binding.phoneEditEmail.setText(session.getRememberPhone());

        if (!session.getRememberEmail().equalsIgnoreCase(""))
            binding.edtEmail.setText(session.getRememberEmail());

        if (!session.getRememeberPass().equalsIgnoreCase(""))
            binding.edtPasswordCode.setText(session.getRememeberPass());

        binding.textRegister.setOnClickListener(view -> startActivity(new Intent(activity, SignupActivity.class)));

        binding.forgotPass.setOnClickListener(view -> startActivity(new Intent(activity, ForgotPasswordActivity.class)));

    }

    private boolean isValidate() {
        if (binding.phoneLogin.getText().toString().equalsIgnoreCase("")) {
            binding.phoneLogin.setError("Enter Your Phone ..!");
            binding.phoneLogin.requestFocus();
            return false;
        } else if (binding.edtPasswordCode.getText().toString().equalsIgnoreCase("")) {
            binding.edtPasswordCode.setError("Enter Your Password..!");
            binding.edtPasswordCode.requestFocus();
            return false;
        } else {
            return true;
        }


    }

    private void Login() {
        Log.e(TAG, "Login: isLoginSelected " + isPhoneSelected);
        loginWithPhone();

    }


    private void loginWithPhone() {

        if (binding.rememberPass.isChecked()) {
            session.setRememberPhone(binding.phoneEditEmail.getText().toString());
            session.setRememeberPass(binding.edtPasswordCode.getText().toString());
        }

        pd.show();
        ApiInterface apiInterface = RatrofitClient.getClient(LoginActivity.this);
        apiInterface.loginWithMobile(
                binding.phoneEditEmail.getText().toString(),
                binding.edtPasswordCode.getText().toString(),
                "fcm").enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(@NonNull Call<LoginModel> call, @NonNull Response<LoginModel> response) {
                pd.dismiss();
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            Log.e("TAG", "onResponse() called with: call = [" + call + "], response = [" + response + "]");
                            session.setMobile(response.body().getData().getMobile());

                            session.setUserId(response.body().getData().getId());
                            session.setMyInviteCode(response.body().getData().getInviteCode());
                            session.setCurrent_amount(response.body().getData().getCurrent());
                            session.setMyWallet(response.body().getData().getWallet());
                            session.setWithdrawPsswordh(response.body().getData().getWith_password());

                            session.setLogin(true);
                            if (response.body().getData().getIsPrime().equalsIgnoreCase("1")) {
                                session.setIsPrime(true);
                            }

                            session.setType(response.body().getData().getType());

                            startActivity(new Intent(activity, DashboardActivity.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK)

                            );


                            finish();
                            Log.d("TAG", "onResponse() called with: call = [" + call + "], response = [" + response + "]");

                        } else {
                            getErrorToast(activity, response.body().getMsg());
                           // Toast.makeText(LoginActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginModel> call, @NonNull Throwable t) {
                pd.dismiss();
                getErrorToast(activity, "Server Not Respond..!");
               // Toast.makeText(LoginActivity.this, "Server not responding ", Toast.LENGTH_SHORT).show();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });
    }

}