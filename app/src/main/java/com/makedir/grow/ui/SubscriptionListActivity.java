package com.makedir.grow.ui;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.makedir.grow.Adapters.SubscriptionAdapter;
import com.makedir.grow.Model.LogoutModel;
import com.makedir.grow.Model.SubscriptionModel;
import com.makedir.grow.R;
import com.makedir.grow.RetrofitApis.ApiInterface;
import com.makedir.grow.RetrofitApis.RatrofitClient;
import com.makedir.grow.databinding.ActivitySubscriptionListBinding;
import com.makedir.grow.utils.ProgressDialog;
import com.makedir.grow.utils.SubscriptionSelectInterface;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscriptionListActivity extends AppCompatActivity implements SubscriptionSelectInterface {

    ActivitySubscriptionListBinding binding;
    SubscriptionListActivity activity;
    ApiInterface apiInterface;
    ProgressDialog pd;
    public static String selectedSubscriptionId = "";
    public String selectedPrice = "";
    private Session session;
    private String startDate = "", endDate = "", months = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubscriptionListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;
        apiInterface = RatrofitClient.getClient(activity);
        pd = new ProgressDialog(activity);
        session = new Session(activity);

        binding.contiue.setOnClickListener(view -> {
            if (selectedSubscriptionId.equalsIgnoreCase("")) {
                Toast.makeText(activity, "Select Any Plan", Toast.LENGTH_SHORT).show();
            } else {
                addSubscription();
            }
        });


    }

    @SuppressLint("SimpleDateFormat")
    private void addSubscription() {

        Calendar calendar = Calendar.getInstance();
        startDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        Log.e(TAG, "onCreate: Start Date " + startDate);

        // Add 3 months to the Calendar
        calendar.add(Calendar.MONTH, Integer.parseInt(months));
        endDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        Log.e(TAG, "onCreate: End  Date " + endDate);


        pd.show();
        apiInterface.addOrder(
                session.getUserId(),
                selectedPrice,
                startDate,
                endDate,
                selectedSubscriptionId
        ).enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogoutModel> call, @NonNull Response<LogoutModel> response) {
                pd.dismiss();
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            session.setIsPrime(true);
                            Toast.makeText(activity, "Subscribed..", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(activity, DashboardActivity.class));
                            finish();
                        } else {
                            Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
            }

            @Override
            public void onFailure(@NonNull Call<LogoutModel> call, @NonNull Throwable t) {
                pd.dismiss();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getSubscriptionList();
    }

    private void getSubscriptionList() {
        pd.show();
        apiInterface.getSubscription().enqueue(new Callback<SubscriptionModel>() {
            @Override
            public void onResponse(@NonNull Call<SubscriptionModel> call, @NonNull Response<SubscriptionModel> response) {
                pd.dismiss();
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
                            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                            binding.primiumReycelr.setLayoutManager(linearLayoutManager);
                            binding.primiumReycelr.setAdapter(new SubscriptionAdapter(activity, response.body().getData(), activity));
                        }
            }

            @Override
            public void onFailure(@NonNull Call<SubscriptionModel> call, @NonNull Throwable t) {
                pd.dismiss();
            }
        });


    }

    @Override
    public void onSubscriptionSelect(String id, String months2) {
        selectedPrice = id;
        months = months2;
        Log.e(TAG, "onSubscriptionSelect:  " + id);
    }
}