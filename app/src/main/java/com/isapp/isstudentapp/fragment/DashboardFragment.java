package com.isapp.isstudentapp.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.isapp.isstudentapp.activity.DashboardActivity;
import com.isapp.isstudentapp.activity.LoginActivity;
import com.isapp.isstudentapp.activity.ProfileActivity;
import com.isapp.isstudentapp.R;
import com.isapp.isstudentapp.activity.NotesActivity;
import com.isapp.isstudentapp.activity.PerformanceActivity;
import com.isapp.isstudentapp.activity.ScheduleActivity;
import com.isapp.isstudentapp.activity.SplashActivity;
import com.isapp.isstudentapp.constant.Constants;
import com.isapp.isstudentapp.model.DashBoardModel;
import com.isapp.isstudentapp.preference.PreferenceManager;
import com.isapp.isstudentapp.retrofit.ApiInterface;
import com.isapp.isstudentapp.retrofit.RetroFitClient;

import java.time.LocalTime;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashboardFragment extends Fragment {

    ProgressDialog progressDialog;
    PreferenceManager preferenceManager;
    TextView name, grade, program, city ;
    LinearLayout ll_profile;
    RelativeLayout performance, myNotes, schedule ;

    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ll_profile = view.findViewById(R.id.ll_profile);
        ll_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.show();
        progressDialog.setTitle("Loading");
        preferenceManager = new PreferenceManager(getContext());
        name = view.findViewById(R.id.tv_name);
        grade = view.findViewById(R.id.tv_grade);
        program = view.findViewById(R.id.your_program_text);
        myNotes = view.findViewById(R.id.dashboard_notes);
        city = view.findViewById(R.id.tv_city);









        myNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NotesActivity.class);
                startActivity(intent);
            }
        });
        schedule = view.findViewById(R.id.schedule);

        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ScheduleActivity.class);
                startActivity(intent);
            }
        });
        performance = view.findViewById(R.id.performance_rl);
        performance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PerformanceActivity.class);
                startActivity(intent);

            }
        });
        ApiInterface apiInterface = RetroFitClient.getRetrofit().create(ApiInterface.class);
        final DashBoardModel dashBoardModel = new DashBoardModel(preferenceManager.getInt(Constants.USER_ID));
        Call<DashBoardModel> call = apiInterface.dashboardPostData(dashBoardModel);
        call.enqueue(new Callback<DashBoardModel>() {
            @Override
            public void onResponse(Call<DashBoardModel> call, Response<DashBoardModel> response) {
                progressDialog.dismiss();
                if (response.body().getStatus().equals("success")) {
                    String regTypeName = response.body().getRegType().replace("_", " ");
                    name.setText("Hi, " + response.body().getfName());
                    grade.setText(response.body().getGradeName());
                    city.setText("Location : "+response.body().getCity() +" | "+response.body().getCountry());
                    program.setText("Enrollment Type : " +regTypeName);




                } else {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<DashBoardModel> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

        return view;

    }
}