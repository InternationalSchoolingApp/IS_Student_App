package com.isapp.isstudentapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.isapp.isstudentapp.common.ColorOfStatusAndNavBar;
import com.isapp.isstudentapp.constant.Constants;
import com.isapp.isstudentapp.databinding.ActivityPerformanceByGradeBinding;
import com.isapp.isstudentapp.adapter.ActivityListPerformanceAdapter;
import com.isapp.isstudentapp.model.SubjectPerformanceDetail;
import com.isapp.isstudentapp.network.NetworkChangeListener;
import com.isapp.isstudentapp.preference.PreferenceManager;
import com.isapp.isstudentapp.retrofit.ApiInterface;
import com.isapp.isstudentapp.retrofit.RetroFitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerformanceByGradeActivity extends AppCompatActivity {

    private ActivityPerformanceByGradeBinding binding;

    ProgressDialog progressDialog;
    NetworkChangeListener networkChangeListner = new NetworkChangeListener();
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPerformanceByGradeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();

        Bundle extra = getIntent().getExtras();
        Integer userId = preferenceManager.getInt(Constants.USER_ID);
        String courseId = "";
        if (extra != null) {
            courseId = extra.getString("courseId");
        }
        apiCall(userId, courseId);

    }

    public void init(){
        progressDialog = new ProgressDialog(this);
        preferenceManager = new PreferenceManager(this);
        binding.activityListPerformance.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        binding.activityListPerformance.setLayoutManager(llm);
        ColorOfStatusAndNavBar colorOfStatusAndNavBar = new ColorOfStatusAndNavBar();
        colorOfStatusAndNavBar.loginAndForgetPassword(this);
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


    private void apiCall(Integer userId, String courseId) {
        ApiInterface apiInterface = RetroFitClient.getRetrofit().create(ApiInterface.class);
        progressDialog.show();
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);

        SubjectPerformanceDetail subjectPerformanceDetail = new SubjectPerformanceDetail(courseId, userId);
        Call<SubjectPerformanceDetail> call = apiInterface.progressBySubject(subjectPerformanceDetail);
        call.enqueue(new Callback<SubjectPerformanceDetail>() {
            @Override
            public void onResponse(Call<SubjectPerformanceDetail> call, Response<SubjectPerformanceDetail> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    binding.reltivePerformBySubject.setVisibility(View.VISIBLE);

                    try {
                        binding.totalAssignments.setText("Total Assignments : " + response.body().getTotalAssignment().intValue());
                        binding.submitted.setText("Submitted : " + response.body().getSubmiteAssign().intValue());
                        binding.upcoming.setText("Upcoming Assignments : " + response.body().getUpcomingAssign().intValue());
                        binding.pending.setText("Pending Assignments : " + response.body().getPendingAssign().intValue());
                        binding.passed.setText("Passed Assignments : " + response.body().getPassesAssign().intValue());
                        binding.failed.setText("Failed Assignments : " + response.body().getFailedAssign().intValue());
                        binding.submittedBeforeTime.setText("Submitted Before Time : " + response.body().getSubmitBeforeTimeAssign().intValue());
                        binding.submittedOnTime.setText("Submitted On Time : " + response.body().getSubmitOntimeAssign().intValue());
                        binding.submittedLate.setText("Submitted Late : " + response.body().getSubmitLateAssign().intValue());
                        binding.nameOfSubjectPerformance.setText("Subject :\n" + response.body().getResponse().getEnrollments().getEnrollment().get(0).getEntity().getTitle());
                        binding.currentOverallScore.setText("Overall Score : " + response.body().getResponse().getEnrollments().getEnrollment().get(0).getGrades().getAchieved());
                        binding.overallGrade.setText("Overall Grade : " + response.body().getResponse().getEnrollments().getEnrollment().get(0).getGrades().getLetter());
                        binding.dateView.setText("Duration : " + response.body().getResponse().getEnrollments().getEnrollment().get(0).getStartdate() + "-" + response.body().getResponse().getEnrollments().getEnrollment().get(0).getEnddate());
                        binding.gradableActivity.setText("Gradable Activity : " + response.body().getResponse().getEnrollments().getEnrollment().get(0).getGrades().getGradable() + "/" + response.body().getResponse().getEnrollments().getEnrollment().get(0).getGrades().getCompletedgradable());
                        binding.totalActivity.setText("All Activity : " + response.body().getResponse().getEnrollments().getEnrollment().get(0).getGrades().getCompletable() + "/" + response.body().getResponse().getEnrollments().getEnrollment().get(0).getGrades().getCompletable());
                        List<SubjectPerformanceDetail.Response.Enrollments.Enrollment.Grades.Items.Item> passingList = response.body().getResponse().getEnrollments().getEnrollment().get(0).getGrades().getItems().getItem();
                        ActivityListPerformanceAdapter activityListPerformanceAdapter = new ActivityListPerformanceAdapter(passingList);
                        binding.activityListPerformance.setAdapter(activityListPerformanceAdapter);

                    } catch (Exception e) {
                        Log.d("Error", "onResponse: " + e);
                    }
                }

            }

            @Override
            public void onFailure(Call<SubjectPerformanceDetail> call, Throwable t) {
                progressDialog.dismiss();

            }
        });
    }


}