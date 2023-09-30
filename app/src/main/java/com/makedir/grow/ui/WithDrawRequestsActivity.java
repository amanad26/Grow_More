package com.makedir.grow.ui;

import static com.makedir.grow.utils.Constants.getErrorToast;
import static com.makedir.grow.utils.Constants.server;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.makedir.grow.Adapters.WithdrawRequestAdapter;
import com.makedir.grow.Model.WithdrawModel;
import com.makedir.grow.R;
import com.makedir.grow.RetrofitApis.RatrofitClient;
import com.makedir.grow.databinding.ActivityWithDrawRequestsBinding;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WithDrawRequestsActivity extends AppCompatActivity {

    ActivityWithDrawRequestsBinding binding;
    WithDrawRequestsActivity activity;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWithDrawRequestsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        session = new Session(activity);
        getWithDraw();

        binding.backBtn.setOnClickListener(view -> onBackPressed());


    }

    @Override
    protected void onResume() {
        super.onResume();
        getWithDraw();
    }

    private void getWithDraw() {

        RatrofitClient.getClient(activity).getWithdrawRequest(session.getUserId()).enqueue(new Callback<WithdrawModel>() {
            @Override
            public void onResponse(@NonNull Call<WithdrawModel> call, @NonNull Response<WithdrawModel> response) {
                binding.progressBar.setVisibility(View.GONE);
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {

                            List<WithdrawModel.Datum> data = response.body().getData();
                            Collections.reverse(data);
                            binding.recycelr.setLayoutManager(new LinearLayoutManager(activity));
                            binding.recycelr.setAdapter(new WithdrawRequestAdapter(activity, data));
                        } else {
                            getErrorToast(activity, response.body().getMsg());
                            //Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
            }

            @Override
            public void onFailure(@NonNull Call<WithdrawModel> call, @NonNull Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                getErrorToast(activity, server);
            }
        });
    }
}