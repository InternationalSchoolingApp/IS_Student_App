package com.isapp.isstudentapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.isapp.isstudentapp.adapter.RecentConversionAdapter;
import com.isapp.isstudentapp.chat.ChatMessage;
import com.isapp.isstudentapp.common.BaseActivity;
import com.isapp.isstudentapp.common.ColorOfStatusAndNavBar;
import com.isapp.isstudentapp.constant.Constants;
import com.isapp.isstudentapp.databinding.ActivityRecentChatBinding;
import com.isapp.isstudentapp.preference.PreferenceManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecentChatActivity extends BaseActivity {

    private ActivityRecentChatBinding binding;
    private List<ChatMessage> conversation;
    private RecentConversionAdapter recentConversionAdapter;
    private FirebaseFirestore firebaseFirestore;
    PreferenceManager preferenceManager;
    String senderIdClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRecentChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        listenConversations();
        setListeners();

    }

    private void init() {
        ColorOfStatusAndNavBar colorOfStatusAndNavBar = new ColorOfStatusAndNavBar();
        colorOfStatusAndNavBar.loginAndForgetPassword(this);
        preferenceManager = new PreferenceManager(this);
        senderIdClass= preferenceManager.getString(Constants.USER_EMAIL).toLowerCase();
        conversation = new ArrayList<>();
        recentConversionAdapter = new RecentConversionAdapter(conversation);
        binding.recentRecyclerView.setAdapter(recentConversionAdapter);
        firebaseFirestore = FirebaseFirestore.getInstance();

    }
    private void setListeners() {
        binding.startChatBtn.setOnClickListener(v -> {
            Intent i = new Intent(RecentChatActivity.this, AssignedTeachersActivity.class);
            startActivity(i);
            finish();
        });
        binding.recentTeacherBackButton.setOnClickListener(v -> onBackPressed());

    }

    private void listenConversations(){


        firebaseFirestore.collection(Constants.KEY_COLLECTIONS_CONVERSATION)
                .whereEqualTo(Constants.KEY_SENDER_ID, preferenceManager.getString(Constants.USER_EMAIL).toLowerCase())
                .addSnapshotListener(eventListener);
        firebaseFirestore.collection(Constants.KEY_COLLECTIONS_CONVERSATION)
                .whereEqualTo(Constants.KEY_RECIEVER_ID, preferenceManager.getString(Constants.USER_EMAIL).toLowerCase())
                .addSnapshotListener(eventListener);
    }


    private EventListener<QuerySnapshot> eventListener = (value , error) ->{
        if(error!=null){
            return;
        }
        if (value!= null){
            for(DocumentChange documentChange : value.getDocumentChanges()){
                if (documentChange.getType() == DocumentChange.Type.ADDED){
                    String senderId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                    String receiverId = documentChange.getDocument().getString(Constants.KEY_RECIEVER_ID);
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.senderId = senderId;
                    chatMessage.receiverId = receiverId;
                    if (senderId.equals(senderIdClass)){
                        chatMessage.conversionName = documentChange.getDocument().getString(Constants.KEY_RECEIVER_NAME);
                        chatMessage.conversionId = documentChange.getDocument().getString(Constants.KEY_RECIEVER_ID);
                    }else{
                        chatMessage.conversionName = documentChange.getDocument().getString(Constants.KEY_SENDER_NAME);
                        chatMessage.conversionId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                    }
                    chatMessage.message = documentChange.getDocument().getString(Constants.KEY_LAST_MESSAGE);
                    chatMessage.dateObject = documentChange.getDocument().getDate(Constants.KEY_TIME_STAMP);
                    chatMessage.teacherCourse = documentChange.getDocument().getString(Constants.KEY_SUBJECT_NAME);
                    chatMessage.teacherName = documentChange.getDocument().getString(Constants.KEY_RECEIVER_NAME);
                    chatMessage.teacherId  = documentChange.getDocument().getString(Constants.KEY_TEACHER_ID);
                    chatMessage.teacherEmail  = documentChange.getDocument().getString("teacherEmail");
                    conversation.add(chatMessage);
                }else if (documentChange.getType() == DocumentChange.Type.MODIFIED){
                    for (int i = 0;i<conversation.size();i++){
                        String senderId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                        String receiverId = documentChange.getDocument().getString(Constants.KEY_RECIEVER_ID);
                        if(conversation.get(i).senderId.equals(senderId) && conversation.get(i).receiverId.equals(receiverId)){
                            conversation.get(i).message = documentChange.getDocument().getString(Constants.KEY_LAST_MESSAGE);
                            conversation.get(i).dateObject = documentChange.getDocument().getDate(Constants.KEY_TIME_STAMP);
                            conversation.get(i).teacherCourse = documentChange.getDocument().getString(Constants.KEY_SUBJECT_NAME);
                            conversation.get(i).teacherName = documentChange.getDocument().getString(Constants.KEY_RECIEVER_ID);
                            conversation.get(i).teacherId = documentChange.getDocument().getString(Constants.KEY_TEACHER_ID);
                            conversation.get(i).teacherEmail = documentChange.getDocument().getString("teacherEmail");
                            break;
                        }
                    }
                }
            }
            Collections.sort(conversation, (obj1, obj2)-> obj2.dateObject.compareTo(obj1.dateObject));
            recentConversionAdapter.notifyDataSetChanged();
            binding.recentRecyclerView.smoothScrollToPosition(0);
            binding.recentRecyclerView.setVisibility(View.VISIBLE);

        }
    };


}