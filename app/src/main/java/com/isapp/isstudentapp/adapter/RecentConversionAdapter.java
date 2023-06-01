package com.isapp.isstudentapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.isapp.isstudentapp.activity.ChatActivity;
import com.isapp.isstudentapp.chat.ChatMessage;
import com.isapp.isstudentapp.databinding.TeacherRecentConversationBinding;

import java.util.List;

public class RecentConversionAdapter extends RecyclerView.Adapter<RecentConversionAdapter.ConversionViewHolder> {
    private final List<ChatMessage> chatMessages;

    public RecentConversionAdapter(List<ChatMessage> chatMessages) {
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
            binding.recentTeacherNameChat.setText(chatMessage.conversionName);
            binding.teacherRecentMessage.setText(chatMessage.message);
            binding.timeRecentMessage.setText(chatMessage.dateObject.toString());
            String teacherId = chatMessage.teacherId;
            String teacherName = chatMessage.conversionName;
            String teacherEmail = chatMessage.teacherEmail;
            String teacherSubject = chatMessage.teacherCourse;



            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View v) {

                                                         Intent intent = new Intent(v.getContext(), ChatActivity.class);
                                                         intent.putExtra("teacherId", teacherId);
                                                         intent.putExtra("teacherName", teacherName);
                                                         intent.putExtra("subjectName", teacherSubject);
                                                         intent.putExtra("teacherEmail",teacherEmail);
                                                         v.getContext().startActivity(intent);


                                                     }
                                                 }


            );


        }
    }

}
