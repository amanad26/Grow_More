package com.makedir.grow.Adapters;

import static com.makedir.grow.RetrofitApis.BaseUrls.IMAGE_URL;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makedir.grow.Model.LogoutModel;
import com.makedir.grow.Model.UserListModel;
import com.makedir.grow.R;
import com.makedir.grow.RetrofitApis.ApiInterface;
import com.makedir.grow.RetrofitApis.RatrofitClient;
import com.makedir.grow.RetrofitApis.UpdateInterface;
import com.makedir.grow.databinding.UserListLayoutBinding;
import com.makedir.grow.utils.ProgressDialog;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.ViewHolder> {

    Context context;
    List<UserListModel.Datum> models;
    UpdateInterface updateInterface ;

    public UsersListAdapter(Context context, List<UserListModel.Datum> models, UpdateInterface updateInterface) {
        this.context = context;
        this.models = models;
        this.updateInterface = updateInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.user_list_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.binding.userMobile.setText(models.get(position).getMobile());
        holder.binding.referEarnTv.setText(models.get(position).getName());
        holder.binding.remainingBalance.setText("Remaining : " + models.get(position).getRemaining());

        if (!models.get(position).getImage().equalsIgnoreCase(""))
            Picasso.get().load(IMAGE_URL + models.get(position).getImage()).placeholder(R.drawable.user_noti_2).into(holder.binding.userImaeg);

        holder.binding.pay.setOnClickListener(view -> {
            float remaining = Float.parseFloat(models.get(position).getRemaining());
            float ratio = Float.parseFloat(models.get(position).getRatio());

            float finalPrice = remaining - ratio;

            models.get(position).setRemaining(String.valueOf(finalPrice));
            updatePrice(models.get(position).getId(), finalPrice);

        });


    }

    private void updatePrice(String id, float finalPrice) {
        ProgressDialog pd = new ProgressDialog(context);
        pd.show();

        ApiInterface apiInterface = RatrofitClient.getClient(context);
        apiInterface.updatePrice(id, String.valueOf(finalPrice)).enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogoutModel> call, @NonNull Response<LogoutModel> response) {
                pd.dismiss();
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            Toast.makeText(context, "Payed..", Toast.LENGTH_SHORT).show();
                            updateInterface.onUpdate();
                        } else {
                            Toast.makeText(context, "Failed...", Toast.LENGTH_SHORT).show();
                        }
            }

            @Override
            public void onFailure(@NonNull Call<LogoutModel> call, @NonNull Throwable t) {
                Log.d("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
                pd.dismiss();
            }
        });


    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        UserListLayoutBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = UserListLayoutBinding.bind(itemView);
        }
    }

}
