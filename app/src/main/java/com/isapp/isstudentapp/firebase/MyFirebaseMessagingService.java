package com.isapp.isstudentapp.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.isapp.isstudentapp.R;
import com.isapp.isstudentapp.activity.RecentChatActivity;
import com.isapp.isstudentapp.constant.Constants;
import com.isapp.isstudentapp.model.User;
import com.isapp.isstudentapp.preference.PreferenceManager;

import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private PreferenceManager preferenceManager;

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        preferenceManager = new PreferenceManager(getApplicationContext());
        preferenceManager.putString(Constants.FIREBASE_TOKEN, token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        User user = new User();
        user.id = message.getData().get(Constants.USER_EMAIL);
        user.name = message.getData().get(Constants.NAME);
        user.token = message.getData().get(Constants.FIREBASE_TOKEN);

        int notificationId = new Random().nextInt();
        String channelId = "chat_message";

        Intent intent = new Intent(this, RecentChatActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId);
        builder.setSmallIcon(R.drawable.bellnotify);
        builder.setContentTitle(user.name);
        builder.setContentText(message.getData().get(Constants.KEY_MESSAGE));
        builder.setStyle( new NotificationCompat.BigTextStyle().bigText(message.getData().get(Constants.KEY_MESSAGE)));
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence channelName = "Chat Message";
            String channelDescription = "This notification channel used for chat message";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setDescription(channelDescription);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationManagerCompat notificationManagerCompat =  NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(notificationId, builder.build());

    }


}
