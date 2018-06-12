package com.example.a201619060353.atividadeextra2.modelo;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent it) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        Intent intent = new Intent(context, GastosMensais.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,0);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification("Pagamento de contas",
                "Você tem uma conta com vencimento para hoje.", pendingIntent);
        notificationHelper.getNotificationManager().notify(1, nb.build());
    }
}
