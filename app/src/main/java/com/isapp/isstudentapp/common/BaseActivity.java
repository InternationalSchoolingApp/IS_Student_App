package com.isapp.isstudentapp.common;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.isapp.isstudentapp.constant.Constants;
import com.isapp.isstudentapp.preference.PreferenceManager;

public class BaseActivity extends AppCompatActivity {

    DocumentReference documentReference;
    PreferenceManager preferenceManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceManager = new PreferenceManager(this);
        String email = preferenceManager.getString(Constants.USER_EMAIL).toLowerCase();
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        documentReference = database.collection(Constants.FIREBASE_USER_DB).document(email);
    }


    @Override
    protected void onPause() {
        super.onPause();
        documentReference.update(Constants.KEY_AVAILABLITY,0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        documentReference.update(Constants.KEY_AVAILABLITY,1);
    }
}
