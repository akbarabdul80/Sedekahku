package com.sragen.sedekahku.Firebase;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sragen.sedekahku.R;
import com.sragen.sedekahku.SplashScreen;

import java.util.Map;
public class firebasenotifikasi extends FirebaseMessagingService {
    private static final String TAG = "TEST TEST";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData() != null) {
            sendNotif(remoteMessage);
        }
    }

    private void sendNotif(RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();
        String title = data.get("title");
        String content = data.get("body");
        Intent intent = new Intent(getApplicationContext(), SplashScreen.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "123";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "AdminSakti Notificaton", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("Notifikasi untuk pembayaran");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{NotificationCompat.DEFAULT_VIBRATE});
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                notificationChannel.setAllowBubbles(true);
            }
            notificationChannel.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);


        builder.setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(content)
                .setDefaults(NotificationCompat.DEFAULT_SOUND)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.ic_notif)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setContentIntent(resultPendingIntent)
                .setColor(getResources().getColor(R.color.dot_active_screen3));

        builder.setPriority(Notification.PRIORITY_HIGH);
        if (Build.VERSION.SDK_INT >= 21) builder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
        notificationManager.notify(Integer.parseInt(NOTIFICATION_CHANNEL_ID), builder.build());
    }


}
