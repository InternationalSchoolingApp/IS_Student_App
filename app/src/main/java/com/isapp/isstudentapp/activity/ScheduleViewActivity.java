package com.isapp.isstudentapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.isapp.isstudentapp.common.ColorOfStatusAndNavBar;
import com.isapp.isstudentapp.databinding.ActivityScheduleViewBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ScheduleViewActivity extends AppCompatActivity {

    private ActivityScheduleViewBinding binding;
    String title, startDate, endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityScheduleViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ColorOfStatusAndNavBar colorOfStatusAndNavBar = new ColorOfStatusAndNavBar();
        colorOfStatusAndNavBar.colorOfStatusBar(this);

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            title = extra.getString("title");
            startDate = extra.getString("startDate");
            endDate = extra.getString("endDate");
        }
        Date date ;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Toast.makeText(this, date.toString(), Toast.LENGTH_SHORT).show();
        binding.title.setText(title);
        binding.startDate.setText(startDate);
        binding.endDate.setText(endDate);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        binding.teacherProfileBackButton.setOnClickListener(v->onBackPressed());
        binding.buttonAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ScheduleViewActivity.this, "Working On This", Toast.LENGTH_SHORT).show();
            }
        });
    }


}