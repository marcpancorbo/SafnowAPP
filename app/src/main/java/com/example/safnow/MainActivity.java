package com.example.safnow;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;


import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {
    FragmentPagerAdapter pageAdapter;
    static Context context;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigator);
        ViewPager viewPager = findViewById(R.id.vpPager);
        pageAdapter = new PageAdapter(getSupportFragmentManager(),getApplicationContext());
        viewPager.setAdapter(pageAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        context = getContext();
        showNotification();
        //checkToken();
    }


    /**
     * Method that allows to check if exists a user token and decides which activity display
     */
    private void checkToken() {
          PreferencesController preferencesController = PreferencesController.getInstance();
         if (preferencesController.getToken(MainActivity.this) == null) {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showNotification() {
        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel("1", "pruebaCanal", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.mipmap.safnow_logo_round)
                .setContentTitle("prueba")
                .setContentText("prueba")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
    }
    public  Context getContext(){
        return context;
    }

}
