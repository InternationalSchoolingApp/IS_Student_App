package com.isapp.isstudentapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.isapp.isstudentapp.common.ColorOfStatusAndNavBar;
import com.isapp.isstudentapp.databinding.ActivityNotesViewBinding;

public class NotesViewActivity extends AppCompatActivity {

    private ActivityNotesViewBinding binding;
    String title, message, time;

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
        }

        binding.title.setText(title);
        binding.message.setText(message);
        binding.time.setText(time);

        binding.teacherProfileBackButton.setOnClickListener(v->onBackPressed());
    }

}