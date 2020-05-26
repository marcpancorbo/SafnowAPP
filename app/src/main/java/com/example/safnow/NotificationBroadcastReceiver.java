package com.example.safnow;

import android.content.BroadcastReceiver;
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
        AskNotificationTimerManager askNotificationTimerManager = AskNotificationTimerManager.getInstance();
        Log.d("administrador", "Ha llegado receiver");
        String type = intent.getType();
        if (type != null) {
            switch (type) {
                case "SI":
                    askNotificationTimerManager.cancelCheckTime();
                    askNotificationTimerManager.removeNotification();
                    break;
                case "NO":
                    askNotificationTimerManager.createAndSetAlarm();
                    break;
                default:
                    break;
            }
        }
    }
}
