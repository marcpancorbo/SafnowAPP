package com.example.safnow;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Timer;
import java.util.TimerTask;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;


public class AskNotificationTimer {

    private Activity activity;
    private long time;
    private Timer timer;
    private TimerTask task;
    private Timer timerNotification;
    private TimerTask taskNotification;
    private static AskNotificationTimer askNotificationTimer;


    public static AskNotificationTimer getInstance(Activity activity, long time) {
        askNotificationTimer = new AskNotificationTimer(activity, time);
        return askNotificationTimer;
    }

    public static AskNotificationTimer getInstance() {
        return askNotificationTimer;
    }


    private AskNotificationTimer(final Activity activity, long time) {
        this.activity = activity;
        this.time = time;
        this.timer = new Timer();
        this.task = new TimerTask() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void run() {
                createNotification(activity);
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

    public void cancelCheckTime(){
        timerNotification.cancel();
        timerNotification.purge();
    }


    /**
     * (Provisional todavia) Metodo que permite crear la notificacion de pregunta que se ejecutara cada cierto tiempo
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void createNotification(Activity activity) {
        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel("1", "pruebaCanal", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = activity.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        //Intent prepared for YES button
        Intent cancelIntent = new Intent(activity, NotificationBroadcastReceiver.class);
        cancelIntent.setAction("NotificationBroadcastReceiver");
        cancelIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);
        PendingIntent snoozePendingIntent =
                PendingIntent.getBroadcast(activity, 0, cancelIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(activity, "1")
                .setSmallIcon(R.drawable.contacts_icon)
                .setContentTitle("prueba")
                .setContentText("¿Estás bien?")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(R.drawable.contacts_icon, "SI", snoozePendingIntent);

        final NotificationManagerCompat notificationManager = NotificationManagerCompat.from(activity);
        notificationManager.notify(1, builder.build());

        // Prepare the timetask when user no responds
        timerNotification = new Timer();
        taskNotification = new TimerTask() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void run() {
                Log.d("administrador","Alarma!!");
                notificationManager.cancel(1);
                cancelCheckTime();
                cancelNotification();
            }
        };
        timerNotification.schedule(taskNotification, 30000);
    }


}
