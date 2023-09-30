package com.makedir.grow.ui;

import static com.makedir.grow.utils.Constants.getErrorToast;
import static com.makedir.grow.utils.Constants.getSuccessToast;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.makedir.grow.BuildConfig;
import com.makedir.grow.Model.UserListModel;
import com.makedir.grow.RetrofitApis.RatrofitClient;
import com.makedir.grow.databinding.FragmentNotificationBinding;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotificationFragment extends Fragment {
    private FragmentNotificationBinding binding;
    Session session;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNotificationBinding.inflate(getLayoutInflater());
        session = new Session(requireContext());

        binding.yourReCodeOriginal.setText(session.getMyInviteCode());

        binding.shareLinear.setOnClickListener(view -> sendWhatsapp("My refer code is (" + session.getMyInviteCode() + ") Register with this code and get Rs.20 Free!  \n Download the app from : https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID));


        binding.shareLinear2.setOnClickListener(view -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "My refer code is (" + session.getMyInviteCode() + ") Register with this code and get Rs.20 Free!  \n Download the app from : https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        });

        binding.copyLink.setOnClickListener(view -> {
            String text = "My refer code is (" + session.getMyInviteCode() + ") Register with this code and get Rs.20 Free!  \n Download the app from : https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;

            ClipboardManager clipboardManager = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("Copied Text", text);
            clipboardManager.setPrimaryClip(clipData);
            getSuccessToast(requireContext(), "Text Copied..!");

        });


        binding.yourReCodeOriginal.setOnLongClickListener(view -> {
            String text = binding.yourReCodeOriginal.getText().toString();

            ClipboardManager clipboardManager = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("Copied Text", text);
            clipboardManager.setPrimaryClip(clipData);
            getSuccessToast(requireContext(), "Text Copied..!");
            //Toast.makeText(requireContext(), "Text copied", Toast.LENGTH_SHORT).show();
            return true;
        });

        binding.tapToCopy.setOnClickListener(v -> {
            String text = binding.yourReCodeOriginal.getText().toString();

            ClipboardManager clipboardManager = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("Copied Text", text);
            clipboardManager.setPrimaryClip(clipData);

            getSuccessToast(requireContext(), "Text Copied..!");
        });


        binding.viewReferral.setOnClickListener(view -> startActivity(new Intent(getActivity(), MyInvitedFriendsActivity.class)));

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (session.getIsPlayStore()) binding.lineContent.setVisibility(View.GONE);
        getMyInvitedFriends();
    }


    private void sendWhatsapp(String message) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp");
        if (sendIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(sendIntent);
        } else {
            Toast.makeText(getContext(), "Whatsapp not installed", Toast.LENGTH_SHORT).show();
        }
    }

    private void getMyInvitedFriends() {
        RatrofitClient.getClient(requireContext()).getMyInvitedFriends(session.getMyInviteCode()).enqueue(new Callback<UserListModel>() {
            @Override
            public void onResponse(@NonNull Call<UserListModel> call, @NonNull Response<UserListModel> response) {
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            if (response.body().getData().size() != 0) {
                                binding.viewReferral.setVisibility(View.VISIBLE);
                                binding.viewReTv.setText("View Referral " + String.valueOf(response.body().getData().size()));
                            }
                        }
            }

            @Override
            public void onFailure(@NonNull Call<UserListModel> call, @NonNull Throwable t) {
                getErrorToast(requireContext(), "Server Not Respond..!");
            }
        });

    }

}