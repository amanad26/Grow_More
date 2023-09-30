package com.makedir.grow.ui;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.makedir.grow.R;
import com.makedir.grow.databinding.ActivityDashboardBinding;

public class DashboardActivity extends AppCompatActivity {
    private Activity activity;
    private ActivityDashboardBinding binding;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        session = new Session(activity);


        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_home) {
                loadFrag(new HomeFragment());
                return true;
            } else if (itemId == R.id.navigation_chat) {
                loadFrag(new ChatFragment());
                return true;
            } else if (itemId == R.id.navigation_notifications) {
                loadFrag(new NotificationFragment());
                return true;
            } else if (itemId == R.id.navigation_profile) {
                loadFrag(new ProfileFragment());
                return true;
            }
            return false;
        });
        loadFrag(new HomeFragment());

//        if (!session.getIsPrime())
//            startActivity(new Intent(activity, SubscriptionListActivity.class));

    }

    private void loadFrag(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(binding.container.getId(), fragment);
        ft.commit();
    }
}