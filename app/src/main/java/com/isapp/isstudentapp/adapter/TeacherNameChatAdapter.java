package com.isapp.isstudentapp.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.isapp.isstudentapp.R;
import com.isapp.isstudentapp.activity.ChatActivity;
import com.isapp.isstudentapp.model.AssignTeacherModel;

import java.util.List;

public class TeacherNameChatAdapter extends RecyclerView.Adapter<TeacherNameChatAdapter.ViewHolderChat>{
    List<AssignTeacherModel.AssignedTeacher> list;
    public TeacherNameChatAdapter(List<AssignTeacherModel.AssignedTeacher> list){
        this.list = list;
    }

    public TeacherNameChatAdapter(){

    }


    @NonNull
    @Override
    public ViewHolderChat onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.teacher_row_content,parent,false);
        ViewHolderChat viewHolderChat = new ViewHolderChat(view);
        return viewHolderChat;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderChat holder, int position) {

        String teacherId ="" ;
        String teacherName ="";
        String teacherSubject ="";
        String teacherEmail ="";

        if (list.get(position).getTeacherName()==null && list.get(position).getCoursesName()==null){

        }else{

            teacherId = list.get(position).getTeacherId();
            teacherName = list.get(position).getTeacherName();
            teacherSubject = list.get(position).getCoursesName();
            teacherEmail = list.get(position).getTeacherEmail();

        }

        holder.teacherName.setText(teacherName);
        holder.teacherSubject.setText(teacherSubject);

        String finalTeacherId = teacherId;
        String finalTeacherName = teacherName;
        String finalTeacherSubject = teacherSubject;
        String finalTeacherEmail = teacherEmail;
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ChatActivity.class);
                intent.putExtra("teacherId", finalTeacherId);
                intent.putExtra("teacherName", finalTeacherName);
                intent.putExtra("subjectName", finalTeacherSubject);
                intent.putExtra("teacherEmail", finalTeacherEmail);

                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolderChat extends RecyclerView.ViewHolder {
        TextView teacherName, teacherSubject;
        LinearLayout linearLayout;

        public ViewHolderChat(@NonNull View itemView) {
            super(itemView);
            teacherName= itemView.findViewById(R.id.teacher_name_chat);
            teacherSubject = itemView.findViewById(R.id.teacher_subject_name);
            linearLayout = itemView.findViewById(R.id.teacher_chat_row_page);
            
        }
    }
}
