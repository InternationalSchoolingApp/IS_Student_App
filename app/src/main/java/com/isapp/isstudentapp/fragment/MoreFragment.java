package com.isapp.isstudentapp.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.isapp.isstudentapp.activity.ProfileActivity;
import com.isapp.isstudentapp.R;
import com.isapp.isstudentapp.activity.LoginActivity;
import com.isapp.isstudentapp.activity.NotesActivity;
import com.isapp.isstudentapp.activity.PerformanceActivity;
import com.isapp.isstudentapp.activity.SplashActivity;
import com.isapp.isstudentapp.common.LogoutDone;
import com.isapp.isstudentapp.constant.Constants;
import com.isapp.isstudentapp.model.UserProfile;
import com.isapp.isstudentapp.preference.PreferenceManager;
import com.isapp.isstudentapp.retrofit.ApiInterface;
import com.isapp.isstudentapp.retrofit.RetroFitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoreFragment extends Fragment {
    ProgressDialog progressDialog;
    Integer userId;
PreferenceManager preferenceManager ;
    ImageView imageView;
    TextView name, grade;
    Button button_log, btn_perf, btn_notes, btn_view_profile;
    String firebaseToken;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        progressDialog = new ProgressDialog(getActivity());
        preferenceManager = new PreferenceManager(view.getContext());
        userId = preferenceManager.getInt(Constants.USER_ID);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading");

        // hooks

        imageView = view.findViewById(R.id.image_more);
        name = view.findViewById(R.id.more_name_tv);
        grade =view.findViewById(R.id.more_grade_tv);
        button_log = view.findViewById(R.id.log_out_btn_more);
        btn_perf = view.findViewById(R.id.performance_btn_more);
        btn_notes = view.findViewById(R.id.notes_btn_more);
        btn_view_profile = view.findViewById(R.id.btn_more_profile);
        button_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int userId = preferenceManager.getInt(Constants.USER_ID);
                String usermail = preferenceManager.getString(Constants.USER_EMAIL);
                firebaseToken = preferenceManager.getString(Constants.FIREBASE_TOKEN);
                Integer platformId = Integer.valueOf(preferenceManager.getInt(Constants.PLATFORM_ID));
                LogoutDone logoutDone = new LogoutDone();
                if(logoutDone.logout(platformId,userId,usermail)){
                    preferenceManager.clear();
                    preferenceManager.putString(Constants.FIREBASE_TOKEN, firebaseToken);
                    Intent intent = new Intent(getActivity(), SplashActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    getActivity().finishAffinity();
                }else {
                    Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                }
            }

        });

        btn_view_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        btn_perf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PerformanceActivity.class);
                startActivity(intent);
            }
        });

        btn_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NotesActivity.class);
                startActivity(intent);
            }
        });

        ApiInterface apiInterface = RetroFitClient.getRetrofit().create(ApiInterface.class);
        final UserProfile userProfile = new UserProfile(userId);
        Call<UserProfile> call = apiInterface.profilePostData(userProfile);
        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                progressDialog.dismiss();
                String url = response.body().getPictureLink();
                url.replace("sch/", "sch/thumb_");
                Glide.with(getContext()).load(url).into(imageView);
                name.setText(response.body().getName());
                grade.setText(response.body().getGradeName());
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

        return view;

    }
}