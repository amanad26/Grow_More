package com.makedir.grow.Adapters;

import static com.makedir.grow.ui.SubscriptionListActivity.selectedSubscriptionId;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makedir.grow.Model.SubscriptionModel;
import com.makedir.grow.R;
import com.makedir.grow.databinding.SubscriptionListLayoutBinding;
import com.makedir.grow.utils.SubscriptionSelectInterface;

import java.util.List;

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.ViewHolder> {

    Context context;
    private int selectedItem = -1;
    List<SubscriptionModel.Datum> models;
    SubscriptionSelectInterface subscriptionSelectInterface;

    public SubscriptionAdapter(Context context, List<SubscriptionModel.Datum> models, SubscriptionSelectInterface subscriptionSelectInterface) {
        this.context = context;
        this.models = models;
        this.subscriptionSelectInterface = subscriptionSelectInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.subscription_list_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.binding.days.setText(models.get(position).getMonths() + "/Months");
        holder.binding.priceTv.setText("$ " + models.get(position).getFee());
        holder.binding.priceRatio.setText(models.get(position).getRatio());
        holder.binding.returnPrice.setText("$ " + models.get(position).getReturnPrice() + "/Day");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//              int previousItem = selectedItem;
//              selectedItem = position;

                if (selectedItem == -1) {
                    selectedItem = position;
                    selectedSubscriptionId = models.get(position).getId();
                    subscriptionSelectInterface.onSubscriptionSelect(models.get(position).getFee(),models.get(position).getMonths());
                    holder.binding.border.setBackgroundResource(R.drawable.selected_premium_layout);
                } else {

                    if (position == selectedItem) {
                        holder.binding.border.setBackgroundResource(R.drawable.premium_background);
                        selectedSubscriptionId = "";
                        selectedItem = -1 ;
                    }
                }

                Log.e("TAG", "onClick: selected pos " + selectedItem);
            }
        });

//        if (selectedItem == position) {
//            holder.binding.border.setBackgroundResource(R.drawable.selected_premium_layout);
//        } else {
//            holder.binding.border.setBackgroundResource(R.drawable.premium_background);
//        }

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SubscriptionListLayoutBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SubscriptionListLayoutBinding.bind(itemView);
        }
    }

}
