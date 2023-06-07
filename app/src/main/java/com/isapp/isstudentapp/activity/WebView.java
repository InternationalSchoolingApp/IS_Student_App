package com.isapp.isstudentapp.activity;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.isapp.isstudentapp.common.ColorOfStatusAndNavBar;
import com.isapp.isstudentapp.databinding.ActivityWebViewBinding;
import com.isapp.isstudentapp.R;


public class WebView extends AppCompatActivity {

    private ActivityWebViewBinding binding;
    ProgressDialog progressDialog;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        init();
        setListeners();

        binding.webview.getSettings().setJavaScriptEnabled(true);
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            url = extra.getString("url");
        }
        binding.webview.loadUrl(url);

        binding.webview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(android.webkit.WebView view, String url) {
                super.onPageFinished(view, url);
                progressDialog.dismiss();
                binding.webview.setVisibility(View.VISIBLE);
            }
        });




    }

    public void setListeners(){
        binding.backButton.setOnClickListener(v->{
            onBackPressed();
            finish();
        });
    }


    public void init(){
        binding = ActivityWebViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ColorOfStatusAndNavBar colorOfStatusAndNavBar = new ColorOfStatusAndNavBar();
        colorOfStatusAndNavBar.colorOfStatusBar(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setCancelable(false);


    }

   }
