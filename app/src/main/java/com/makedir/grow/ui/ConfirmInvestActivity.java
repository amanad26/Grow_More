package com.makedir.grow.ui;

import static com.makedir.grow.utils.Constants.getErrorToast;
import static com.makedir.grow.utils.Constants.getSuccessToast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.widget.Toast;

import com.makedir.grow.Model.UpdateModel;
import com.makedir.grow.RetrofitApis.RatrofitClient;
import com.makedir.grow.databinding.ActivityConfirmInvestBinding;
import com.makedir.grow.utils.ProgressDialog;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmInvestActivity extends AppCompatActivity {

    ActivityConfirmInvestBinding binding;
    ConfirmInvestActivity activity;
    String day = "", dayPrice = "", amount = "", title = "";
    ProgressDialog pd;
    Session session;
    String startDate = "", endDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfirmInvestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        pd = new ProgressDialog(activity);
        session = new Session(activity);

        binding.icBack.setOnClickListener(Viw -> onBackPressed());

        day = getIntent().getStringExtra("day");
        dayPrice = getIntent().getStringExtra("dayPrice");
        amount = getIntent().getStringExtra("amount");
        title = getIntent().getStringExtra("title");

        binding.dayes.setText(day + " Days");
        binding.amountTv.setText(amount);
        binding.perDay.setText("Rs." + dayPrice);
        binding.titleTv.setText(title);

        binding.walletBalance.setText(session.getCurrent_amount());

        binding.investBtn.setOnClickListener(View -> {
            int wallet = Integer.parseInt(session.getCurrent_amount());
            if (wallet < Integer.parseInt(amount)) {
                //Toast.makeText(activity, "Please Add Funds in Balance.!", Toast.LENGTH_SHORT).show();
                getErrorToast(activity, "Please Add Funds in Balance.!");
                startActivity(new Intent(activity, AddBalanceActivity.class));
                finish();
            } else {
                showDialog();
            }
        });

        Calendar currentDate = Calendar.getInstance();
        int currentYear = currentDate.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH);
        int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);

        startDate = currentDay + "-" + (currentMonth + 1) + "-" + currentYear;

        // Add 45 days to the current date
        Calendar next45Days = Calendar.getInstance();
        next45Days.set(currentYear, currentMonth, currentDay);
        next45Days.add(Calendar.DAY_OF_MONTH, 45);

        // Get the year, month, and day from the next 45 days date
        int nextYear = next45Days.get(Calendar.YEAR);
        int nextMonth = next45Days.get(Calendar.MONTH);
        int nextDay = next45Days.get(Calendar.DAY_OF_MONTH);

        endDate = nextDay + "-" + (nextMonth + 1) + "-" + nextYear;

        Log.e("TAG", "onCreate: Start date " + startDate);
        Log.e("TAG", "onCreate: end date " + endDate);


    }


    private void showDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Do you want to purchase this product?");

        builder.setTitle("Confirmation!");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            buyProduct();
        });

        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
            finish();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void buyProduct() {

        pd.show();
        int walletAmount = Integer.parseInt(session.getCurrent_amount());
        int remaining = walletAmount - (Integer.parseInt(amount));

        RatrofitClient.getClient(activity).buyProduct(
                session.getUserId(),
                session.getUser_name(),
                session.getMobile(),
                amount,
                startDate,
                endDate,
                dayPrice,
                String.valueOf(remaining),
                title
        ).enqueue(new Callback<UpdateModel>() {
            @Override
            public void onResponse(@NonNull Call<UpdateModel> call, @NonNull Response<UpdateModel> response) {
                pd.dismiss();
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            session.setCurrent_amount(String.valueOf(remaining));
                            session.setIsPrime(true);
                            getSuccessToast(activity, "Thank Your For Buy Our Product");
                            // Toast.makeText(activity, "Thank Your For Buy Our Product", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            getErrorToast(activity, response.body().getMsg());
                        }
            }

            @Override
            public void onFailure(@NonNull Call<UpdateModel> call, @NonNull Throwable t) {
                pd.dismiss();
                getErrorToast(activity, "Server Not Responding..!");
            }
        });


    }

}