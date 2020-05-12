package com.example.safnow.model;



import android.content.Context;
import android.util.Log;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;


import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;



import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;



public class SafnowAppDaoImpl implements SafnowAppDao {

    private Context context;
    private final String URL_API = "https://safnow.azurewebsites.net/rest/";
    private static SafnowAppDaoImpl safnowAppDaoImpl;

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

    public static SafnowAppDaoImpl getInstance(Context context) {
        if (safnowAppDaoImpl == null) {
            safnowAppDaoImpl = new SafnowAppDaoImpl(context);
        }
        return safnowAppDaoImpl;
    }

    @Override
    public void storeUser(final User user, Response.Listener listener, Response.ErrorListener errorListener) {
        RequestQueue queue = Volley.newRequestQueue(context);

        Map<String, String> params = new HashMap();
        params.put("name", user.getName());
        params.put("phoneNumber", user.getPhoneNumber());
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, URL_API+"store/user", parameters, listener, errorListener);
        queue.add(jsonRequest);
    }

    @Override
    public void sendVerificationCode(final User user, final String code, Response.Listener listener, Response.ErrorListener errorListener) {
        RequestQueue queue = Volley.newRequestQueue(context);

        Map<String, String> params = new HashMap();
        params.put("phoneNumber", user.getPhoneNumber());
        params.put("verificationCode", code);

        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, URL_API+"login", parameters, listener, errorListener);
        Log.d("administrador", jsonRequest.toString());
        queue.add(jsonRequest);
    }
}
