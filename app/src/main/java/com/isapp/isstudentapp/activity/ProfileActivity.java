package com.isapp.isstudentapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.isapp.isstudentapp.common.ColorOfStatusAndNavBar;
import com.isapp.isstudentapp.constant.Constants;
import com.isapp.isstudentapp.databinding.ActivityProfileBinding;
import com.isapp.isstudentapp.model.UserProfile;
import com.isapp.isstudentapp.network.NetworkChangeListener;
import com.isapp.isstudentapp.preference.PreferenceManager;
import com.isapp.isstudentapp.retrofit.ApiInterface;
import com.isapp.isstudentapp.retrofit.RetroFitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;
    ProgressDialog progressDialog;
    PreferenceManager preferenceManager;
    NetworkChangeListener networkChangeListner = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ColorOfStatusAndNavBar colorOfStatusAndNavBar = new ColorOfStatusAndNavBar();
        colorOfStatusAndNavBar.colorOfStatusBar(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading");

        preferenceManager = new PreferenceManager(this);

        binding.profileBackButton.setOnClickListener(v-> onBackPressed());

        final UserProfile userProfile = new UserProfile(preferenceManager.getInt(Constants.USER_ID));
        ApiInterface apiInterface = RetroFitClient.getRetrofit().create(ApiInterface.class);
        Call<UserProfile> call = apiInterface.profilePostData(userProfile);
        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                progressDialog.dismiss();
                binding.scrollProfile.setVisibility(View.VISIBLE);
                if (response.body() == null) {
                    Toast.makeText(ProfileActivity.this, "No response", Toast.LENGTH_SHORT).show();
                } else if (response.body().getStatus().equals("success")) {
                    String url = response.body().getPictureLink();
                    url.replace("sch/", "sch/thumb_");
                    Glide.with(ProfileActivity.this).load(url).into(binding.profileImage);
                    binding.gradeTv.setText(response.body().getGradeName());
                    binding.parentName.setText(response.body().getParentName());
                    binding.cityTv.setText(response.body().getCityName());
                    binding.nameTv.setText(response.body().getName());
                    binding.rollNumber.setText(response.body().getRollNumber());
                    binding.phoneNumber.setText(response.body().getContactNumber());
                    binding.email.setText(response.body().getEmail());
                    binding.addmissionTv.setText(response.body().getAddmissionDate());


                }


            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                progressDialog.dismiss();
            }
        });


    }

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListner, filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListner);
        super.onStop();
    }

}