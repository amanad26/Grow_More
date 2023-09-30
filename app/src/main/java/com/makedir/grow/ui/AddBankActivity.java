package com.makedir.grow.ui;

import static com.makedir.grow.utils.Constants.getErrorToast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.makedir.grow.Model.BankModel;
import com.makedir.grow.Model.UpdateModel;
import com.makedir.grow.RetrofitApis.RatrofitClient;
import com.makedir.grow.databinding.ActivityAddBankBinding;
import com.makedir.grow.utils.ProgressDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBankActivity extends AppCompatActivity {

    ActivityAddBankBinding binding;
    AddBankActivity activity;
    Session session;
    ProgressDialog pd;
    String bankAdded = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBankBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        session = new Session(activity);
        pd = new ProgressDialog(activity);

        bankAdded = getIntent().getStringExtra("bankAdded");

        if (bankAdded.equalsIgnoreCase("1")) {
            binding.title.setText("Update Bank Details");
            binding.btnTitle.setText("Update Bank Details");
            getBankDetails();
        } else {
            binding.title.setText("Add Bank Details");
            binding.btnTitle.setText("Add Bank Details");
        }


        binding.cardRegister.setOnClickListener(view -> {
            if (bankAdded.equalsIgnoreCase("1")) {
                updateBankDetails();
            } else {
                if (isValidate()) addBank();
            }

        });

        binding.icBack.setOnClickListener(View -> onBackPressed());


    }

    private void addBank() {

        pd.show();
        RatrofitClient.getClient(activity).addBankDetails(
                session.getUserId(),
                binding.bankName.getText().toString().trim(),
                binding.accountHolderName.getText().toString().trim(),
                binding.ifscCode.getText().toString().trim(),
                binding.edtAccountNumber.getText().toString().trim()
        ).enqueue(new Callback<UpdateModel>() {
            @Override
            public void onResponse(@NonNull Call<UpdateModel> call, Response<UpdateModel> response) {
                pd.dismiss();
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            session.setAccountNumber(binding.edtAccountNumber.getText().toString());
                            finish();
                        } else
                            getErrorToast(activity, response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<UpdateModel> call, @NonNull Throwable t) {
                pd.dismiss();
                getErrorToast(activity,"Server Not Respond..!");
            }
        });


    }

    private void getBankDetails() {
        pd.show();
        RatrofitClient.getClient(activity).getBank(
                session.getUserId()
        ).enqueue(new Callback<BankModel>() {
            @Override
            public void onResponse(@NonNull Call<BankModel> call, Response<BankModel> response) {
                pd.dismiss();
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            BankModel.Data data = response.body().getData();
                            binding.bankName.setText(data.getBankName());
                            binding.ifscCode.setText(data.getIfscCode());
                            binding.accountHolderName.setText(data.getAccountHolderName());
                            binding.edtAccountNumber.setText(data.getAccountNumber());
                            session.setAccountNumber(data.getAccountNumber());
                        } else
                            getErrorToast(activity, response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<BankModel> call, @NonNull Throwable t) {
                pd.dismiss();
                getErrorToast(activity, "Server Not Respond..!");
            }
        });

    }

    private void updateBankDetails() {

    }

    private boolean isValidate() {
        if (binding.bankName.getText().toString().equalsIgnoreCase("")) {
            binding.bankName.setError("Enter Bank Name..!");
            binding.bankName.requestFocus();
            return false;
        } else if (binding.edtAccountNumber.getText().toString().equalsIgnoreCase("")) {
            binding.edtAccountNumber.setError("Enter Account Number..!");
            binding.edtAccountNumber.requestFocus();
            return false;
        } else if (binding.accountHolderName.getText().toString().equalsIgnoreCase("")) {
            binding.accountHolderName.setError("Enter Account Holder Name..!");
            binding.accountHolderName.requestFocus();
            return false;
        } else if (binding.ifscCode.getText().toString().equalsIgnoreCase("")) {
            binding.ifscCode.setError("Enter IFSC Code  ..!");
            binding.ifscCode.requestFocus();
            return false;
        } else {
            return true;
        }


    }
}