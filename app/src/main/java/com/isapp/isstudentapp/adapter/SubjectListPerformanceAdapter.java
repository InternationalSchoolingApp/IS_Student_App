package com.isapp.isstudentapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.isapp.isstudentapp.R;
import com.isapp.isstudentapp.activity.PerformanceByGradeActivity;
import com.isapp.isstudentapp.model.SubjectListPerformance;

import java.util.List;

public class SubjectListPerformanceAdapter extends RecyclerView.Adapter<SubjectListPerformanceAdapter.ViewHolder> {
    List<SubjectListPerformance.DomainStudents.DomainStudent> list;

    public SubjectListPerformanceAdapter(List<SubjectListPerformance.DomainStudents.DomainStudent> list){
        this.list = list;
    }

    public SubjectListPerformanceAdapter(){

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.subject_list_view_performance,parent,false);
        ViewHolder viewHolder =new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.subjectName.setText(list.get(position).getCourseName());
            holder.teacherName.setText("Teacher : "+ list.get(position).getTeacherName());
            holder.score.setText("Overall Score : " + list.get(position).getAchieved()+"%");
            holder.gradable_tv.setText("Gradable Activity Score : "+list.get(position).getComplete()+"%");
            holder.all_activity_tv.setText("All Activity Score : "+list.get(position).getCompleteAll()+"%");
            String gradeProgressString = list.get(position).getComplete();
            int gradeProgress = Integer.valueOf(gradeProgressString);
            holder.grade.setProgress(gradeProgress);
            String allActivityString = list.get(position).getCompleteAll();
            int allActivity = Integer.valueOf(allActivityString);
            holder.allActivity.setProgress(allActivity);
            String value = list.get(position).getAchieved();
            float scorePB = Float.valueOf(value);
            holder.score_pb.setProgress(Math.round(scorePB));
            String courseId = list.get(position).getCourseId();
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), PerformanceByGradeActivity.class);
                        intent.putExtra("courseId", courseId);
                        v.getContext().startActivity(intent);
                }
            });


    }




    @Override
    public int getItemCount() {
        return list.size();
    }

    public static Intent getCourseId(Context context , String courseId){
        Intent intent = new Intent(context, PerformanceByGradeActivity.class);
        intent.putExtra("courseId", courseId);
        context.startActivity(intent);
        return  intent;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView subjectName, teacherName, score, gradable_tv, all_activity_tv;
        ProgressBar grade, allActivity, score_pb;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectName= itemView.findViewById(R.id.subject_name);
            teacherName= itemView.findViewById(R.id.teacher_name_tv);
            score= itemView.findViewById(R.id.score);
            grade =itemView.findViewById(R.id.gradable_pb);
            allActivity =itemView.findViewById(R.id.activity_pb);
            gradable_tv =itemView.findViewById(R.id.gradable_tv);
            all_activity_tv =itemView.findViewById(R.id.all_activity_tv);
            score_pb =itemView.findViewById(R.id.score_pb);
            linearLayout = itemView.findViewById(R.id.subject_performance_view);



        }
    }
}
