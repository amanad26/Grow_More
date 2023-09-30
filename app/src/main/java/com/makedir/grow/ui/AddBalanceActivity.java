package com.makedir.grow.ui;

import static com.makedir.grow.utils.Constants.getErrorToast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.makedir.grow.Model.UpiIdsModel;
import com.makedir.grow.R;
import com.makedir.grow.RetrofitApis.RatrofitClient;
import com.makedir.grow.databinding.ActivityAddBalanceBinding;
import com.makedir.grow.utils.ProgressDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBalanceActivity extends AppCompatActivity {

    ActivityAddBalanceBinding binding;
    String selectedPrice = "400";
    AddBalanceActivity activity;
    Session session;
    ProgressDialog pd;
    List<UpiIdsModel.Datum> upiData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBalanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        session = new Session(activity);
        pd = new ProgressDialog(activity);
        binding.icBack.setOnClickListener(view -> onBackPressed());

        binding.tv400.setOnClickListener(view -> {
            selectedPrice = "400";
            binding.tv400.setBackgroundResource(R.drawable.add_balace_selected);
            binding.tv1200.setBackgroundResource(R.drawable.add_balance);
            binding.tv2500.setBackgroundResource(R.drawable.add_balance);
            binding.tv5000.setBackgroundResource(R.drawable.add_balance);
            binding.tv9500.setBackgroundResource(R.drawable.add_balance);
            binding.tv15000.setBackgroundResource(R.drawable.add_balance);
        });

        binding.tv1200.setOnClickListener(view -> {
            selectedPrice = "1200";
            binding.tv1200.setBackgroundResource(R.drawable.add_balace_selected);
            binding.tv400.setBackgroundResource(R.drawable.add_balance);
            binding.tv2500.setBackgroundResource(R.drawable.add_balance);
            binding.tv5000.setBackgroundResource(R.drawable.add_balance);
            binding.tv9500.setBackgroundResource(R.drawable.add_balance);
            binding.tv15000.setBackgroundResource(R.drawable.add_balance);
        });


        binding.tv2500.setOnClickListener(view -> {
            selectedPrice = "2500";
            binding.tv2500.setBackgroundResource(R.drawable.add_balace_selected);
            binding.tv400.setBackgroundResource(R.drawable.add_balance);
            binding.tv1200.setBackgroundResource(R.drawable.add_balance);
            binding.tv5000.setBackgroundResource(R.drawable.add_balance);
            binding.tv9500.setBackgroundResource(R.drawable.add_balance);
            binding.tv15000.setBackgroundResource(R.drawable.add_balance);
        });

        binding.tv5000.setOnClickListener(view -> {
            selectedPrice = "5000";
            binding.tv5000.setBackgroundResource(R.drawable.add_balace_selected);
            binding.tv400.setBackgroundResource(R.drawable.add_balance);
            binding.tv1200.setBackgroundResource(R.drawable.add_balance);
            binding.tv2500.setBackgroundResource(R.drawable.add_balance);
            binding.tv9500.setBackgroundResource(R.drawable.add_balance);
            binding.tv15000.setBackgroundResource(R.drawable.add_balance);
        });

        binding.tv9500.setOnClickListener(view -> {
            selectedPrice = "9500";
            binding.tv9500.setBackgroundResource(R.drawable.add_balace_selected);
            binding.tv400.setBackgroundResource(R.drawable.add_balance);
            binding.tv1200.setBackgroundResource(R.drawable.add_balance);
            binding.tv2500.setBackgroundResource(R.drawable.add_balance);
            binding.tv5000.setBackgroundResource(R.drawable.add_balance);
            binding.tv15000.setBackgroundResource(R.drawable.add_balance);
        });
        binding.tv15000.setOnClickListener(view -> {
            selectedPrice = "15000";
            binding.tv15000.setBackgroundResource(R.drawable.add_balace_selected);
            binding.tv400.setBackgroundResource(R.drawable.add_balance);
            binding.tv1200.setBackgroundResource(R.drawable.add_balance);
            binding.tv2500.setBackgroundResource(R.drawable.add_balance);
            binding.tv5000.setBackgroundResource(R.drawable.add_balance);
            binding.tv9500.setBackgroundResource(R.drawable.add_balance);
        });


        binding.withdraBtn.setOnClickListener(view -> {
            if (upiData.size() != 0) {

                int ran = new Random().nextInt(upiData.size());
                UpiIdsModel.Datum data = upiData.get(ran);
                startActivity(new Intent(activity, WaitPaymentActivity.class)
                        .putExtra("price", selectedPrice)
                        .putExtra("recharge", "1")
                        .putExtra("data", (Serializable) data)
                );
            } else {
                getErrorToast(activity, "No UPI Payments Found Try Again Later.!");
                ///Toast.makeText(activity, "", Toast.LENGTH_SHORT).show();
            }


        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getUpiIds();
    }

    private void getUpiIds() {
        RatrofitClient.getClient(activity).getUpiIds().enqueue(new Callback<UpiIdsModel>() {
            @Override
            public void onResponse(@NonNull Call<UpiIdsModel> call, @NonNull Response<UpiIdsModel> response) {
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            Log.e("TAG", "onResponse: On Response " + response.body().getMsg());
                            upiData.clear();
                            upiData.addAll(response.body().getData());
                        } else {
                            getErrorToast(activity, response.body().getMsg());
                            // Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
            }

            @Override
            public void onFailure(@NonNull Call<UpiIdsModel> call, @NonNull Throwable t) {
                getErrorToast(activity, "Server Not Responding.!");
            }
        });


    }


}