package com.example.safnow;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NotificationBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "NotificationBroadcastReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("administrador", "Ha llegado receiver");
        AskNotificationTimer askNotificationTimer = AskNotificationTimer.getInstance();
        askNotificationTimer.cancelCheckTime();
    }
}
