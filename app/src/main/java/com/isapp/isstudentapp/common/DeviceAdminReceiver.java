package com.isapp.isstudentapp.common;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.isapp.isstudentapp.activity.SplashActivity;
import com.isapp.isstudentapp.constant.Constants;
import com.isapp.isstudentapp.preference.PreferenceManager;

public class DeviceAdminReceiver extends android.app.admin.DeviceAdminReceiver {
    PreferenceManager preferenceManager;

    @Override
    public void onEnabled(Context context, Intent intent) {
        preferenceManager = new PreferenceManager(context);
        LogoutDone logoutDone = new LogoutDone();
        int userId = preferenceManager.getInt(Constants.USER_ID);
        String usermail = preferenceManager.getString(Constants.USER_EMAIL);
        String firebaseToken = preferenceManager.getString(Constants.FIREBASE_TOKEN);
        Integer platformId = Integer.valueOf(preferenceManager.getInt(Constants.PLATFORM_ID));
        if (logoutDone.logout(platformId, userId, usermail)) {
            preferenceManager.clear();
            preferenceManager.putString(Constants.FIREBASE_TOKEN, firebaseToken);
            intent = new Intent(context.getApplicationContext(), SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        } else {
            Toast.makeText(context.getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDisabled(Context context, Intent intent) {

    }

}
