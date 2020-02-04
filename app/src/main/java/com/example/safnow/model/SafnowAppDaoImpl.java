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

import com.example.safnow.PreferencesController;


import java.util.HashMap;
import java.util.Map;



public class SafnowAppDaoImpl implements SafnowAppDao {

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
    private final String URL_API = "http://10.0.2.2:8080/rest/";


    public static SafnowAppDaoImpl getInstance(Context context) {
        if (safnowAppDaoImpl == null) {
            safnowAppDaoImpl = new SafnowAppDaoImpl(context);
        }
        return safnowAppDaoImpl;
    }

    @Override
    public void storeUser(final User user) {
        final PreferencesController preferencesController = PreferencesController.getInstance();
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL_API + "login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("administrador", response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR: =================" + error);
                Log.d("administrador", error.getMessage());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Log.d("administrador", "coge params");
                Map<String, String> params = new HashMap<>();
                params.put("phoneNumber", user.getPhoneNumber());
                //params.put("verificationCode", "1234");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        queue.add(request);
        Log.d("administrador", request.toString());
    }
}
