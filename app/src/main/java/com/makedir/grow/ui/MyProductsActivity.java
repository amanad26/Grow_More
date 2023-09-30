package com.makedir.grow.ui;

import static com.makedir.grow.utils.Constants.getErrorToast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.makedir.grow.Adapters.MyProductAdapter;
import com.makedir.grow.Model.MyProductModel;
import com.makedir.grow.R;
import com.makedir.grow.RetrofitApis.RatrofitClient;
import com.makedir.grow.databinding.ActivityMyProductsBinding;
import com.makedir.grow.databinding.ActivityWithDrawRequestsBinding;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProductsActivity extends AppCompatActivity {

    ActivityMyProductsBinding binding;
    MyProductsActivity activity;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyProductsBinding.inflate(getLayoutInflater());
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

        RatrofitClient.getClient(activity).getProducts(session.getUserId()).enqueue(new Callback<MyProductModel>() {
            @Override
            public void onResponse(@NonNull Call<MyProductModel> call, @NonNull Response<MyProductModel> response) {
                binding.progressBar.setVisibility(View.GONE);
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {

                            List<MyProductModel.Datum> data = response.body().getData();
                            Collections.reverse(data);
                            binding.recycelr.setLayoutManager(new LinearLayoutManager(activity));
                            binding.recycelr.setAdapter(new MyProductAdapter(activity, data));
                        } else {
                            getErrorToast(activity, response.body().getMsg());
                            //Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
            }

            @Override
            public void onFailure(@NonNull Call<MyProductModel> call, @NonNull Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                getErrorToast(activity, "Server Not Respond..!");
            }
        });
    }
}