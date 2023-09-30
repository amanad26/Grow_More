package com.makedir.grow.ui;

import static com.makedir.grow.utils.Constants.getErrorToast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.makedir.grow.Adapters.MyInvitedFriendsAdapter;
import com.makedir.grow.Model.UserListModel;
import com.makedir.grow.R;
import com.makedir.grow.RetrofitApis.RatrofitClient;
import com.makedir.grow.databinding.ActivityMyInvitedFriendsBinding;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyInvitedFriendsActivity extends AppCompatActivity {

    ActivityMyInvitedFriendsBinding binding;
    MyInvitedFriendsActivity activity;
    Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyInvitedFriendsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        session = new Session(activity);

        binding.backBtn.setOnClickListener(view -> onBackPressed());

    }

    @Override
    protected void onResume() {
        super.onResume();
        getMyInvitedFriends();
    }

    private void getMyInvitedFriends() {
        RatrofitClient.getClient(activity).getMyInvitedFriends(session.getMyInviteCode()).enqueue(new Callback<UserListModel>() {
            @Override
            public void onResponse(@NonNull Call<UserListModel> call, @NonNull Response<UserListModel> response) {
                binding.progressBar.setVisibility(View.GONE);
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            List<UserListModel.Datum> data = response.body().getData();
                            Collections.reverse(data);
                            binding.recycelr.setLayoutManager(new LinearLayoutManager(activity));
                            binding.recycelr.setAdapter(new MyInvitedFriendsAdapter(activity, data));
                        } else {
                            getErrorToast(activity, "No Friends Found..!");
                            //Toast.makeText(activity, "No Friends Found", Toast.LENGTH_SHORT).show();
                        }
            }

            @Override
            public void onFailure(@NonNull Call<UserListModel> call, @NonNull Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                getErrorToast(activity, "Server Not Respond..!");
            }
        });

    }
}