package com.example.safnow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView name;
    private TextView phoneNumber;
    private Button btSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.tvName);
        phoneNumber = findViewById(R.id.tvPhone);
        btSend = findViewById(R.id.btSend);
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkFields()){

                }
            }
        });
    }

    public boolean checkFields(){
        if (TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(phoneNumber.getText())){
            Toast.makeText(this,R.string.InputToast,Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
