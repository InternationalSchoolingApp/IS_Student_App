package com.isapp.isstudentapp.activity;

import android.app.ProgressDialog;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.isapp.isstudentapp.chat.ChatAdapter;
import com.isapp.isstudentapp.chat.ChatMessage;
import com.isapp.isstudentapp.common.ColorOfStatusAndNavBar;
import com.isapp.isstudentapp.constant.Constants;
import com.isapp.isstudentapp.databinding.ActivityChatAdminBinding;
import com.isapp.isstudentapp.fcmApi.FcmApiClient;
import com.isapp.isstudentapp.fcmApi.FcmApiInterface;
import com.isapp.isstudentapp.model.AdminStudentMappingModel;
import com.isapp.isstudentapp.model.CheckAssignedAdminModel;
import com.isapp.isstudentapp.network.NetworkChangeListener;
import com.isapp.isstudentapp.preference.PreferenceManager;
import com.isapp.isstudentapp.retrofit.ApiInterface;
import com.isapp.isstudentapp.retrofit.RetroFitClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatAdminActivity extends AppCompatActivity {

    private ActivityChatAdminBinding binding;

    NetworkChangeListener networkChangeListner = new NetworkChangeListener();
    private List<ChatMessage> chatMessages;
    private ChatAdapter chatAdapter;

    ProgressDialog progressDialog;
    private FirebaseFirestore database;
    private PreferenceManager preferenceManager;

    Integer userId;

    private String adminName, adminEmail, adminUserId, senderId, recieverFcmToken;
    private String conversionId = null;
    private Boolean online = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ColorOfStatusAndNavBar colorOfStatusAndNavBar = new ColorOfStatusAndNavBar();
        colorOfStatusAndNavBar.colorOfStatusBar(this);
        init();
        getAdmin();


    }

    private void setListeners() {
        binding.backButtonChat.setOnClickListener(c -> onBackPressed());
        binding.sendButtonChat.setOnClickListener(v -> {
            if (!binding.chatEdittext.getText().toString().equals("")) {
                sendMessage();
            }
        });
    }




    void assignAdmin(){
        ApiInterface apiInterface = RetroFitClient.getRetrofit().create(ApiInterface.class);
        AdminStudentMappingModel adminStudentMappingModel = new AdminStudentMappingModel(userId);
        Call<AdminStudentMappingModel> call = apiInterface.adminStudentMappingModel(adminStudentMappingModel);
        call.enqueue(new Callback<AdminStudentMappingModel>() {
            @Override
            public void onResponse(Call<AdminStudentMappingModel> call, Response<AdminStudentMappingModel> response) {
                if(response.body().getStatus().equals("success")){
                    progressDialog.dismiss();
                    finish();
                    startActivity(getIntent());
                }else{
                    progressDialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<AdminStudentMappingModel> call, Throwable t) {

            }
        });


    }

    void init() {
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        preferenceManager = new PreferenceManager(getApplicationContext());
        userId= preferenceManager.getInt(Constants.USER_ID);
        senderId = preferenceManager.getString(Constants.USER_EMAIL);
        binding.chatScreenTeacherName.setText("School Admin");
        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatMessages, senderId);
        database = FirebaseFirestore.getInstance();
        binding.chatContent.setAdapter(chatAdapter);

    }

    private void sendMessage() {

        HashMap<String, Object> message = new HashMap<>();
        message.put("senderId", senderId);
        message.put("receiverId", adminEmail);
        message.put("studentEmail", senderId);
        message.put("adminEmail", adminEmail);
        message.put("message", binding.chatEdittext.getText().toString());
        message.put("timeStamp", new Date());
        message.put("type", "ADMIN_STUDENT");
        database.collection(Constants.KEY_COLLECTION_CHAT_ADMIN).add(message);
        if(!online){
            try{

                JSONArray tokens = new JSONArray();
                tokens.put(recieverFcmToken);

                JSONObject data = new JSONObject();
                data.put(Constants.USER_EMAIL, preferenceManager.getString(Constants.USER_EMAIL));
                data.put(Constants.NAME, "School Admin");
                data.put(Constants.FIREBASE_TOKEN, preferenceManager.getString(Constants.FIREBASE_TOKEN));
                data.put(Constants.KEY_MESSAGE, binding.chatEdittext.getText().toString());
                data.put("channel", "STUDENT_ADMIN");

                JSONObject body = new JSONObject();
                body.put(Constants.REMOTE_MESSAGE_DATA, data);
                body.put(Constants.REGISTRATION_IDS, tokens);

                sendNotification(body.toString());

            }catch ( Exception exception){

            }
        }

        if (conversionId != null) {
            updateConversion(binding.chatEdittext.getText().toString());
        } else {
            HashMap<String, Object> conversion = new HashMap<>();
            conversion.put(Constants.KEY_SENDER_ID, senderId);
            conversion.put(Constants.KEY_SENDER_NAME, preferenceManager.getString(Constants.NAME));
            conversion.put(Constants.KEY_RECIEVER_ID, adminEmail);
            conversion.put(Constants.KEY_RECEIVER_NAME, adminName);
            conversion.put("adminName", adminName);
            conversion.put("adminEmail", adminEmail);
            conversion.put("studentName",preferenceManager.getString(Constants.NAME));
            conversion.put("studentEmail",senderId);
            conversion.put(Constants.KEY_LAST_MESSAGE, binding.chatEdittext.getText().toString());
            conversion.put(Constants.KEY_TIME_STAMP, new Date());
            addConversion(conversion);
        }
                binding.chatEdittext.setText(null);
    }

    private void listenAvailabilityOfReceiver() {
        database.collection(Constants.FIREBASE_USER_DB).document(""+adminEmail).addSnapshotListener(ChatAdminActivity.this, ((value, error) -> {
            if (error != null) {
                return;
            }
            if (value != null) {
                if (value.getLong(Constants.KEY_AVAILABLITY) != null) {

                }
                if (value.get(Constants.FIREBASE_TOKEN) != null) {
                    recieverFcmToken = Objects.requireNonNull(value.get(Constants.FIREBASE_TOKEN).toString());
                }
            }
            if (value.get(Constants.FIREBASE_TOKEN) != null) {
                recieverFcmToken = Objects.requireNonNull(value.get(Constants.FIREBASE_TOKEN).toString());
                Log.d("FCM TOKEN", "listenAvailabilityOfReceiver: " + recieverFcmToken);
            } else {
                Log.d("FCM TOKEN NULL", "listenAvailabilityOfReceiver: ");
            }
            if (online) {
                binding.textOnline.setVisibility(View.VISIBLE);
            } else {
                binding.textOnline.setVisibility(View.GONE);
            }

        }));
    }


    private String getReadableDateTime(Date date) {
        return new SimpleDateFormat("MMMM dd, yyyy - hh:mm a", Locale.getDefault()).format(date);
    }

    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if (error != null) {
            return;
        }
        if (value != null) {
            int count = chatMessages.size();
            for (DocumentChange documentChange : value.getDocumentChanges()) {
                if (documentChange.getType() == DocumentChange.Type.ADDED) {
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.senderId = documentChange.getDocument().getString("senderId");
                    chatMessage.receiverId = documentChange.getDocument().getString("receiverId");
                    chatMessage.message = documentChange.getDocument().getString("message");
                    chatMessage.time = getReadableDateTime(documentChange.getDocument().getDate("timeStamp"));
                    chatMessage.dateObject = documentChange.getDocument().getDate("timeStamp");
                    chatMessages.add(chatMessage);
                }
            }
            Collections.sort(chatMessages, (obj1, obj2) -> obj1.dateObject.compareTo(obj2.dateObject));
            if (count == 0) {
                chatAdapter.notifyDataSetChanged();
            } else {
                chatAdapter.notifyItemRangeInserted(chatMessages.size(), chatMessages.size());
                binding.chatContent.smoothScrollToPosition(chatMessages.size() - 1);
            }
            binding.chatContent.setVisibility(View.VISIBLE);
        }

        if (conversionId == null) {
            checkForConversion();
        }

    };

    private void listenMessage() {

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_CHAT_ADMIN)
                .whereEqualTo("senderId", senderId)
                .whereEqualTo("receiverId", adminEmail)
                .addSnapshotListener(eventListener);
        database.collection(Constants.KEY_COLLECTION_CHAT_ADMIN)
                .whereEqualTo("senderId", adminEmail)
                .whereEqualTo("receiverId", senderId)
                .addSnapshotListener(eventListener);

    }



    private void addConversion(HashMap<String, Object> conversion) {
        CollectionReference conversation = database.collection("ADMIN_STUDENT_CONVERSATION");
        conversation.document(""+preferenceManager.getString(Constants.USER_EMAIL).toLowerCase()+" - "+adminEmail).set(conversion).addOnSuccessListener(documentReference -> conversionId = (""+preferenceManager.getString(Constants.USER_EMAIL).toLowerCase()+" - "+adminEmail)).addOnFailureListener(exception->{
        });

    }

    private void updateConversion(String message) {
        DocumentReference documentReference = database.collection("ADMIN_STUDENT_CONVERSATION").document(conversionId);
        documentReference.update(Constants.KEY_LAST_MESSAGE, message, Constants.KEY_TIME_STAMP, new Date(), Constants.KEY_SENDER_ID, senderId,
                Constants.KEY_SENDER_NAME, preferenceManager.getString(Constants.NAME),
                Constants.KEY_RECIEVER_ID, adminEmail,
                "adminEmail", adminEmail,
                "adminName", adminName,
                "studentName",preferenceManager.getString(Constants.NAME),
                Constants.ADMIN_ID, String.valueOf(preferenceManager.getInt(Constants.USER_ID)));
    }


    private void checkForConversionRemotely(String senderId) {
        database.collection("ADMIN_STUDENT_CONVERSATION").whereEqualTo(Constants.KEY_SENDER_ID, senderId).whereEqualTo(Constants.KEY_RECIEVER_ID, adminEmail).get().addOnCompleteListener(conversionCompleteListener);
    }

    private void checkForConversion() {
        if (chatMessages.size() != 0) {
            checkForConversionRemotely(senderId);
            checkForConversionRemotely(adminEmail);
        }
    }

    private final OnCompleteListener<QuerySnapshot> conversionCompleteListener = task -> {
        if (task.isSuccessful() && task.getResult() != null && task.getResult().getDocuments().size() > 0) {
            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
            conversionId = documentSnapshot.getId();
        }
    };

    private void sendNotification(String messageBody) {

        FcmApiClient.getClient().create(FcmApiInterface.class).sendMessage(Constants.getRemoteMessageHeaders(), messageBody).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {

                if (response.isSuccessful()) {
                    try {
                        if (response.body() != null) {
                            JSONObject responseJson = new JSONObject(response.body());
                            JSONArray results = responseJson.getJSONArray("results");
                            if (responseJson.getInt("failure") == 1) {
                                JSONObject error = (JSONObject) results.get(0);
                                return;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {

                }
            }


            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

            }
        });

    }

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

    private void getAdmin() {

        ApiInterface apiInterface = RetroFitClient.getRetrofit().create(ApiInterface.class);
        CheckAssignedAdminModel checkAssignedAdminModel = new CheckAssignedAdminModel(userId);
        Call<CheckAssignedAdminModel> call = apiInterface.checkAssignedAdminModel(checkAssignedAdminModel);
        call.enqueue(new Callback<CheckAssignedAdminModel>() {
            @Override
            public void onResponse(Call<CheckAssignedAdminModel> call, Response<CheckAssignedAdminModel> response) {
                if (response.body().getStatus().equals("success")){
                    assignAdmin();
                    progressDialog.dismiss();
                }else{
                    adminUserId = String.valueOf(response.body().getAdminId());
                    adminEmail = response.body().getAdminEmail();
                    adminName = response.body().getAdminName();
                    progressDialog.dismiss();
                    setListeners();
                    listenMessage();

                }
            }

            @Override
            public void onFailure(Call<CheckAssignedAdminModel> call, Throwable t) {

            }
        });

    }
}