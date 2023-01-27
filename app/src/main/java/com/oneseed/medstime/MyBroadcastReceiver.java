package com.oneseed.medstime;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // create the notification here
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyMeds")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("MedsTime")
                .setContentText("Напоминание о приеме лекарств!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                //request permission
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
            return;
        }
        notificationManager.notify(200, builder.build());

    }
}




