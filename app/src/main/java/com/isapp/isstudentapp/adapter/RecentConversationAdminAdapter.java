package com.isapp.isstudentapp.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.isapp.isstudentapp.activity.ChatAdminActivity;
import com.isapp.isstudentapp.chat.ChatMessage;
import com.isapp.isstudentapp.databinding.TeacherRecentConversationBinding;

import java.util.List;

public class RecentConversationAdminAdapter extends RecyclerView.Adapter<RecentConversationAdminAdapter.ConversionViewHolder> {

    private final List<ChatMessage> chatMessages;

    public RecentConversationAdminAdapter(List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    @NonNull
    @Override
    public ConversionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConversionViewHolder(TeacherRecentConversationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ConversionViewHolder holder, int position) {
        holder.setData(chatMessages.get(position));
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    class ConversionViewHolder extends RecyclerView.ViewHolder {
        TeacherRecentConversationBinding binding;

        ConversionViewHolder(TeacherRecentConversationBinding teacherRecentConversationBinding) {
            super(teacherRecentConversationBinding.getRoot());
            binding = teacherRecentConversationBinding;
        }

        void setData(ChatMessage chatMessage) {
            binding.recentTeacherNameChat.setText("School Admin");
            binding.teacherRecentMessage.setText(chatMessage.message);
            binding.timeRecentMessage.setText(chatMessage.dateObject.toString());
            String studentId = chatMessage.adminId;
            String studentName = chatMessage.adminName;
            String studentEmail = chatMessage.adminEmail;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View v) {

                                                         Intent intent = new Intent(v.getContext(), ChatAdminActivity.class);
                                                         intent.putExtra("adminUserId", studentId);
                                                         intent.putExtra("name", studentName);
                                                         intent.putExtra("email", studentEmail);
                                                         v.getContext().startActivity(intent);

                                                     }
                                                 }


            );


        }
    }

}
