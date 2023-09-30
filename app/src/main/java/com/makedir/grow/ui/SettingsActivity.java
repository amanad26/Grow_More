package com.makedir.grow.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.makedir.grow.R;
import com.makedir.grow.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity {

    SettingsActivity activity;
    ActivitySettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;

        binding.About.setOnClickListener(view -> startActivity(new Intent(activity, AboutusActivity.class)));
        binding.termsAndCondition.setOnClickListener(view -> startActivity(new Intent(activity, TermsConditionActivity.class)));
        binding.faq.setOnClickListener(view -> startActivity(new Intent(activity, FaqActivity.class)));
        binding.editProfile.setOnClickListener(view -> startActivity(new Intent(activity, UpdateProfileActivity.class)));
        binding.privacyPolicy.setOnClickListener(view -> startActivity(new Intent(activity, PrivacyPolicyActivity.class)));

    }
}