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


public class AskNotificationTimerManager {

    private Activity activity;
    private long time;
    private Timer timer;
    private TimerTask task;
    private Timer timerNotification;
    private TimerTask taskNotification;
    private static AskNotificationTimerManager askNotificationTimerManager;


    public static AskNotificationTimerManager getInstance(Activity activity, long time) {
        askNotificationTimerManager = new AskNotificationTimerManager(activity, time);
        return askNotificationTimerManager;
    }

    public static AskNotificationTimerManager getInstance() {
        return askNotificationTimerManager;
    }


    private AskNotificationTimerManager(final Activity activity, long time) {
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
     * Method that allows to cancel completely the notification and set the state to Alarm
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void createAndSetAlarm() {
        this.cancelCheckTime();
        this.cancelNotification();
        this.removeNotification();
        PreferencesController controller = PreferencesController.getInstance();
        controller.setTimerNotificationActive(activity, false);
        AlarmNotificationManager alarmNotificationManager = AlarmNotificationManager.getInstance(activity, true);
    }

    /**
     * Method that allows to remove the notification
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void removeNotification() {
        NotificationManager notificationManager = activity.getSystemService(NotificationManager.class);
        notificationManager.cancel(1);
    }

    /**
     * Metodo que permite crear la notificacion de pregunta que se ejecutara cada cierto tiempo
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void createNotification(final Activity activity) {
        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel("1", "TimerChannel", NotificationManager.IMPORTANCE_DEFAULT);
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
                createAndSetAlarm();
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
                .setContentTitle(activity.getApplicationContext().getString(R.string.important))
                .setContentText(activity.getApplicationContext().getString(R.string.are_ok))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setOngoing(true)
                .addAction(R.drawable.contacts_icon, activity.getApplicationContext().getString(R.string.yes), yesPendingIntent)
                .addAction(R.drawable.contacts_icon, activity.getApplicationContext().getString(R.string.no), noPendingIntent);
    }

}
