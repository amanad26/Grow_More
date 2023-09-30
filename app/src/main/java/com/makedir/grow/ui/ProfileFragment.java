package com.makedir.grow.ui;

import static com.makedir.grow.utils.Constants.getCurrentDate;
import static com.makedir.grow.utils.Constants.getErrorToast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.makedir.grow.Model.LoginModel;
import com.makedir.grow.R;
import com.makedir.grow.RetrofitApis.ApiInterface;
import com.makedir.grow.RetrofitApis.RatrofitClient;
import com.makedir.grow.databinding.FragmentProfileBinding;
import com.makedir.grow.utils.ProgressDialog;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    Activity activity;
    ApiInterface apiInterface;
    Session session;
    ProgressDialog pd;
    boolean withAble = false;
    String isBankAdd = "0";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        activity = requireActivity();
        session = new Session(activity);
        apiInterface = RatrofitClient.getClient(activity);

        pd = new ProgressDialog(activity);

        binding.setting.setOnClickListener(view -> startActivity(new Intent(activity, SettingsActivity.class)));

        binding.aboutUs.setOnClickListener(view -> startActivity(new Intent(activity, AboutusActivity.class)));
        binding.recharge.setOnClickListener(view -> startActivity(new Intent(activity, AddBalanceActivity.class)));
        binding.walletRequest.setOnClickListener(view -> startActivity(new Intent(activity, WithDrawRequestsActivity.class)));
        binding.buyProducts.setOnClickListener(view -> startActivity(new Intent(activity, MyProductsActivity.class)));
        binding.termsAndCondition.setOnClickListener(view -> startActivity(new Intent(activity, TermsConditionActivity.class)));
        binding.faq.setOnClickListener(view -> startActivity(new Intent(activity, FaqActivity.class)));
        binding.editProfile.setOnClickListener(view -> startActivity(new Intent(activity, UpdateProfileActivity.class)));
        binding.privacyPolicy.setOnClickListener(view -> startActivity(new Intent(activity, PrivacyPolicyActivity.class)));
        binding.addbank.setOnClickListener(view -> startActivity(new Intent(activity, AddBankActivity.class).putExtra("bankAdded", isBankAdd)));

        binding.logout.setOnClickListener(view -> {
            session.setLogin(false);
            startActivity(new Intent(activity, WelcomeActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK)
            );
            Toast.makeText(requireContext(), "Sign Out", Toast.LENGTH_SHORT).show();
            requireActivity().finish();
        });

        binding.walletBtn.setOnClickListener(view -> {
            Log.e("TAG", "onCreateView: session.getWithdrawcheckIn() " + session.getWithdrawcheckIn());
            int amount = Integer.parseInt(binding.walletBalance.getText().toString());
            if (session.getIsPrime()) {
                if (withAble) {
                    if (amount > 150) {
                        if (isBankAdd.equalsIgnoreCase("1")) {

                            if (session.getWithdrawcheckIn().equalsIgnoreCase("")) {
                                Log.e("TAG", "onCreateView: First if ");
                                startActivity(new Intent(requireContext(), WithDrawActivity.class).putExtra("amount", String.valueOf(amount)));
                            } else {
                                if (!session.getWithdrawcheckIn().equalsIgnoreCase(getCurrentDate())) {
                                    startActivity(new Intent(requireContext(), WithDrawActivity.class).putExtra("amount", String.valueOf(amount)));
                                    Log.e("TAG", "onCreateView: Second if ");
                                } else {
                                    getErrorToast(activity, "You can only one withdraw in a day..!");
                                    // Toast.makeText(activity, "You can only one withdraw in a day..!", Toast.LENGTH_SHORT).show();
                                }
                            }

                        } else {
                            getErrorToast(activity, "Please add your bank account..!");
                            //Toast.makeText(activity, "Please add your bank account ", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(requireContext(), AddBankActivity.class)
                                    .putExtra("bankAdded", "0"));
                        }
                    } else {
                        getErrorToast(activity, "Minimum withdraw amount is 150..!");
                        //Toast.makeText(activity, "Minimum withdraw amount is 150", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    getErrorToast(activity, "Withdraw time 10AM to 6PM..!");
                    // Toast.makeText(activity, "Withdraw time 12PM to 10PM ", Toast.LENGTH_SHORT).show();
                }

            } else {
                getErrorToast(activity, "Purchase any one product to withdraw your amount ..!");
                //  Toast.makeText(activity, "Purchase any one product to withdraw your amount  ", Toast.LENGTH_SHORT).show();
            }
        });


        // Define the start and end times
        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 10);
        startTime.set(Calendar.MINUTE, 0);

        Calendar endTime = Calendar.getInstance();
        endTime.set(Calendar.HOUR_OF_DAY, 18);
        endTime.set(Calendar.MINUTE, 0);

        Calendar currentTime = Calendar.getInstance();

        if (currentTime.after(startTime) && currentTime.before(endTime)) {
            Log.e("TAG", "onCreateView: You can Withdraw ");
            withAble = true;
        } else {
            withAble = false;
        }

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (session.getIsPlayStore()) {
            binding.linearBalance.setVisibility(View.GONE);
            binding.linearProducts.setVisibility(View.GONE);
            binding.lineraProfileEdit.setVisibility(View.GONE);
        }
        getUserProfile();
    }

    private void getUserProfile() {

        pd.show();
        apiInterface.userProfile(session.getUserId()).enqueue(new Callback<LoginModel>() {
            private static final String TAG = "Update Profile";

            @Override
            public void onResponse(@NonNull Call<LoginModel> call, @NonNull Response<LoginModel> response) {
                pd.dismiss();
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            LoginModel.Data data = response.body().getData();

                            binding.number.setText(data.getMobile());
                            binding.textUserName.setText(data.getName());
                            binding.walletBalance.setText(data.getWallet());
                            binding.currentBalance.setText(data.getCurrent());
                            binding.remainingBalance.setText(data.getRemaining());

                            session.setUser_name(data.getName());
                            session.setEmail(data.getEmail());
                            session.setMobile(data.getMobile());
                            session.setMyWallet(data.getWallet());
                            session.setCurrent_amount(data.getCurrent());
                            session.setRemainig_amount(data.getRemaining());
                            session.setWithdrawPsswordh(data.getWith_password());

                            if (data.getIsPrime().equalsIgnoreCase("1")) session.setIsPrime(true);
                            if (data.getBankAdded().equalsIgnoreCase("1")) isBankAdd = "1";
                            session.setMyInviteCode(data.getInviteCode());
                            session.setUserImage(response.body().getPath() + data.getImage());

                            if (!data.getImage().equalsIgnoreCase(""))
                                Picasso.get().load(response.body().getPath() + data.getImage()).into(binding.imageUserProfile);

                            Log.e(TAG, "onResponse: Mobile" + session.getMobile());

                        }

            }

            @Override
            public void onFailure(@NonNull Call<LoginModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });

    }


}