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
        if (this.timer != null) {
            this.timer.cancel();
            this.timer.purge();
        }
    }

    /**
     * Method that allows to stop checking if the user responds in time
     */
    public void cancelCheckTime() {
        if (timerNotification != null) {
            timerNotification.cancel();
            timerNotification.purge();
        }
    }

    /**
     * Metodo que permite crear la notificacion de pregunta que se ejecutara cada cierto tiempo
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void createNotification(final Activity activity) {
        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel("1", "pruebaCanal", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = activity.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        final NotificationManagerCompat notificationManager = NotificationManagerCompat.from(activity);
        NotificationCompat.Builder builder = setNotificationBuilder();
        notificationManager.notify(1, builder.build());

        // Prepare the timetask when user no responds
        timerNotification = new Timer();
        taskNotification = new TimerTask() {
            @Override
            public void run() {
                Log.d("administrador", "Alarma!!");
                notificationManager.cancel(1);
                cancelCheckTime();
                cancelNotification();
                PreferencesController controller = PreferencesController.getInstance();
                controller.setTimerNotificationActive(activity, false);
            }
        };
        timerNotification.schedule(taskNotification, 30000);
    }

    /**
     * Method that allows to set the Notification Builder
     *
     * @return Returns the Notification Builder
     */
    private NotificationCompat.Builder setNotificationBuilder() {
        //Intent prepared for YES button
        Intent yesIntent = new Intent(activity, NotificationBroadcastReceiver.class);
        yesIntent.setAction("NotificationBroadcastReceiver");
        yesIntent.setType("SI");
        PendingIntent yesPendingIntent =
                PendingIntent.getBroadcast(activity, 0, yesIntent, 0);

        //Intent prepared for NO button
        Intent noIntent = new Intent(activity, NotificationBroadcastReceiver.class);
        noIntent.setAction("NotificationBroadcastReceiver");
        noIntent.setType("NO");
        PendingIntent noPendingIntent =
                PendingIntent.getBroadcast(activity, 0, noIntent, 0);

        return new NotificationCompat.Builder(activity, "1")
                .setSmallIcon(R.drawable.contacts_icon)
                .setContentTitle("IMPORTANTE")
                .setContentText("¿Estás bien?")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(R.drawable.contacts_icon, "SI", yesPendingIntent)
                .addAction(R.drawable.contacts_icon, "NO", noPendingIntent);
    }

}
