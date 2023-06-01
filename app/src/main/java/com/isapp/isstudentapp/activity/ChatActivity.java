package com.isapp.isstudentapp.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.isapp.isstudentapp.chat.ChatAdapter;
import com.isapp.isstudentapp.chat.ChatMessage;
import com.isapp.isstudentapp.common.BaseActivity;
import com.isapp.isstudentapp.common.ColorOfStatusAndNavBar;
import com.isapp.isstudentapp.constant.Constants;
import com.isapp.isstudentapp.databinding.ActivityChatBinding;
import com.isapp.isstudentapp.fcmApi.FcmApiClient;
import com.isapp.isstudentapp.fcmApi.FcmApiInterface;
import com.isapp.isstudentapp.model.User;
import com.isapp.isstudentapp.network.NetworkChangeListener;
import com.isapp.isstudentapp.preference.PreferenceManager;

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

public class ChatActivity extends BaseActivity {

    private ActivityChatBinding binding;
    PreferenceManager preferenceManager;
    private List<ChatMessage> chatMessages;
    private ChatAdapter chatAdapter;
    private FirebaseFirestore database;
    NetworkChangeListener networkChangeListner = new NetworkChangeListener();
    private String conversionId = null;
    private Boolean online = false;
    private User recieverUser;

    private String senderId, teacherId, teacherName, courseName, teacherEmail, recieverFcmToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ColorOfStatusAndNavBar colorOfStatusAndNavBar = new ColorOfStatusAndNavBar();
        colorOfStatusAndNavBar.chat(this);

