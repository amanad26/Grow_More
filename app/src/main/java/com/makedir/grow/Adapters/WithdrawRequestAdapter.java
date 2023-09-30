package com.makedir.grow.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makedir.grow.Model.WithdrawModel;
import com.makedir.grow.R;
import com.makedir.grow.databinding.WithdrawRequestBinding;

import java.util.List;

public class WithdrawRequestAdapter extends RecyclerView.Adapter<WithdrawRequestAdapter.ViewHolder> {

    Context context;
    List<WithdrawModel.Datum> models;

    public WithdrawRequestAdapter(Context context, List<WithdrawModel.Datum> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.withdraw_request, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.binding.amount.setText(models.get(position).getAmountPay());
        holder.binding.date.setText(models.get(position).getDate());

        if (models.get(position).getStarus().equalsIgnoreCase("pending")) {
            holder.binding.pay.setText("Pending");
            holder.binding.payCard.setCardBackgroundColor(context.getResources().getColor(R.color.color_primary));
        } else {
            holder.binding.pay.setText("Success");
            holder.binding.payCard.setCardBackgroundColor(context.getResources().getColor(R.color.green));
        }

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        WithdrawRequestBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = WithdrawRequestBinding.bind(itemView);
        }
    }
}
