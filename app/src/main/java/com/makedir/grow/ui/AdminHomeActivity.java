package com.makedir.grow.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.makedir.grow.Adapters.UsersListAdapter;
import com.makedir.grow.Model.UserListModel;
import com.makedir.grow.R;
import com.makedir.grow.RetrofitApis.ApiInterface;
import com.makedir.grow.RetrofitApis.RatrofitClient;
import com.makedir.grow.RetrofitApis.UpdateInterface;
import com.makedir.grow.databinding.ActivityAdminHomeBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminHomeActivity extends AppCompatActivity implements UpdateInterface {

    AdminHomeActivity activity;
    ActivityAdminHomeBinding binding;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        apiInterface = RatrofitClient.getClient(activity);


    }

    @Override
    protected void onResume() {
        super.onResume();
        getUsersList();
    }

    private void getUsersList() {
        apiInterface.getAllUsersList().enqueue(new Callback<UserListModel>() {
            @Override
            public void onResponse(@NonNull Call<UserListModel> call, @NonNull Response<UserListModel> response) {
                binding.progressbar.setVisibility(View.GONE);
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {

                            binding.recycelr.setLayoutManager(new LinearLayoutManager(activity));
                            binding.recycelr.setAdapter(new UsersListAdapter(activity, response.body().getData(), AdminHomeActivity.this));

                        }
            }

            @Override
            public void onFailure(@NonNull Call<UserListModel> call, @NonNull Throwable t) {
                binding.progressbar.setVisibility(View.GONE);

            }
        });

    }

    @Override
    public void onUpdate() {
        getUsersList();
    }
}