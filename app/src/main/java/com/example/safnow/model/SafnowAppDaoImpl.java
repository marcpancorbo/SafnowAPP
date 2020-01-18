package com.example.safnow.model;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.safnow.MainActivity;
import com.example.safnow.MapActivity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SafnowAppDaoImpl implements SafnowAppDao{

    private Context context;

    public SafnowAppDaoImpl(Context context) {
        this.context = context;
    }

    @Override
    public User getUser(String identifier) {
        return null;
    }

    @Override
    public void storeAlert(Alert alert) {

    }

    @Override
    public void deleteAlert(Alert alert) {

    }

    private static SafnowAppDaoImpl safnowAppDaoImpl;
    private final String URL_API = "http://safnow.dynu.net/Safnow/rest/store/user";


    public static SafnowAppDaoImpl getInstance(Context context){
        if(safnowAppDaoImpl == null){
            safnowAppDaoImpl = new SafnowAppDaoImpl(context);
        }
        return safnowAppDaoImpl;
    }

    @Override
    public void storeUser(final User user) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JSONObject object = new JSONObject();
        try {
            object.put("name",user.getName());
            object.put("phoneNumber",user.getPhoneNumber());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL_API, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Intent intent = new Intent(context, MapActivity.class);
                        context.startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("marc",error.getMessage());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
