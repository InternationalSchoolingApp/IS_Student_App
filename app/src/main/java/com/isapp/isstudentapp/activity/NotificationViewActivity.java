package com.isapp.isstudentapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.isapp.isstudentapp.common.ColorOfStatusAndNavBar;
import com.isapp.isstudentapp.databinding.ActivityNotificationViewBinding;
import com.isapp.isstudentapp.model.NotificationLog;
import com.isapp.isstudentapp.retrofit.ApiInterface;
import com.isapp.isstudentapp.retrofit.RetroFitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotificationViewActivity extends AppCompatActivity {

    private ActivityNotificationViewBinding binding;
    String title, message, time;

    Integer id, userId;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNotificationViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ColorOfStatusAndNavBar colorOfStatusAndNavBar = new ColorOfStatusAndNavBar();
        colorOfStatusAndNavBar.colorOfStatusBar(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            title = extra.getString("title");
            message = extra.getString("message");
            time = extra.getString("time");
            id = extra.getInt("id");
            userId = extra.getInt("userId");
        }

        binding.title.setText(title);
        binding.message.setText(message);
        binding.time.setText(time);

        ApiInterface apiInterface = RetroFitClient.getRetrofit().create(ApiInterface.class);
        NotificationLog notificationLog = new NotificationLog(userId, id);

        Call<NotificationLog> call = apiInterface.getUpdateNotification(notificationLog);
        call.enqueue(new Callback<NotificationLog>() {
            @Override
            public void onResponse(Call<NotificationLog> call, Response<NotificationLog> response) {
                if (response.isSuccessful()){
                    if (response.body().getStatus().equals("success")){
                        progressDialog.dismiss();
                    }else{
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationLog> call, Throwable t) {
                progressDialog.dismiss();
            }
        });







        binding.teacherProfileBackButton.setOnClickListener(v->onBackPressed());

    }

}