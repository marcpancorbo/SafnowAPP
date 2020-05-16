package com.example.safnow;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.safnow.model.Alert;
import com.example.safnow.model.SafnowAppDao;
import com.example.safnow.model.SafnowAppDaoImpl;
import com.example.safnow.model.Ubication;
import com.example.safnow.model.User;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {
    FragmentPagerAdapter pageAdapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigator);
        ViewPager viewPager = findViewById(R.id.vpPager);
        pageAdapter = new PageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pageAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        checkToken();
        /*
        SafnowAppDao safnowAppDao = SafnowAppDaoImpl.getInstance(this);

        safnowAppDao.storeAlert(alert, new Response.Listener() {
            @Override
            public void onResponse(Object response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
         */
        //AskNotificationTimer notification = new AskNotificationTimer(this, 5000);
    }

    /**
     * Method that allows to check if exists a user token and decides which activity display
     */
    private void checkToken() {
        PreferencesController preferencesController = PreferencesController.getInstance();
       /* if (preferencesController.getToken(MainActivity.this) == null) {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
        */
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }



}
