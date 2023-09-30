package com.makedir.grow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.makedir.grow.Model.PaymentResponseModel;
import com.makedir.grow.RetrofitApis.RatrofitClient;
import com.open.open_web_sdk.OpenPayment;
import com.open.open_web_sdk.listener.PaymentStatusListener;
import com.open.open_web_sdk.model.TransactionDetails;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    MainActivity activity;
    public static String key = "Bearer baff4bb0-f156-11ed-86d1-fbd06fd9e8b5:c7d3bd40f23195c94e46770b30ce73e7a9146c9b";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;

        Map<String, Object> map = new HashMap<>();
        map.put("amount", "1");
        map.put("contact_number", "9144171702");
        map.put("email_id", "aman123@gmail.com");
        map.put("currency", "INR");
        map.put("mtx", "ajdahds788");

        RatrofitClient.getPaymentClient(activity).paymentStart(key, map).enqueue(new Callback<PaymentResponseModel>() {
            @Override
            public void onResponse(Call<PaymentResponseModel> call, Response<PaymentResponseModel> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        String id = response.body().getId();
                        OnPaymentStart(id, key);
                    }

                }
            }

            @Override
            public void onFailure(Call<PaymentResponseModel> call, Throwable t) {

            }
        });

    }

    private void OnPaymentStart(String id, String accessKey) {

        OpenPayment payment = new OpenPayment.Builder()
                .with(MainActivity.this)
                .setPaymentToken(id)
                .setEnvironment(OpenPayment.Environment.SANDBOX)
                .setAccessKey("baff4bb0-f156-11ed-86d1-fbd06fd9e8b5")// Add your Color Hex Code here
                .setErrorColor("#fc304e")  // Add your Error Color Hex Code here// Add your Logo URL here
                .build();

        payment.startPayment();

        payment.setPaymentStatusListener(new PaymentStatusListener() {
            @Override
            public void onTransactionCompleted(@NonNull TransactionDetails transactionDetails) {
                Log.e("TAG", "onTransactionCompleted: Okay " + transactionDetails.getPaymentTokenId());
                Log.e("TAG", "onTransactionCompleted: Okay " + transactionDetails.getStatus());
            }

            @Override
            public void onError(@NonNull String s) {
                Log.e("TAG", "onError: Error " + s);
            }
        });

    }
}