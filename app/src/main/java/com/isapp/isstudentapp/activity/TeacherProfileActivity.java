package com.isapp.isstudentapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.isapp.isstudentapp.common.ColorOfStatusAndNavBar;
import com.isapp.isstudentapp.databinding.ActivityTeacherProfileBinding;
import com.isapp.isstudentapp.model.TeacherInfoModel;
import com.isapp.isstudentapp.network.NetworkChangeListener;
import com.isapp.isstudentapp.retrofit.ApiInterface;
import com.isapp.isstudentapp.retrofit.RetroFitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherProfileActivity extends AppCompatActivity {

    private ActivityTeacherProfileBinding binding;

    ProgressDialog progressDialog;

    String teacherId, teacherName, courseName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ColorOfStatusAndNavBar colorOfStatusAndNavBar = new ColorOfStatusAndNavBar();
        colorOfStatusAndNavBar.loginAndForgetPassword(this);

        binding = ActivityTeacherProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setTitle("Loading");
        progressDialog.setCancelable(false);
        binding.teacherProfileBackButton.setOnClickListener(v->onBackPressed());

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            teacherId = extra.getString("teacherId");
            teacherName = extra.getString("teacherName");
            courseName = extra.getString("subjectName");
        }

        binding.teacherNameInfo.setText(teacherName);
        binding.teacherSubjectTv.setText(courseName);

        ApiInterface apiInterface = RetroFitClient.getRetrofit().create(ApiInterface.class);
        TeacherInfoModel teacherInfoModel = new TeacherInfoModel(Integer.parseInt(teacherId));
        Call<TeacherInfoModel> call = apiInterface.teacherInfoModel(teacherInfoModel);
        call.enqueue(new Callback<TeacherInfoModel>() {
            @Override
            public void onResponse(Call<TeacherInfoModel> call, Response<TeacherInfoModel> response) {
                if (response.body().getStatus().equals("success")){
                    String url = response.body().getMessage();
                    url.replace("sch/", "sch/thumb_");
                    Glide.with(TeacherProfileActivity.this).load(url).into(binding.teacherProfileImage);
                    progressDialog.dismiss();
                }else{
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<TeacherInfoModel> call, Throwable t) {
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

    NetworkChangeListener networkChangeListner = new NetworkChangeListener();

}