package com.isapp.isstudentapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.isapp.isstudentapp.common.ColorOfStatusAndNavBar;
import com.isapp.isstudentapp.constant.Constants;
import com.isapp.isstudentapp.fragment.ChatFragment;
import com.isapp.isstudentapp.fragment.DashboardFragment;
import com.isapp.isstudentapp.R;
import com.isapp.isstudentapp.databinding.ActivityDashboardBinding;
import com.isapp.isstudentapp.fragment.MoreFragment;
import com.isapp.isstudentapp.model.DashboardNotificationModel;
import com.isapp.isstudentapp.network.NetworkChangeListener;
import com.isapp.isstudentapp.preference.PreferenceManager;
import com.isapp.isstudentapp.retrofit.ApiInterface;
import com.isapp.isstudentapp.retrofit.RetroFitClient;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;

    ProgressDialog progressDialog;
    PreferenceManager preferenceManager;
    Animation slide_up;
    long pressedTime;
    NetworkChangeListener networkChangeListner = new NetworkChangeListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        binding.notificationBell.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, NotificationActivity.class);
            startActivity(intent);
        });
        binding.bottomNavMenu.setItemSelected(R.id.nav_dashboard, true);
        getNotificationCount();
        getSupportFragmentManager().beginTransaction().replace(binding.fragmentContainer.getId(), new DashboardFragment()).commit();
        bottomMenu();

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

    private void changeImage(Integer i) {
        preferenceManager.putInt(Constants.COUNT, i);
        binding.notificationBell.setImageResource(R.drawable.bellnotify);
    }
}