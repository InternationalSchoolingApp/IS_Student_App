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
import com.isapp.isstudentapp.databinding.ActivityPerformanceBinding;
import com.isapp.isstudentapp.model.SubjectListPerformance;
import com.isapp.isstudentapp.adapter.SubjectListPerformanceAdapter;
import com.isapp.isstudentapp.network.NetworkChangeListener;
import com.isapp.isstudentapp.preference.PreferenceManager;
import com.isapp.isstudentapp.retrofit.ApiInterface;
import com.isapp.isstudentapp.retrofit.RetroFitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerformanceActivity extends AppCompatActivity {

    private ActivityPerformanceBinding binding;
    ProgressDialog progressDialog ;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ColorOfStatusAndNavBar colorOfStatusAndNavBar  = new ColorOfStatusAndNavBar();
        colorOfStatusAndNavBar.colorOfStatusBar(this);

        binding = ActivityPerformanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading");
        progressDialog.show();


        binding.performanceBackButton.setOnClickListener(v->onBackPressed());
        binding.subjectListPerformance.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        binding.subjectListPerformance.setLayoutManager(llm);
        preferenceManager = new PreferenceManager(this);
        listing();




    }

    private void listing() {
        ApiInterface apiInterface = RetroFitClient.getRetrofit().create(ApiInterface.class);
        SubjectListPerformance subjectListPerformance = new SubjectListPerformance(preferenceManager.getInt(Constants.USER_ID));
        Call<SubjectListPerformance> call = apiInterface.progressSubjectList(subjectListPerformance);
        call.enqueue(new Callback<SubjectListPerformance>() {
            @Override
            public void onResponse(Call<SubjectListPerformance> call, Response<SubjectListPerformance> response) {
                progressDialog.dismiss();
                if (response.body().getCode().equals("SUCCESS")) {
                    List<SubjectListPerformance.DomainStudents.DomainStudent> domainStudentList = response.body().getDomainStudentsList().get(0).getDomainStudent();
                    if (domainStudentList.isEmpty()) {
                        Toast.makeText(PerformanceActivity.this, "It doesn't have data", Toast.LENGTH_SHORT).show();
                    } else {
                        SubjectListPerformanceAdapter subjectListPerformanceAdapter = new SubjectListPerformanceAdapter(domainStudentList);
                        binding.subjectListPerformance.setAdapter(subjectListPerformanceAdapter);
                    }
                } else {
                    Toast.makeText(PerformanceActivity.this, "Technical Glitch", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SubjectListPerformance> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(PerformanceActivity.this, "Not Working", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

}