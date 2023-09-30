package com.makedir.grow.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makedir.grow.Model.MyProductModel;
import com.makedir.grow.Model.UserListModel;
import com.makedir.grow.R;
import com.makedir.grow.databinding.InvitedListLayoutBinding;
import com.makedir.grow.databinding.PlanListLayoutBinding;

import java.util.List;

public class MyInvitedFriendsAdapter extends RecyclerView.Adapter<MyInvitedFriendsAdapter.ViewHolder> {


    Context context;
    List<UserListModel.Datum> planList;

    public MyInvitedFriendsAdapter(Context context, List<UserListModel.Datum> planList) {
        this.context = context;
        this.planList = planList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.invited_list_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.binding.name.setText(planList.get(position).getName());
        holder.binding.mobile.setText(StrRpl(planList.get(position).getMobile()));
    }


    public static String StrRpl(String str) {

        char[] chars = str.toCharArray();
        for (int i = 2, j = 0; i < chars.length - 2; i++) {
            char ch = chars[i];
            if (!Character.isWhitespace(ch)) {
                chars[i] = '*';
                j++;
            }
        }
        str = new String(chars);
        return str;
    }

    @Override
    public int getItemCount() {
        return planList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        InvitedListLayoutBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = InvitedListLayoutBinding.bind(itemView);
        }
    }

}
