package com.example.safnow;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Timer;
import java.util.TimerTask;


public class AskNotificationTimer {

    private Activity activity;
    private long time;
    private Timer timer;
    private TimerTask task;
    private static AskNotificationTimer askNotificationTimer;


    public static AskNotificationTimer getInstance(Activity activity, long time) {
        askNotificationTimer = new AskNotificationTimer(activity, time);
        return askNotificationTimer;
    }

    public static AskNotificationTimer getInstance(Activity activity) {
        return askNotificationTimer;
    }


    private AskNotificationTimer(Activity activity, long time) {
        this.activity = activity;
        this.time = time;
        this.timer = new Timer();
        this.task = new TimerTask() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void run() {
                createNotification();
            }
        };
        timer.schedule(task, time, time);
    }

    /**
     * Metodo que permite cancelar la ejecucion de la notificacion
     */
    public void cancelNotification() {
        this.timer.cancel();
        this.timer.purge();
    }


    /**
     * (Provisional todavia) Metodo que permite crear la notificacion de pregunta que se ejecutara cada cierto tiempo
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void createNotification() {
        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel("1", "pruebaCanal", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = activity.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(activity, "1")
                .setSmallIcon(R.drawable.contacts_icon)
                .setContentTitle("prueba")
                .setContentText("prueba")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(activity);
        notificationManager.notify(1, builder.build());
    }


}
