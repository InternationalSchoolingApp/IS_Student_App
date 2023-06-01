package com.isapp.isstudentapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.isapp.isstudentapp.R;
import com.isapp.isstudentapp.activity.AssignedAdminRecentChat;
import com.isapp.isstudentapp.activity.AssignedTeachersActivity;
import com.isapp.isstudentapp.activity.ChatAdminActivity;
import com.isapp.isstudentapp.activity.RecentChatActivity;


public class ChatFragment extends Fragment {

    RelativeLayout relativeLayout , admin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        relativeLayout = view.findViewById(R.id.teacher_row);
        admin = view.findViewById(R.id.admin_row);

        admin.setOnClickListener(v->{
            Intent intent = new Intent(v.getContext(), ChatAdminActivity.class);
            startActivity(intent);
        });
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RecentChatActivity.class);
                startActivity(intent);
            }
        });

        return view ;

    }
}