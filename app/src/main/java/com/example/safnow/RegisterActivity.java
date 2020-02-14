package com.example.safnow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.safnow.model.SafnowAppDaoImpl;
import com.example.safnow.model.User;

import org.json.JSONException;
import org.json.JSONObject;


public class RegisterActivity extends AppCompatActivity {

    private TextView name;
    private TextView phoneNumber;
    private Button btSend;
    private ProgressBar pbMain;
    private boolean name_set = false;
    private TextView code;
    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pbMain = findViewById(R.id.pbMain);
        name = findViewById(R.id.tvName);
        phoneNumber = findViewById(R.id.tvPhone);
        btSend = findViewById(R.id.btSend);
        code = findViewById(R.id.tvCode2);
        code.setEnabled(false);
        code.setVisibility(View.INVISIBLE);
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!name_set){
                    if(checkFieldsName()){
                        pbMain.setVisibility(View.VISIBLE);
                        SafnowAppDaoImpl safnowAppDaoImp = SafnowAppDaoImpl.getInstance(RegisterActivity.this);
                        user.setName(name.getText().toString());
                        user.setPhoneNumber(phoneNumber.getText().toString());
                        Log.d("administrador",user.getPhoneNumber());
                        safnowAppDaoImp.storeUser(user, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("administrador", response.toString());
                                pbMain.setVisibility(View.INVISIBLE);
                                phoneNumber.setVisibility(View.INVISIBLE);
                                phoneNumber.setEnabled(false);
                                name.setVisibility(View.INVISIBLE);
                                name.setEnabled(false);
                                code.setEnabled(true);
                                code.setVisibility(View.VISIBLE);
                                name_set = true;
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //Log.d("administrador", error.getMessage());
                            }
                        });
                    }
                }else{
                    if(checkFieldCode()){
                        final PreferencesController preferencesController = PreferencesController.getInstance();
                        SafnowAppDaoImpl safnowAppDaoImp = SafnowAppDaoImpl.getInstance(RegisterActivity.this);
                        safnowAppDaoImp.sendVerificationCode(user, code.getText().toString(), new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                String token = null;
                                try {
                                    token = response.getString("token");
                                    Log.d("administrador", token);
                                    preferencesController.addToken(RegisterActivity.this, token);
                                    finish();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("administrador", "ERROR: "+error.getMessage());
                            }
                        });
                    }
                }
            }
        });
    }

    public boolean checkFieldsName(){
        if (TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(phoneNumber.getText())){
            Toast.makeText(this,R.string.InputToast,Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    public boolean checkFieldCode(){
        if (TextUtils.isEmpty(code.getText())){
            Toast.makeText(this,R.string.InputToast,Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }





}
