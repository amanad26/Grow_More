package com.makedir.grow.ui;

import static com.makedir.grow.RetrofitApis.BaseUrls.IMAGE_URL;
import static com.makedir.grow.utils.Constants.getErrorToast;
import static com.makedir.grow.utils.Constants.getSuccessToast;
import static com.makedir.grow.utils.Constants.server;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.makedir.grow.Model.UpdateModel;
import com.makedir.grow.Model.UpiIdsModel;
import com.makedir.grow.R;
import com.makedir.grow.RetrofitApis.RatrofitClient;
import com.makedir.grow.databinding.ActivityWaitPaymentBinding;
import com.makedir.grow.utils.ProgressDialog;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WaitPaymentActivity extends AppCompatActivity {

    String selectedPrice = "";
    ActivityWaitPaymentBinding binding;
    WaitPaymentActivity activity;
    Session session;
    ProgressDialog pd;
    private String isRecharge = "0";
    UpiIdsModel.Datum data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWaitPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        session = new Session(activity);
        pd = new ProgressDialog(activity);

        selectedPrice = getIntent().getStringExtra("price");
        isRecharge = getIntent().getStringExtra("recharge");
        data = (UpiIdsModel.Datum) getIntent().getSerializableExtra("data");

        Log.e("TAG", "onCreate: Price " + selectedPrice);

        binding.icBack.setOnClickListener(view -> onBackPressed());

        binding.amountTv.setText(selectedPrice);

        binding.icCode.setText(data.getUpiId());
        Picasso.get().load(IMAGE_URL + data.getImage()).placeholder(R.drawable.ic_logo).into(binding.barCodeImage);

        binding.icCode.setOnLongClickListener(view -> {
            String text = binding.icCode.getText().toString();

            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("Copied Text", text);
            clipboardManager.setPrimaryClip(clipData);

            getSuccessToast(activity, "Text Copied");
            //Toast.makeText(activity, "Text copied", Toast.LENGTH_SHORT).show();
            return true;
        });

        binding.coptyCode.setOnClickListener(view -> {
            String text = binding.icCode.getText().toString();

            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("Copied Text", text);
            clipboardManager.setPrimaryClip(clipData);

            getSuccessToast(activity, "Text Copied");
            //Toast.makeText(activity, "Text copied", Toast.LENGTH_SHORT).show();
        });

        binding.withdraBtn.setOnClickListener(view -> {
            if (binding.utrEdit.getText().toString().trim().equalsIgnoreCase("")) {
                binding.utrEdit.setError("Enter UTR Number ");
            } else {
                rechargeBalance();
            }
        });

    }

    private void rechargeBalance() {

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm");
        Date d = new Date();
        String time = formatter2.format(d.getTime());

        Locale locale = new Locale("fr", "FR");
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
        String date = dateFormat.format(d);


        pd.show();
        RatrofitClient.getClient(activity).rechargeBalance(
                session.getUserId(),
                session.getUser_name(),
                session.getMobile(),
                binding.utrEdit.getText().toString().trim(),
                selectedPrice,
                session.getCurrent_amount(),
                isRecharge,
                date + " " + time
        ).enqueue(new Callback<UpdateModel>() {
            @Override
            public void onResponse(@NonNull Call<UpdateModel> call, @NonNull Response<UpdateModel> response) {
                pd.dismiss();
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            getErrorToast(activity, response.body().getMsg());
                            //  Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
            }

            @Override
            public void onFailure(@NonNull Call<UpdateModel> call, @NonNull Throwable t) {
                pd.dismiss();
                getErrorToast(activity, server);
                //Toast.makeText(activity, "Server not responding ", Toast.LENGTH_SHORT).show();
            }
        });


    }
}