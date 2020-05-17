package com.example.safnow;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class NotificationBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "NotificationBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        AskNotificationTimer askNotificationTimer = AskNotificationTimer.getInstance();
        Log.d("administrador", "Ha llegado receiver");
        String type = intent.getType();
        if (type != null) {
            switch (type) {
                case "SI":
                    askNotificationTimer.cancelCheckTime();
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
