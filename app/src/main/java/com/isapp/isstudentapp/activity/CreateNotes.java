package com.isapp.isstudentapp.activity;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

import com.isapp.isstudentapp.common.ColorOfStatusAndNavBar;
import com.isapp.isstudentapp.constant.Constants;
import com.isapp.isstudentapp.databinding.ActivityCreateNotesBinding;
import com.isapp.isstudentapp.model.CreateMyNotes;
import com.isapp.isstudentapp.network.NetworkChangeListener;
import com.isapp.isstudentapp.preference.PreferenceManager;
import com.isapp.isstudentapp.retrofit.ApiInterface;
import com.isapp.isstudentapp.retrofit.RetroFitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateNotes extends AppCompatActivity {


    private ActivityCreateNotesBinding binding;
    ProgressDialog progressDialog;
    PreferenceManager preferenceManager;
    NetworkChangeListener networkChangeListner = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ColorOfStatusAndNavBar color = new ColorOfStatusAndNavBar();
        color.loginAndForgetPassword(this);
        binding = ActivityCreateNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(this);
        progressDialog = new ProgressDialog(this);
        binding.createNoteBtn.setOnClickListener(v -> {
            if (validate()) {
                saving();
                progressDialog.show();
                progressDialog.setCancelable(false);
                binding.createNoteMessage.getEditText().setText(null);
                binding.createNoteTitle.getEditText().setText(null);
            }
        });

        binding.backButtonCreateNotes.setOnClickListener(v-> onBackPressed());

    }

    private void saving() {
        String title = binding.createNoteTitle.getEditText().getText().toString();
        String message = binding.createNoteMessage.getEditText().getText().toString();

        Integer userId = preferenceManager.getInt(Constants.USER_ID);

        CreateMyNotes createMyNotes = new CreateMyNotes(message, userId, title);
        ApiInterface apiInterface = RetroFitClient.getRetrofit().create(ApiInterface.class);
        Call<CreateMyNotes> call = apiInterface.createMyNotes(createMyNotes);
        call.enqueue(new Callback<CreateMyNotes>() {
            @Override
            public void onResponse(Call<CreateMyNotes> call, Response<CreateMyNotes> response) {
                progressDialog.dismiss();
                if (response.body().getStatus().equals("success")) {
                    Toast.makeText(CreateNotes.this, "Note Saved Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CreateNotes.this, "OOPS! Error Occured", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreateMyNotes> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CreateNotes.this, "OOPS! Error Occured", Toast.LENGTH_SHORT).show();
            }
        });


    }

    boolean validate() {
        if (binding.createNoteTitle.getEditText().getText().toString().equals("") || binding.createNoteMessage.getEditText().getText().toString().equals("")) {
            if (binding.createNoteTitle.getEditText().getText().toString().equals("")) {
                binding.createNoteTitle.getEditText().setError("Title Field Empty");
            } else if (binding.createNoteMessage.getEditText().getText().toString().equals("")) {
                binding.createNoteMessage.getEditText().setError("Message Field Empty");
            }
        } else {
            return true;
        }
        return false;
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