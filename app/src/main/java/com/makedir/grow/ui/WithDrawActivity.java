package com.makedir.grow.ui;

import static com.makedir.grow.utils.Constants.getCurrentDate;
import static com.makedir.grow.utils.Constants.getErrorToast;
import static com.makedir.grow.utils.Constants.server;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.makedir.grow.Model.UpdateModel;
import com.makedir.grow.RetrofitApis.RatrofitClient;
import com.makedir.grow.databinding.ActivityWithDrawBinding;
import com.makedir.grow.utils.ProgressDialog;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WithDrawActivity extends AppCompatActivity {

    ActivityWithDrawBinding binding;
    String amount = "";
    ProgressDialog pd;
    WithDrawActivity activity;
    String startDate;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWithDrawBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        session = new Session(activity);
        pd = new ProgressDialog(activity);

        amount = getIntent().getStringExtra("amount");
        binding.amountTv.setText(amount);
        binding.accountNumber.setText(session.getAccountNumber());
        binding.icBack.setOnClickListener(view -> onBackPressed());

        binding.withdraBtn.setOnClickListener(view -> {

            if (binding.password.getText().toString().equalsIgnoreCase("")) {
                binding.password.setError("Enter Withdraw Password..!");
                binding.password.requestFocus();
            } else if (!binding.password.getText().toString().equalsIgnoreCase(session.getWithdrawPssword())) {
                binding.password.setError("Incorrect Password..!");
                binding.password.requestFocus();
            } else {
                if (!binding.walletAmount.getText().toString().equalsIgnoreCase("")) {
                    int am = Integer.parseInt(amount);
                    int enterAmount = Integer.parseInt(binding.walletAmount.getText().toString());
                    if (enterAmount > am) {
                        Toast.makeText(WithDrawActivity.this, "Amount is greater then wallet amount ", Toast.LENGTH_SHORT).show();
                    } else {
                        sendWithDrawRequest(enterAmount, am);
                    }
                } else {
                    binding.walletAmount.setError("Enter amount..");
                    binding.password.requestFocus();
                }
            }


        });


    }

    private void sendWithDrawRequest(int am, int wallet) {
        pd.show();

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Display the time
        String currentTime = String.format("%02d:%02d", hour, minute);

        int rem = Integer.parseInt(session.getRemainig_amount());
        int ramAfter = rem + am;

        int min = (am * 10) / 100;
        int amountToPay = am - min;
        int reamingWallet = wallet - am;

        RatrofitClient.getClient(activity).addWithdrawRequest(
                session.getUserId(),
                session.getUser_name(),
                session.getMobile(),
                String.valueOf(am),
                String.valueOf(amountToPay),
                getCurrentDate() + " " + currentTime,
                String.valueOf(ramAfter),
                String.valueOf(reamingWallet)

        ).enqueue(new Callback<UpdateModel>() {
            @Override
            public void onResponse(@NonNull Call<UpdateModel> call, @NonNull Response<UpdateModel> response) {
                pd.dismiss();
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            Toast.makeText(activity, "Request Send..!", Toast.LENGTH_SHORT).show();
                            session.setWithdrawcheckIn(getCurrentDate() + " " + currentTime);
                            finish();
                        } else {
                            getErrorToast(activity, response.body().getMsg());
                            ///Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
            }

            @Override
            public void onFailure(@NonNull Call<UpdateModel> call, @NonNull Throwable t) {
                getErrorToast(activity, server);
            }
        });

    }
}

/*

public function runDailyScript(){

    $query = $this->db->where('isPrime', '1')->get('users');

    if($query->num_rows() > 0 ){

     foreach ($query->result() as $value) {
  code to be executed;


         $userId = $value->row()->id ;

        $queryInner = $this->db->where('user_id', $userId)->get('current_plan');

         if($queryInner->num_rows() > 0){
             print_r($queryInner->result());
         }else{
             print_r("No data");
         }


     }
    }else{
        print_r("No data");
    }


}

* */