package com.makedir.grow.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;


import com.makedir.grow.Model.LoginModel;
import com.makedir.grow.Model.LogoutModel;
import com.makedir.grow.R;
import com.makedir.grow.RetrofitApis.ApiInterface;
import com.makedir.grow.RetrofitApis.RatrofitClient;
import com.makedir.grow.databinding.ActivityUpdateProfileBinding;
import com.makedir.grow.utils.ProgressDialog;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileActivity extends AppCompatActivity {

    ActivityUpdateProfileBinding binding;
    UpdateProfileActivity activity;
    Session session;
    ApiInterface apiInterface;
    ProgressDialog pd;
    private Uri filePath = null;
    private Bitmap bitmap;
    File userImage = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;
        session = new Session(activity);
        apiInterface = RatrofitClient.getClient(activity);
        pd = new ProgressDialog(activity);

        binding.userNameHeaading.setText(session.getUser_name());
        binding.updateEmail.setText(session.getEmail());
        binding.updatePhone.setText(session.getMobile());

        if (!session.getUserImage().equalsIgnoreCase(""))
            Picasso.get().load(session.getUserImage()).placeholder(R.drawable.user_noti_2).into(binding.userImage);


        binding.icBack.setOnClickListener(view -> onBackPressed());

        binding.userImage.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, 100);
        });

//        binding.logout.setOnClickListener(view -> {
//            session.logout();
//            startActivity(new Intent(activity, WelcomeActivity.class)
//                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            );
//            finish();
//        });
        binding.updateSaveChanges.setOnClickListener(view -> updateUserProfile());

    }


    private void getUserProfile() {

        pd.show();
        apiInterface.userProfile(session.getUserId()).enqueue(new Callback<LoginModel>() {
            private static final String TAG = "Update Profile";

            @Override
            public void onResponse(@NonNull Call<LoginModel> call, @NonNull Response<LoginModel> response) {
                pd.dismiss();
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            LoginModel.Data data = response.body().getData();


                            binding.updatePhone.setText(data.getMobile());
                            binding.updateEmail.setText(data.getEmail());
                            binding.updateUserName.setText(data.getName());
                            binding.userNameHeaading.setText(data.getName());

                            session.setUser_name(data.getName());
                            session.setEmail(data.getEmail());
                            session.setMobile(data.getMobile());
                            session.setUserImage(response.body().getPath() + data.getImage());


                            if (!data.getImage().equalsIgnoreCase("")) {
                                Picasso.get().load(response.body().getPath() + data.getImage()).placeholder(R.drawable.user_noti_2).into(binding.userImage);

                            }

                        }

            }

            @Override
            public void onFailure(@NonNull Call<LoginModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserProfile();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            filePath = data.getData();
            bitmap = setBitmap(binding.userImage, binding.userImage);
            userImage = bitmapToFile(activity, bitmap);
            uploadImage();
        }
    }


    private void updateUserProfile() {
        pd.show();
        String prime = "";
        if (session.getIsPrime()) prime = "1";
        else prime = "0";

        apiInterface.updateUserProfile(
                session.getUserId(),
                binding.updateUserName.getText().toString(),
                prime,
                binding.updatePhone.getText().toString(),
                binding.updateEmail.getText().toString()
        ).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(@NonNull Call<LoginModel> call, @NonNull Response<LoginModel> response) {
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            Toast.makeText(activity, "Updated...!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(activity, DashboardActivity.class));
                            finish();
                        } else {
                            Toast.makeText(activity, "Failed...!", Toast.LENGTH_SHORT).show();
                        }
            }

            @Override
            public void onFailure(@NonNull Call<LoginModel> call, @NonNull Throwable t) {

            }
        });


    }


    private void uploadImage() {

        pd.show();

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", userImage.getName(), RequestBody.create(MediaType.parse("image/*"), userImage));
        apiInterface.uploadImage(filePart).enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogoutModel> call, @NonNull Response<LogoutModel> response) {
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            apiInterface.updateProfileImage(session.getUserId(), response.body().getFilename()).enqueue(new Callback<LogoutModel>() {
                                @Override
                                public void onResponse(@NonNull Call<LogoutModel> call, @NonNull Response<LogoutModel> response) {
                                    pd.dismiss();
                                }

                                @Override
                                public void onFailure(@NonNull Call<LogoutModel> call, @NonNull Throwable t) {
                                    pd.dismiss();
                                }
                            });


                        }
            }

            @Override
            public void onFailure(Call<LogoutModel> call, Throwable t) {

            }
        });


    }


    public Bitmap setBitmap(ImageView imageView, ImageView imageView2) {

        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static File bitmapToFile(Context mContext, Bitmap bitmap) {
        try {
            String name = System.currentTimeMillis() + ".png";
            File file = new File(mContext.getCacheDir(), name);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 60, bos);
            byte[] bArr = bos.toByteArray();
            bos.flush();
            bos.close();

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bArr);
            fos.flush();
            fos.close();

            return file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}