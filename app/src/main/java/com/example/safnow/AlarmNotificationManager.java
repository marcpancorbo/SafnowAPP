package com.example.safnow;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.safnow.model.SafnowAppDaoImpl;

public class AlarmNotificationManager {

    private static AlarmNotificationManager alarmNotificationManager;

    public static AlarmNotificationManager getInstance(Context context, boolean create) {
        if (create) {
            alarmNotificationManager = new AlarmNotificationManager(context);
        }
        return alarmNotificationManager;
    }

    private AlarmNotificationManager(Context context) {
        SafnowAppDaoImpl dao = SafnowAppDaoImpl.getInstance(context);
        dao.storeAlert(getLatitude(context), getLongitude(context));
        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel("2", "AlarmChannel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        final NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        NotificationCompat.Builder builder = setNotificationBuilder(context);
        notificationManager.notify(2, builder.build());
    }

    /**
     * Method that allows to get the current position of the user
     *
     * @param context Receive the context
     * @return Return the position or null
     */
    public String getLatitude(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // Check if there are permission for the gps.
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double latitude = location.getLatitude();
        return String.valueOf(latitude);
    }

    /**
     * Method that allows to get the current position of the user
     *
     * @param context Receive the context
     * @return Return the position or null
     */
    public String getLongitude(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // Check if there are permission for the gps.
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = location.getLongitude();
        return String.valueOf(longitude);
    }


    /**
     * Method that allows to set the Notification Builder
     *
     * @return Returns the Notification Builder
     */
    private NotificationCompat.Builder setNotificationBuilder(Context context) {
        return new NotificationCompat.Builder(context, "2")
                .setSmallIcon(R.drawable.contacts_icon)
                .setContentTitle("IMPORTANTE")
                .setContentText("SE HA ACTIVADO LA ALARMA")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }


}
