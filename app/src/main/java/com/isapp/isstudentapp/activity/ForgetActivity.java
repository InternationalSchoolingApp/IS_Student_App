package com.isapp.isstudentapp.activity;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.isapp.isstudentapp.R;
import com.isapp.isstudentapp.common.ColorOfStatusAndNavBar;
import com.isapp.isstudentapp.databinding.ActivityForgetBinding;
import com.isapp.isstudentapp.model.ForgetPasswordModel;
import com.isapp.isstudentapp.network.NetworkChangeListener;
import com.isapp.isstudentapp.retrofit.ApiInterface;
import com.isapp.isstudentapp.retrofit.RetroFitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ForgetActivity extends AppCompatActivity {

    private ActivityForgetBinding binding;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private Dialog dialog;
    private Animation slide_up;
    private String email;
    private ProgressDialog progressDialog;
    NetworkChangeListener networkChangeListner = new NetworkChangeListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityForgetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        listener();
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            email = extra.getString("email");
            binding.forgetEmailTv.getEditText().setInputType(InputType.TYPE_CLASS_TEXT);
            binding.forgetEmailTv.getEditText().setText(email);
        }
    }

    private void listener() {
        binding.forgetPageBtn.setOnClickListener(v -> {
            String username = binding.forgetEmailTv.getEditText().getText().toString().trim();

            if (username.isEmpty()) {
                binding.forgetEmailTv.setError("Email field is empty");
                binding.forgetEmailTv.setHelperText("Email field is empty");
            } else if (!username.matches(emailPattern)) {
                binding.forgetEmailTv.setHelperText("");
                binding.forgetEmailTv.setError("Invalid Mail");
            } else {
                binding.forgetEmailTv.setHelperText("");
                binding.forgetEmailTv.setError("");
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                forget(username);
            }
        });
    }


    private void forget(String username) {


        ApiInterface apiInterface = RetroFitClient.getRetrofit().create(ApiInterface.class);
        final ForgetPasswordModel forgetPasswordModel = new ForgetPasswordModel(username);
        Call<ForgetPasswordModel> call = apiInterface.forgetPassword(forgetPasswordModel);
        call.enqueue(new Callback<ForgetPasswordModel>() {
            @Override
            public void onResponse(Call<ForgetPasswordModel> call, Response<ForgetPasswordModel> response) {
                progressDialog.dismiss();
                String statusValue = response.body().getStatusValue();
                String status = response.body().getStatus();
                getStatus(status, statusValue);
            }

            @Override
            public void onFailure(Call<ForgetPasswordModel> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }

    private void getStatus(String status, String statusValue) {
        dialog.setContentView(R.layout.pop_up_dialog);
        dialog.show();
        TextView textViewTitle, textViewMessage;
        ImageView imageView;

        textViewTitle = dialog.findViewById(R.id.popup_textView);
        textViewMessage = dialog.findViewById(R.id.popup_message);
        imageView = dialog.findViewById(R.id.close_popup);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        if (status.equals("success")) {
            textViewTitle.setText("Got It ");
            textViewMessage.setText("Check your mail, you will get a reset link shortly");
        } else if (status.equals("failed")) {
            if (statusValue.equals("userNotExist")) {
                textViewTitle.setText("Not Exist");
                textViewMessage.setText("The user you are trying to access is not Registered");
            } else if (statusValue.equals("notEnabled")) {
                textViewTitle.setText("Sorry");
                textViewMessage.setText("User Not Enabled");
            }
        } else {
            textViewTitle.setText("Technical Glitch");
            textViewMessage.setText("Sorry," + "/n there is a technical Glitch");
        }

    }

    public void init() {
        ColorOfStatusAndNavBar colorOfStatusAndNavBar = new ColorOfStatusAndNavBar();
        colorOfStatusAndNavBar.loginAndForgetPassword(this);
        dialog = new Dialog(this);
        slide_up = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        progressDialog = new ProgressDialog(this);
        binding.scrollForgetLayout.setAnimation(slide_up);
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


}