package com.example.a201619060353.atividadeextra2.modelo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.v4.app.NotificationCompat;

import com.example.a201619060353.atividadeextra2.R;

public class NotificationHelper extends ContextWrapper {

    private NotificationManager notificationManager;
    private final String CANAL_ID = "Notificacao";

    public NotificationHelper(Context base) {
        super(base);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CANAL_ID,
                    "Channel", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationChannel.setLightColor(R.color.colorPrimary);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

            getNotificationManager().createNotificationChannel(notificationChannel);
        }
    }

    public NotificationManager getNotificationManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    public NotificationCompat.Builder getChannelNotification(String title, String message, PendingIntent intent) {
        return new NotificationCompat.
                Builder(getApplicationContext(), CANAL_ID).
                setContentTitle(title).
                setContentText(message).setSmallIcon(R.drawable.baseline_notification_important_black_18dp).
                setContentIntent(intent);
    }
}
