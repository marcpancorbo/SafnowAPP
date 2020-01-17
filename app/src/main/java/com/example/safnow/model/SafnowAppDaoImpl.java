package com.example.safnow.model;


import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.safnow.MainActivity;
import com.google.gson.Gson;

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
        Gson gson = new Gson();
        final String json = gson.toJson(user);
        Log.d("luis", json);
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("luis", "bien api");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("luis", error.getMessage());
                    }
                }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap headers = new HashMap();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }

                @Override
                protected String getParamsEncoding() {
                    return json;
                }
        };
        queue.add(request);
    }
}
