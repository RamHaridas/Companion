package com.whitehorse.dailydiary.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.whitehorse.dailydiary.MainActivity;
import com.whitehorse.dailydiary.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotificationSeriveFirebase extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //super.onMessageReceived(remoteMessage);
        showNotification(remoteMessage.getNotification().getBody().toString());
    }

    public void showNotification (String message){
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,new Intent(this, MainActivity.class),0);
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_faces)
                .setContentTitle("TEST TITLE")
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0,notification);
    }
}
