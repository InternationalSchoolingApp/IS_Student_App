package com.isapp.isstudentapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.isapp.isstudentapp.R;
import com.isapp.isstudentapp.model.SubjectPerformanceDetail;

import java.util.List;

public class ActivityListPerformanceAdapter extends RecyclerView.Adapter<ActivityListPerformanceAdapter.ViewHolderNew>{
    public ActivityListPerformanceAdapter(List<SubjectPerformanceDetail.Response.Enrollments.Enrollment.Grades.Items.Item> list) {
        this.list = list;
    }

    List<SubjectPerformanceDetail.Response.Enrollments.Enrollment.Grades.Items.Item> list;

    @NonNull
    @Override
    public ViewHolderNew onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.subject_wise_performance_recycler_view,parent,false);
        ViewHolderNew viewHolder =new ViewHolderNew(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderNew holder, int position) {
            holder.activityName.setText("Activity : \n"+list.get(position).getTitle());
            holder.dueDate.setText("Due : "+list.get(position).getDuedate());
            holder.subDate.setText("Submitted On : "+list.get(position).getSubmitteddate());
            holder.timeSpent.setText("Spent : "+list.get(position).getUnitTimeSpent());
            holder.submittedStatus.setText("Status : "+list.get(position).getSubmissionStatus());
            holder.score.setText("Score : "+list.get(position).getAchieved()+ "%");
            holder.grade.setText("Grade : "+list.get(position).getLetter());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolderNew extends RecyclerView.ViewHolder{

        TextView activityName, dueDate, subDate, timeSpent, submittedStatus, score, grade;
        public ViewHolderNew(@NonNull View itemView) {
            super(itemView);
            activityName = itemView.findViewById(R.id.activity_name_tv);
            dueDate = itemView.findViewById(R.id.due_date_tv);
            subDate = itemView.findViewById(R.id.submitted_date_tv);
            timeSpent = itemView.findViewById(R.id.unit_time_spent);
            submittedStatus = itemView.findViewById(R.id.submission_status);
            score = itemView.findViewById(R.id.score_in_rec);
            grade = itemView.findViewById(R.id.grade_in_rec);

        }
    }
}
