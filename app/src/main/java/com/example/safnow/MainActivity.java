package com.example.safnow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkToken();
        setContentView(R.layout.activity_map);
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
    }

}
