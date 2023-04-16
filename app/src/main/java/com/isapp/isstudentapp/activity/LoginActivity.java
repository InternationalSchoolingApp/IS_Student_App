package com.isapp.isstudentapp.activity;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.isapp.isstudentapp.R;
import com.isapp.isstudentapp.common.ColorOfStatusAndNavBar;
import com.isapp.isstudentapp.common.MobileModel;
import com.isapp.isstudentapp.constant.Constants;
import com.isapp.isstudentapp.databinding.ActivityLoginBinding;
import com.isapp.isstudentapp.model.FirebaseTokenModel;
import com.isapp.isstudentapp.model.LoginModel;
import com.isapp.isstudentapp.network.NetworkChangeListener;
import com.isapp.isstudentapp.preference.PreferenceManager;
import com.isapp.isstudentapp.retrofit.ApiInterface;
import com.isapp.isstudentapp.retrofit.RetroFitClient;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    //binding
    private ActivityLoginBinding binding;
    //model
    private String model;
    //progressDialog
    ProgressDialog progressDialog;

    TextView privacy, terms;
    //back
    private long pressedTime;
    //pattern
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    //Animation
    Animation slide_up;
    //dialog
    Dialog dialog;
    //network change listen
    NetworkChangeListener networkChangeListner = new NetworkChangeListener();
    //shared prefrence
    PreferenceManager preferenceManager;
    //Firebase
    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        listeners();
    }

    private void init() {
        ColorOfStatusAndNavBar colorOfStatusAndNavBar = new ColorOfStatusAndNavBar();
        colorOfStatusAndNavBar.loginAndForgetPassword(this);
        privacy = findViewById(R.id.privacy);
        terms = findViewById(R.id.terms_of_use);
        progressDialog = new ProgressDialog(this);
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.pop_up_dialog);
        slide_up = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        binding.scrollLoginLayout.setAnimation(slide_up);
        preferenceManager = new PreferenceManager(this);
        database = FirebaseFirestore.getInstance();
        MobileModel mobileModel = new MobileModel();
        model = mobileModel.getDeviceName();
    }

    private void listeners() {
        binding.forgetBtn.setOnClickListener(v -> {
            if (binding.username.getEditText().getText().toString()!=null){
                Intent intent = new Intent(v.getContext(), ForgetActivity.class);
                intent.putExtra("email", binding.username.getEditText().getText().toString());
                startActivity(intent);
            }else{
                Intent i = new Intent(LoginActivity.this, ForgetActivity.class);
                startActivity(i);
            }
        });
        binding.loginBtn.setOnClickListener(c -> {
            check();
        });
        binding.privacy.setOnClickListener(v->{
            String url = "https://internationalschooling.org/privacy-policy/";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });

        binding.termsOfUse.setOnClickListener(v->{
            String url = "https://internationalschooling.org/terms-of-use/";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });
    }

    private void check() {

        String username = binding.username.getEditText().getText().toString().trim();
        String password = binding.password.getEditText().getText().toString().trim();

        if (username.isEmpty()) {
            binding.username.setError("Email field is empty");
            binding.username.setHelperText("Email field is empty");
        } else if (password.isEmpty()) {
            binding.password.setError("Password is empty");
            binding.password.setHelperText("Password field is empty");
        } else if (!username.matches(emailPattern)) {
            binding.username.setHelperText("");
            binding.username.setError("Invalid Mail");
        } else {
            binding.username.setHelperText("");
            binding.username.setError("");
            binding.password.setHelperText("");
            binding.password.setError("");
            finalDone();
        }

    }


    public void finalDone() {

        progressDialog.show();
        progressDialog.setTitle("Loading");
        progressDialog.setCancelable(false);

        String email = binding.username.getEditText().getText().toString().toLowerCase().trim().toLowerCase();
        String password = binding.password.getEditText().getText().toString().trim();

        ApiInterface apiInterface = RetroFitClient.getRetrofit().create(ApiInterface.class);

        final LoginModel loginModel = new LoginModel(email, password, model, 1);
        Call<LoginModel> call = apiInterface.loginPostData(loginModel);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                progressDialog.dismiss();
                String status = response.body().getStatus();
                if (status.equals("success")) {
                    int userIdOnResponse = response.body().getUserId();
                    int id = response.body().getPlatformId().intValue();
                    int ssid = response.body().getStudentStandardId();
                    String name = response.body().getName();
                    preferenceManager.putString(Constants.NAME, name);
                    preferenceManager.putBoolean(Constants.USER_LOGGED, true);
                    preferenceManager.putInt(Constants.USER_ID, userIdOnResponse);
                    preferenceManager.putString(Constants.USER_EMAIL, email);
                    preferenceManager.putInt(Constants.PLATFORM_ID, id);
                    preferenceManager.putInt(Constants.SSID, ssid);
                    saveTokenFireBase(userIdOnResponse);
                    registerInFirebase(email, name, userIdOnResponse,ssid);
                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    getStatus(status);
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void registerInFirebase(String usermail, String name, Integer userId, Integer ssid) {
        String mail = usermail.toLowerCase();
        if (!firebaseCheck(mail)) {
            CollectionReference user = database.collection(Constants.FIREBASE_USER_DB);
            HashMap<String, Object> data = new HashMap<>();
            data.put(Constants.NAME, name);
            data.put(Constants.USER_EMAIL, mail);
            data.put(Constants.USER_ID, userId);
            data.put(Constants.ROLE, Constants.STUDENT_ROLE);
            data.put(Constants.SSID, ssid);
            data.put(Constants.FIREBASE_TOKEN, preferenceManager.getString(Constants.FIREBASE_TOKEN));
            user.document(mail).set(data).addOnSuccessListener(d -> {

            }).addOnFailureListener(exception -> {

            });
        }

    }

    public void saveTokenFireBase(Integer userId) {
        MobileModel mobileModel = new MobileModel();
        mobileModel.getDeviceName();
        String token = preferenceManager.getString(Constants.FIREBASE_TOKEN);
        FirebaseTokenModel firebaseTokenModel = new FirebaseTokenModel(userId, mobileModel.getDeviceName(), token);
        ApiInterface apiInterface = RetroFitClient.getRetrofit().create(ApiInterface.class);
        Call<FirebaseTokenModel> call = apiInterface.firebaseToken(firebaseTokenModel);
        call.enqueue(new Callback<FirebaseTokenModel>() {
            @Override
            public void onResponse(Call<FirebaseTokenModel> call, Response<FirebaseTokenModel> response) {
                if (response.body().getToken() == null) {

                }
            }

            @Override
            public void onFailure(Call<FirebaseTokenModel> call, Throwable t) {

            }
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

    @Override
    public void onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press Back Again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }

    private void getStatus(String status) {

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
        if (status.equals("failed")) {
            textViewTitle.setText("Technical Glitch");
            textViewMessage.setText("Sorry," + "/n there is a technical Glitch");
        } else if (status.equals("userNotExist")) {
            textViewTitle.setText("Not Exist");
            textViewMessage.setText("The user you are trying to access is not Registered");
        } else if (status.equals("underProcess")) {
            textViewTitle.setText("Under Process");
            textViewMessage.setText("User is under processing please go with web portal");
        } else if (status.equals("notStudent")) {
            textViewTitle.setText("Hey Don't Worry");
            textViewMessage.setText("You're not a Student, But dont worry you can get access your account please download Admin and Teacher App");
        } else if (status.equals("userDeleted")) {
            textViewTitle.setText("User Deleted");
            textViewMessage.setText("User you're trying to access, May be deleted or withdrawn");
        } else if (status.equals("invalidCredentials")) {
            textViewTitle.setText("Invalid Password");
            textViewMessage.setText("entered in valid password please type correct password or reset");
        } else {
            progressDialog.dismiss();
        }

    }

    public Boolean firebaseCheck(String email) {
        Integer i[] = {0};

        database = FirebaseFirestore.getInstance();
        DocumentReference docRef = database.collection(Constants.FIREBASE_USER_DB).document(email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().getData() != null) {
                        docRef.update("firebaseToken", preferenceManager.getString(Constants.FIREBASE_TOKEN));
                        i[0] = 1;

                        Log.d("FCM User", "Exist : " + task.getResult().getData());
                    } else {
                        Log.d("FCM User", "Not Found");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });

        if (i[0] == 1) {
            return true;
        } else {
            return false;
        }

    }



}