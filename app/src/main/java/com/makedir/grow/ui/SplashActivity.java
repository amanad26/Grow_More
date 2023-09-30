package com.makedir.grow.ui;

import static com.makedir.grow.utils.Constants.getErrorToast;
import static com.makedir.grow.utils.Constants.getSuccessToast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.makedir.grow.Model.CheckAppData;
import com.makedir.grow.R;
import com.makedir.grow.RetrofitApis.RatrofitClient;
import com.makedir.grow.databinding.ActivitySplashBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    Session session;
    SplashActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        activity = this;
        session = new Session(SplashActivity.this);

        RatrofitClient.getClient(SplashActivity.this).checkAppData().enqueue(new Callback<CheckAppData>() {
            @Override
            public void onResponse(@NonNull Call<CheckAppData> call, @NonNull Response<CheckAppData> response) {
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            // BaseUrls.BASEURL = response.body().getData().getBaseUrl();
                            if (response.body().getData().getIsOff().equalsIgnoreCase("0")) {
                                if (session.isLoggedIn()) {
                                    if (session.getType().equalsIgnoreCase("user")) {
                                        startActivity(new Intent(SplashActivity.this, DashboardActivity.class)
                                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK)
                                        );
                                        finish();
                                    }
                                } else {
                                    startActivity(new Intent(SplashActivity.this, SignupActivity.class)
                                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK)
                                    );
                                    finish();
                                }
                                session.setIsPlayStore(false);
                            } else {
//                                startActivity(new Intent(SplashActivity.this, PlayStoreMainActivity.class)
//                                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK)
//                                );
//                                finish();
                                if (session.isLoggedIn()) {
                                    if (session.getType().equalsIgnoreCase("user")) {
                                        startActivity(new Intent(SplashActivity.this, DashboardActivity.class)
                                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK)
                                        );
                                        finish();
                                    }
                                } else {
                                    startActivity(new Intent(SplashActivity.this, LoginActivity.class)
                                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK)
                                    );
                                    finish();
                                }
                                session.setIsPlayStore(true);
                            }


                        } else {
                            getErrorToast(activity, "Server Not Respond.!");
                            //Toast.makeText(activity, "Server Not Responding..", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                if (response.code() == 403) {
                    getErrorToast(activity, "Server Not Respond.!");
                    //Toast.makeText(activity, "Server Not Responding..", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(@NonNull Call<CheckAppData> call, @NonNull Throwable t) {
                getErrorToast(activity, "Server Not Respond.!");
                //Toast.makeText(activity, "Server not responding ", Toast.LENGTH_SHORT).show();
            }
        });

    }
}