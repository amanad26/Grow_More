package com.makedir.grow.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makedir.grow.Model.PlanListModel;
import com.makedir.grow.R;
import com.makedir.grow.databinding.PlanListLayoutBinding;
import com.makedir.grow.ui.ConfirmInvestActivity;

import java.util.List;

public class PlanListAdapter extends RecyclerView.Adapter<PlanListAdapter.ViewHolder> {

    Context context;
    List<PlanListModel.Datum> planList;

    public PlanListAdapter(Context context, List<PlanListModel.Datum> planList) {
        this.context = context;
        this.planList = planList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.plan_list_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.binding.days.setText(planList.get(position).getDays());
        holder.binding.income.setText(planList.get(position).getDayPrice());
        holder.binding.entryFee.setText(planList.get(position).getPrice());
        holder.binding.name.setText(planList.get(position).getName());

        holder.itemView.setOnClickListener(view -> context.startActivity(new Intent(context, ConfirmInvestActivity.class)
                .putExtra("day", planList.get(position).getDays())
                .putExtra("dayPrice", planList.get(position).getDayPrice())
                .putExtra("amount", planList.get(position).getPrice())
                .putExtra("title", planList.get(position).getName())
        ));

    }


    @Override
    public int getItemCount() {
        return planList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        PlanListLayoutBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = PlanListLayoutBinding.bind(itemView);
        }
    }
}
