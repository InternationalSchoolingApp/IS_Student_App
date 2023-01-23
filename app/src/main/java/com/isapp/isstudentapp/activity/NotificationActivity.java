package com.isapp.isstudentapp.activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

import com.isapp.isstudentapp.common.ColorOfStatusAndNavBar;
import com.isapp.isstudentapp.constant.Constants;
import com.isapp.isstudentapp.adapter.NotificationAdapter;
import com.isapp.isstudentapp.databinding.ActivityNotificationBinding;
import com.isapp.isstudentapp.model.NotificationForApp;
import com.isapp.isstudentapp.network.NetworkChangeListener;
import com.isapp.isstudentapp.preference.PreferenceManager;
import com.isapp.isstudentapp.retrofit.ApiInterface;
import com.isapp.isstudentapp.retrofit.RetroFitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {

    private ActivityNotificationBinding binding;
    private ProgressDialog progressDialog;
    PreferenceManager preferenceManager;
    NetworkChangeListener networkChangeListner = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(this);
        binding.notificationList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        binding.notificationList.setLayoutManager(llm);
        ColorOfStatusAndNavBar colorOfStatusAndNavBar = new ColorOfStatusAndNavBar();
        colorOfStatusAndNavBar.loginAndForgetPassword(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setTitle("Loading");
        progressDialog.setCancelable(false);
        binding.notificationBackButton.setOnClickListener(v-> onBackPressed());
        ApiInterface apiInterface = RetroFitClient.getRetrofit().create(ApiInterface.class);
        NotificationForApp notificationForApp = new NotificationForApp(preferenceManager.getInt(Constants.USER_ID));
        Call<NotificationForApp> call = apiInterface.notificationForApp(notificationForApp);
        call.enqueue(new Callback<NotificationForApp>() {
            @Override
            public void onResponse(Call<NotificationForApp> call, Response<NotificationForApp> response) {
                progressDialog.dismiss();
                if(response.isSuccessful()){
                    if (response.body().getMessage().equals("Notification got")){
                        List<NotificationForApp.ListForApp> notification = response.body().getListForApp();
                        NotificationAdapter notificationAdapter = new NotificationAdapter(notification);
                        binding.notificationList.setAdapter(notificationAdapter);
                    }else{
                        Toast.makeText(NotificationActivity.this, "No notification", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationForApp> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(NotificationActivity.this, "Technical Glitch", Toast.LENGTH_SHORT).show();
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