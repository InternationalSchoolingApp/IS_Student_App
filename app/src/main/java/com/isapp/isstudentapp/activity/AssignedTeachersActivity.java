package com.isapp.isstudentapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.isapp.isstudentapp.adapter.TeacherNameChatAdapter;
import com.isapp.isstudentapp.common.ColorOfStatusAndNavBar;
import com.isapp.isstudentapp.constant.Constants;
import com.isapp.isstudentapp.databinding.ActivityAssignedTeachersBinding;
import com.isapp.isstudentapp.model.AssignTeacherModel;
import com.isapp.isstudentapp.network.NetworkChangeListener;
import com.isapp.isstudentapp.preference.PreferenceManager;
import com.isapp.isstudentapp.retrofit.ApiInterface;
import com.isapp.isstudentapp.retrofit.RetroFitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignedTeachersActivity extends AppCompatActivity {

    private ActivityAssignedTeachersBinding binding;
    PreferenceManager preferenceManager;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAssignedTeachersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ColorOfStatusAndNavBar colorOfStatusAndNavBar = new ColorOfStatusAndNavBar();
        colorOfStatusAndNavBar.colorOfStatusBar(this);
        binding.teacherRecyclerview.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL, false);
        binding.teacherRecyclerview.setLayoutManager(llm);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();

        binding.assignTeacherBackButton.setOnClickListener(v->onBackPressed());

        ApiInterface apiInterface = RetroFitClient.getRetrofit().create(ApiInterface.class);
        preferenceManager  = new PreferenceManager(this);
        AssignTeacherModel assignTeacherModel = new AssignTeacherModel(preferenceManager.getInt(Constants.SSID));
        Call<AssignTeacherModel> call = apiInterface.assignTeacherChat(assignTeacherModel);
        call.enqueue(new Callback<AssignTeacherModel>() {
            @Override
            public void onResponse(Call<AssignTeacherModel> call, Response<AssignTeacherModel> response) {
                progressDialog.dismiss();
                if (response.body().getStatus().equals("success")){
                    List<AssignTeacherModel.AssignedTeacher> list = response.body().getAssignedTeachers();
                    TeacherNameChatAdapter teacherNameChatAdapter = new TeacherNameChatAdapter(list);
                    binding.teacherRecyclerview.setAdapter(teacherNameChatAdapter);
                }else if(response.body().getStatus().equals("failed")){
                    progressDialog.dismiss();
                    Toast.makeText(AssignedTeachersActivity.this, "No Teacher Assign", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AssignTeacherModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(AssignedTeachersActivity.this, "Technical Error", Toast.LENGTH_SHORT).show();
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