package com.isapp.isstudentapp.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.isapp.isstudentapp.R;
import com.isapp.isstudentapp.common.ColorOfStatusAndNavBar;
import com.isapp.isstudentapp.common.LogoutDone;
import com.isapp.isstudentapp.constant.Constants;
import com.isapp.isstudentapp.databinding.ActivityDashboardBinding;
import com.isapp.isstudentapp.fragment.ChatFragment;
import com.isapp.isstudentapp.fragment.DashboardFragment;
import com.isapp.isstudentapp.fragment.MoreFragment;
import com.isapp.isstudentapp.model.AppUpdationCheckModel;
import com.isapp.isstudentapp.model.DashboardNotificationModel;
import com.isapp.isstudentapp.model.Status;
import com.isapp.isstudentapp.network.NetworkChangeListener;
import com.isapp.isstudentapp.preference.PreferenceManager;
import com.isapp.isstudentapp.retrofit.ApiInterface;
import com.isapp.isstudentapp.retrofit.RetroFitClient;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.time.LocalTime;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;

    ProgressDialog progressDialog;
    PreferenceManager preferenceManager;


    Animation slide_up;
    long pressedTime;


    String version, versionCode;

    NetworkChangeListener networkChangeListner = new NetworkChangeListener();


    String message;

    Dialog dialog, wishes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        getStatus();
        updation();
        getPopUpWishes();



        binding.notificationBell.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, NotificationActivity.class);
            startActivity(intent);
        });
        binding.bottomNavMenu.setItemSelected(R.id.nav_dashboard, true);
        getNotificationCount();
        getSupportFragmentManager().beginTransaction().replace(binding.fragmentContainer.getId(), new DashboardFragment()).commit();
        bottomMenu();

    }

    private void getStatus() {

        Integer userId = preferenceManager.getInt(Constants.USER_ID);
        Status status = new Status(userId);
        ApiInterface apiInterface = RetroFitClient.getRetrofit().create(ApiInterface.class);
        Call<Status> call = apiInterface.getUserStatus(status);
        call.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                if (!response.body().getStatus().equals("success")){
                    LogoutDone logoutDone = new LogoutDone();
                    logoutDone.logout(preferenceManager.getInt(Constants.PLATFORM_ID), userId, preferenceManager.getString(Constants.USER_EMAIL));
                }            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {

            }
        });

    }

    private void getPopUp() {

        dialog.show();
        androidx.appcompat.widget.AppCompatButton button, btnClose;
        btnClose = dialog.findViewById(R.id.btn_notNow);
        btnClose.setOnClickListener(v->{
            dialog.dismiss();
        });

    }

    private void getPopUpWishes() {
        LocalTime time = LocalTime.now(); // Get the current time
        int hour = time.getHour(); // Get the hour from the current time


        if (hour >= 6 && hour < 12) {
            message ="Good morning!";
        } else if (hour >= 12 && hour < 18) {
            message = "Good afternoon!";
        } else {
            message = "Good evening!";
        }


        wishes.show();
        TextView name, wish;
        name = wishes.findViewById(R.id.oops_text);
        wish = wishes.findViewById(R.id.network_issue_body);


        name.setText("Hi, "+preferenceManager.getString(Constants.NAME));
        wish.setText(message);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                wishes.dismiss();
            }
        }, 3000);


    }

    private void bottomMenu() {

        binding.bottomNavMenu.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i) {
                    case R.id.nav_dashboard:
                        fragment = new DashboardFragment();
                        break;
                    case R.id.nav_chat:
                        fragment = new ChatFragment();
                        break;
                    case R.id.nav_more:
                        fragment = new MoreFragment();

                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
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

    public void init() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.check_app_update);
        wishes = new Dialog(this);
        wishes.setContentView(R.layout.good_status_pop_up);
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading");
        preferenceManager = new PreferenceManager(this);
        slide_up = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        binding.relativeBottomLayout.setAnimation(slide_up);
        ColorOfStatusAndNavBar colorOfStatusAndNavBar = new ColorOfStatusAndNavBar();
        colorOfStatusAndNavBar.dashboard(this);
    }

    @Override
    public void onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            getSupportFragmentManager().beginTransaction().replace(binding.fragmentContainer.getId(), new DashboardFragment()).commit();
            binding.bottomNavMenu.setItemSelected(R.id.nav_dashboard, true);
            Toast.makeText(this, "Press back again to quit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }

    public void getNotificationCount() {

        Integer userId = preferenceManager.getInt(Constants.USER_ID);
        Integer count = preferenceManager.getInt(Constants.COUNT);
        ApiInterface apiInterface = RetroFitClient.getRetrofit().create(ApiInterface.class);
        DashboardNotificationModel dashboardNotificationModel = new DashboardNotificationModel(userId);
        Call<DashboardNotificationModel> call = apiInterface.dashboardNotificationModel(dashboardNotificationModel);
        call.enqueue(new Callback<DashboardNotificationModel>() {
            @Override
            public void onResponse(Call<DashboardNotificationModel> call, Response<DashboardNotificationModel> response) {
                progressDialog.dismiss();
                if (response.body().getNotificationCount().intValue() == count) {

                } else {
                    changeImage(response.body().getNotificationCount());
                }
            }

            @Override
            public void onFailure(Call<DashboardNotificationModel> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }


    public void updation(){
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
            // versionCode = String.valueOf(pInfo.versionCode);
            Log.d("App Version", "Version Name: " + version + " Version Code: " + versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        AppUpdationCheckModel.Authentication authentication = new AppUpdationCheckModel.Authentication("r5HmaHsWDXPLCX5Zfog9",String.valueOf(preferenceManager.getInt(Constants.USER_ID)),"");
        AppUpdationCheckModel.RequestData requestData = new AppUpdationCheckModel.RequestData(version);
        AppUpdationCheckModel app = new AppUpdationCheckModel(authentication, requestData);
        ApiInterface apiInterface = RetroFitClient.getRetrofit().create(ApiInterface.class);
        Call<AppUpdationCheckModel> call = apiInterface.appUpdation(app);
        call.enqueue(new Callback<AppUpdationCheckModel>() {
            @Override
            public void onResponse(Call<AppUpdationCheckModel> call, Response<AppUpdationCheckModel> response) {
                Log.d("IN_RESPONSE" , "RESPONSE");
                String status  =  response.body().getStatusResponse().getStatus();
                if (status.equals("SUCCESS")){
                    Log.d("GETTING_RESPONSE" , "SUCCESS");
                    if (response.body().getAppversionUpdateResponse().getVersionUpdateStatus().equals("Y")){
                        getPopUp();
                        Log.d("UPDATE_RESPONSE" , response.body().getAppversionUpdateResponse().getVersionUpdateStatus());
                    }
                } else if (status.equals("FAILED")){

                }

            }

            @Override
            public void onFailure(Call<AppUpdationCheckModel> call, Throwable t) {
                Log.d("RESPONSE_FAILED" , "FAILED");
            }
        });
    }

    private void changeImage(Integer i) {
        preferenceManager.putInt(Constants.COUNT, i);
        binding.notificationBell.setImageResource(R.drawable.bellnotify);
    }
}