package com.isapp.isstudentapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

import com.isapp.isstudentapp.common.ColorOfStatusAndNavBar;
import com.isapp.isstudentapp.databinding.ActivityNotesViewBinding;
import com.isapp.isstudentapp.model.DeleteNotes;
import com.isapp.isstudentapp.model.GetNotes;
import com.isapp.isstudentapp.network.NetworkChangeListener;
import com.isapp.isstudentapp.retrofit.ApiInterface;
import com.isapp.isstudentapp.retrofit.RetroFitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotesViewActivity extends AppCompatActivity {

    private ActivityNotesViewBinding binding;
    String title, message, time, id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNotesViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ColorOfStatusAndNavBar colorOfStatusAndNavBar = new ColorOfStatusAndNavBar();
        colorOfStatusAndNavBar.colorOfStatusBar(this);

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            title = extra.getString("title");
            message = extra.getString("message");
            time = extra.getString("time");
            id = extra.getString("id");
        }

        binding.title.setText(title);
        binding.message.setText(message);
        binding.time.setText(time);


        binding.teacherProfileBackButton.setOnClickListener(v->onBackPressed());

        ApiInterface apiInterface = RetroFitClient.getRetrofit().create(ApiInterface.class);

        DeleteNotes deleteNotes = new DeleteNotes(Integer.valueOf(id));
        Call<DeleteNotes> call = apiInterface.deleteNotes(deleteNotes);

        binding.delete.setOnClickListener(v->{
            call.enqueue(new Callback<DeleteNotes>() {
                @Override
                public void onResponse(Call<DeleteNotes> call, Response<DeleteNotes> response) {
                if (response.body().getStatus().equals("success")){
                    Toast.makeText(NotesViewActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(v.getContext(), NotesActivity.class);
                    startActivity(intent);
                    finish();
                }
                }

                @Override
                public void onFailure(Call<DeleteNotes> call, Throwable t) {
                    Toast.makeText(NotesViewActivity.this, "Technical Glitch", Toast.LENGTH_SHORT).show();
                }
            });

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