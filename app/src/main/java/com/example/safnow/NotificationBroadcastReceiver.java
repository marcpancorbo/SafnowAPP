package com.example.safnow;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;


public class NotificationBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "NotificationBroadcastReceiver";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        AskNotificationTimer askNotificationTimer = AskNotificationTimer.getInstance();
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        Log.d("administrador", "Ha llegado receiver");
        String type = intent.getType();
        if (type != null) {
            switch (type) {
                case "SI":
                    askNotificationTimer.cancelCheckTime();
                    notificationManager.cancel(1);
                    break;
                case "NO":
                    Log.d("administrador", "ALARMA!!!");
                    break;
                default:
                    break;
            }
        }
    }
}
