package com.makedir.grow.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.makedir.grow.Model.UpdateModel;
import com.makedir.grow.R;
import com.makedir.grow.RetrofitApis.RatrofitClient;
import com.makedir.grow.chat.ChatAdapterNew;
import com.makedir.grow.chat.ChatModelNew;
import com.makedir.grow.databinding.FragmentChatBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChatFragment extends Fragment {

    String senderRoom;
    String reciverRoom;
    String senderId, reciverid = "0";

    String date, time;
    Session session;
    FirebaseDatabase database;
    DatabaseReference reference;

    String message_id;
    String reciverFcmId = "", receiver_name = "", receiver_image = "";
    ArrayList<ChatModelNew> chatModel = new ArrayList<>();

    FragmentChatBinding binding;
    Activity activity;
    private ChatAdapterNew adapterNew;


    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChatBinding.inflate(getLayoutInflater());

        activity = requireActivity();

        session = new Session(activity);

        senderId = session.getUserId();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("chats");

        senderRoom = senderId + reciverid;
        reciverRoom = reciverid + senderId;

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm");
        Date d = new Date();
        time = formatter2.format(d.getTime());

        Locale locale = new Locale("fr", "FR");
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
        date = dateFormat.format(d);

        getAllMessages();

        ///chatAdapter = new ChatAdapterForVendor(activity, messageModelsList);
        Log.e("TAG", "onCreate() called with: savedInstanceState = [" + date + "]");
        Log.e("TAG", "onCreate() called with: Time = [" + formatter2.format(d.getTime()) + "]");

        binding.icSend.setOnClickListener(view -> {
            if (!binding.edtMessage.getText().toString().equalsIgnoreCase(""))
                sendMessage();
        });

        adapterNew = new ChatAdapterNew(activity, chatModel);
        LinearLayoutManager linearLayout = new LinearLayoutManager(activity);
        linearLayout.setOrientation(RecyclerView.VERTICAL);
        linearLayout.setStackFromEnd(true);
        binding.recyclerViewChat.setLayoutManager(linearLayout);
        binding.recyclerViewChat.setAdapter(adapterNew);

        return binding.getRoot();
    }

    private void sendMessage() {
        message_id = reference.push().getKey();
        Log.e("TAG", "sendMessage() called Message key" + message_id);

        String message = binding.edtMessage.getText().toString();
        ChatModelNew modelNew = new ChatModelNew("Help", "", binding.edtMessage.getText().toString().trim(), "Help", reciverid, "", senderId, "", time, "", "text");
        binding.edtMessage.setText("");
        reference
                .child(reciverRoom)
                .push()
                .setValue(modelNew)
                .addOnSuccessListener(unused -> reference
                        .child(senderRoom)
                        .push()
                        .setValue(modelNew)
                        .addOnSuccessListener(unused1 -> {
                            binding.recyclerViewChat.setAdapter(adapterNew);
                            sendMessageToAdmin(message);
                        })).addOnFailureListener(e -> Log.e("TAG", "onFailure() called with: e = [" + e.getLocalizedMessage() + "]"));


    }

    private void sendMessageToAdmin(String message) {
        RatrofitClient.getClient(activity).addChat(
                senderId,
                reciverid,
                message,
                time
        ).enqueue(new Callback<UpdateModel>() {
            @Override
            public void onResponse(@NonNull Call<UpdateModel> call, @NonNull Response<UpdateModel> response) {

            }

            @Override
            public void onFailure(@NonNull Call<UpdateModel> call, @NonNull Throwable t) {

            }
        });


    }

    private void getAllMessages() {

        database.getReference().child("chats").child(reciverRoom)
                .addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        chatModel.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            ChatModelNew messageModel = snapshot1.getValue(ChatModelNew.class);
                            chatModel.add(messageModel);
                        }
                        adapterNew.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}