        init();
        listenHoldOfChat();
        listenMessage();
        loadRecieverDetails();
        setListeners();


    }

    public void init() {
        preferenceManager = new PreferenceManager(this);
        senderId = preferenceManager.getString(Constants.USER_EMAIL).toLowerCase();
        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatMessages, senderId.toLowerCase());
        database = FirebaseFirestore.getInstance();
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            teacherId = extra.getString("teacherId");
            teacherName = extra.getString("teacherName");
            courseName = extra.getString("subjectName");
            teacherEmail = extra.getString("teacherEmail").toLowerCase();

            Log.d("VALUES", "init: "+teacherId + " "+ teacherName+" "+courseName +" "+teacherEmail);
        }
        binding.chatContent.setAdapter(chatAdapter);
    }

    private void loadRecieverDetails() {
        binding.chatScreenTeacherName.setText(teacherName);
        binding.chatTeacherSubjectName.setText(courseName);
    }

    private String getReadableDateTime(Date date) {
        return new SimpleDateFormat("MMMM dd, yyyy - hh:mm a", Locale.getDefault()).format(date);
    }


    private void sendMessage() {
        HashMap<String, Object> message = new HashMap<>();
        message.put(Constants.KEY_SENDER_ID, senderId);
        message.put(Constants.KEY_RECIEVER_ID, teacherEmail);
        message.put(Constants.KEY_MESSAGE, binding.chatEdittext.getText().toString());
        message.put(Constants.KEY_TIME_STAMP, new Date());
        database.collection(Constants.FIREBASE_CHAT_DB).add(message);
        if(!online){
            try{

                JSONArray tokens = new JSONArray();
                tokens.put(recieverFcmToken);

                JSONObject data = new JSONObject();
                data.put(Constants.USER_EMAIL, preferenceManager.getString(Constants.USER_EMAIL));
                data.put(Constants.NAME, preferenceManager.getString(Constants.NAME));
                data.put(Constants.FIREBASE_TOKEN, preferenceManager.getString(Constants.FIREBASE_TOKEN));
                data.put(Constants.KEY_MESSAGE, binding.chatEdittext.getText().toString());
                data.put("channel", "STUDENT_TEACHER");

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
            conversion.put(Constants.KEY_RECIEVER_ID, teacherEmail.toLowerCase());
            conversion.put(Constants.KEY_RECEIVER_NAME, teacherName);
            conversion.put(Constants.TEACHER_ID, teacherId);
            conversion.put("teacherName", teacherName);
            conversion.put("teacherSubject", courseName);
            conversion.put("teacherEmail", teacherEmail);
            conversion.put("studentName",preferenceManager.getString(Constants.NAME));
            conversion.put("studentEmail",senderId);
            conversion.put(Constants.STUDENT_ID, String.valueOf(preferenceManager.getInt(Constants.SSID)));
            conversion.put(Constants.KEY_SUBJECT_NAME, courseName);
            conversion.put(Constants.KEY_LAST_MESSAGE, binding.chatEdittext.getText().toString());
            conversion.put(Constants.KEY_TIME_STAMP, new Date());
            addConversion(conversion);
        }

        binding.chatEdittext.setText(null);
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
                    chatMessage.senderId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                    chatMessage.receiverId = documentChange.getDocument().getString(Constants.KEY_RECIEVER_ID);
                    chatMessage.message = documentChange.getDocument().getString(Constants.KEY_MESSAGE);
                    chatMessage.time = getReadableDateTime(documentChange.getDocument().getDate(Constants.KEY_TIME_STAMP));
                    chatMessage.dateObject = documentChange.getDocument().getDate(Constants.KEY_TIME_STAMP);
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
        database.collection(Constants.KEY_COLLECTION_CHAT)
                .whereEqualTo(Constants.KEY_SENDER_ID, senderId)
                .whereEqualTo(Constants.KEY_RECIEVER_ID, teacherEmail)
                .addSnapshotListener(eventListener);
        database.collection(Constants.KEY_COLLECTION_CHAT)
                .whereEqualTo(Constants.KEY_SENDER_ID, teacherEmail)
                .whereEqualTo(Constants.KEY_RECIEVER_ID, senderId)
                .addSnapshotListener(eventListener);
    }

    private void setListeners() {
        binding.backButtonChat.setOnClickListener(c -> {

            Intent intent = new Intent(ChatActivity.this, RecentChatActivity.class);
            startActivity(intent);
            finish();

        });
        binding.sendButtonChat.setOnClickListener(v -> {
            if (!binding.chatEdittext.getText().toString().equals("")) {
                sendMessage();
            }
        });
        binding.ChatInfoButton.setOnClickListener(v -> {
            Intent intent = new Intent(ChatActivity.this, TeacherProfileActivity.class);
            intent.putExtra("teacherName", teacherName);
            intent.putExtra("teacherEmail", teacherEmail);
            intent.putExtra("teacherId", teacherId);
            intent.putExtra("subjectName", courseName);
            startActivity(intent);
        });


    }

    private void checkForConversionRemotely(String senderId, String receiverId) {
        Task<QuerySnapshot> querySnapshotTask = database.collection(Constants.KEY_COLLECTIONS_CONVERSATION).whereEqualTo(Constants.KEY_SENDER_ID, senderId).whereEqualTo(Constants.KEY_RECIEVER_ID, teacherEmail.toLowerCase()).get().addOnCompleteListener(conversionCompleteListener);
    }

    private final OnCompleteListener<QuerySnapshot> conversionCompleteListener = task -> {
        if (task.isSuccessful() && task.getResult() != null && task.getResult().getDocuments().size() > 0) {
            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
            conversionId = documentSnapshot.getId();
        }
    };

    private void checkForConversion() {
        if (chatMessages.size() != 0) {
            checkForConversionRemotely(senderId, teacherEmail.toLowerCase());
            checkForConversionRemotely(teacherEmail.toLowerCase(), senderId);
        }
    }


    private void addConversion(HashMap<String, Object> conversion) {
        CollectionReference conversation = database.collection(Constants.KEY_COLLECTIONS_CONVERSATION);
        conversation.document(""+preferenceManager.getString(Constants.USER_EMAIL).toLowerCase()+" - "+teacherEmail.toLowerCase()).set(conversion).addOnSuccessListener(documentReference -> conversionId = (""+preferenceManager.getString(Constants.USER_EMAIL).toLowerCase()+" - "+teacherEmail.toLowerCase())).addOnFailureListener(exception->{

        });

    }

    private void updateConversion(String message) {
        DocumentReference documentReference = database.collection(Constants.KEY_COLLECTIONS_CONVERSATION).document(conversionId);
        documentReference.update(Constants.KEY_LAST_MESSAGE, message, Constants.KEY_TIME_STAMP, new Date(), Constants.KEY_SENDER_ID, senderId,
                Constants.KEY_SENDER_NAME, preferenceManager.getString(Constants.NAME),
                Constants.KEY_RECIEVER_ID, teacherEmail.toLowerCase(),
                Constants.KEY_RECIEVER_ID, teacherEmail.toLowerCase(),
                Constants.TEACHER_ID, teacherId,
                "teacherEmail", teacherEmail,
                "teacherName", teacherName,
                "teacherSubject", courseName,
                "studentName",preferenceManager.getString(Constants.NAME),
                Constants.STUDENT_ID, String.valueOf(preferenceManager.getInt(Constants.USER_ID)),
                Constants.KEY_SUBJECT_NAME, courseName);
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

    private void listenAvailabilityOfReceiver() {
        database.collection(Constants.FIREBASE_USER_DB).document(teacherEmail.toLowerCase()).addSnapshotListener(ChatActivity.this, ((value, error) -> {
            if (error != null) {
                return;
            }
            if (value != null) {
                if (value.getLong(Constants.KEY_AVAILABLITY) != null) {
                    int available = Objects.requireNonNull(value.getLong(Constants.KEY_AVAILABLITY).intValue());
                    online = available == 1;
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

    @Override
    protected void onResume() {
        super.onResume();
        listenAvailabilityOfReceiver();
    }



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

    public void listenHoldOfChat(){
        database.collection(Constants.KEY_COLLECTIONS_CONVERSATION).document(""+senderId.toLowerCase()+" - "+teacherEmail.toLowerCase()).addSnapshotListener(ChatActivity.this, ((value, error) -> {
            if (error != null) {
                return;
            }
            if (value != null) {
                if (value.getBoolean(Constants.ON_HOLD) != null) {
                    Boolean onhold = Objects.requireNonNull(value.getBoolean(Constants.ON_HOLD).booleanValue());
                    if (onhold){
                        binding.sendButtonChat.setVisibility(View.GONE);
                        binding.chatEdittext.setVisibility(View.GONE);
                        Toast.makeText(this, "Chat is on hold By School Admin", Toast.LENGTH_SHORT).show();
                    }else{
                        binding.sendButtonChat.setVisibility(View.VISIBLE);
                        binding.chatEdittext.setVisibility(View.VISIBLE);
                    }

                }
            }

        }));

    }




}