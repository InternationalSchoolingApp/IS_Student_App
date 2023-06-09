package com.isapp.isstudentapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.isapp.isstudentapp.common.ColorOfStatusAndNavBar;
import com.isapp.isstudentapp.constant.Constants;
import com.isapp.isstudentapp.databinding.ActivitySplashBinding;
import com.isapp.isstudentapp.preference.PreferenceManager;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;
    private int SPLASH_TIME = 3000;
    PreferenceManager preferenceManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ColorOfStatusAndNavBar colorOfStatusAndNavBar = new ColorOfStatusAndNavBar();
        colorOfStatusAndNavBar.colorOfStatusBar(this);
        super.onCreate(savedInstanceState);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        boolean areNotificationsEnabled = notificationManager.areNotificationsEnabled();
        if (!areNotificationsEnabled) {
            SPLASH_TIME = 15000;
            Intent intent = new Intent();
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("android.provider.extra.APP_PACKAGE", getPackageName());
            startActivity(intent);
            finish();
        }

        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                boolean check = preferenceManager.getBoolean(Constants.USER_LOGGED);

                Intent intent;
                if (check) {
                    intent = new Intent(SplashActivity.this, DashboardActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME);


    }

}