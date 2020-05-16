package com.example.safnow.model;



import android.content.Context;
import android.util.Log;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;


import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.safnow.PreferencesController;
import com.google.gson.Gson;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;



public class SafnowAppDaoImpl implements SafnowAppDao {

    private Context context;
    private final String URL_API = "http://192.168.65.1:45456/rest/";
    private static SafnowAppDaoImpl safnowAppDaoImpl;
    private PreferencesController preferencesController = PreferencesController.getInstance();
    public SafnowAppDaoImpl(Context context) {
        this.context = context;
    }

    @Override
    public User getUser(String identifier) {
        return null;
    }


    @Override
    public void storeAlert(String latitude, String longitude) {
        Alert alert = generateAlert(latitude, longitude);
        RequestQueue queue = Volley.newRequestQueue(context);
        Gson gson = new Gson();
        String j = gson.toJson(alert);
        try {
            JSONObject parameters = new JSONObject(j);
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, URL_API+"Alerta", parameters, null, null) { //no semicolon or coma
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", "Bearer "+preferencesController.getToken(context));
                    return params;
                }
            };
            queue.add(jsonRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, URL_API+"Usuario", parameters, listener, errorListener);
        queue.add(jsonRequest);
    }

    @Override
    public void sendVerificationCode(final User user, final String code, Response.Listener listener, Response.ErrorListener errorListener) {
        RequestQueue queue = Volley.newRequestQueue(context);

        Map<String, String> params = new HashMap();
        params.put("phoneNumber", user.getPhoneNumber());
        params.put("verificationCode", code);

        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, URL_API+"Login", parameters, listener, errorListener);
        Log.d("administrador", jsonRequest.toString());
        queue.add(jsonRequest);
    }

    @Override
    public Alert generateAlert(String latitude, String longitude) {
        Alert alert = new Alert();
        User user = new User();
        user.setPhoneNumber("654023488");
        alert.setUsuario(user);
        Ubication ubication = new Ubication();
        ubication.setAltitude(longitude);
        ubication.setLatitude(latitude);
        alert.getTo().add("654023488");
        alert.setUbication(ubication);
        return alert;
    }
}
