package com.example.safnow.model;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

public class SafnowAppDaoImpl implements SafnowAppDao{

    private static SafnowAppDaoImpl safnowAppDaoImpl;
    private final String URL_API = "http://safnow.dynu.net/Safnow/rest";


    public static SafnowAppDaoImpl getInstance(){
        if(safnowAppDaoImpl == null){
            safnowAppDaoImpl = new SafnowAppDaoImpl();
        }
        return safnowAppDaoImpl;
    }

    @Override
    public void storeUser(final User user) {
        Gson gson = new Gson();
        final String json = gson.toJson(user);
        RequestQueue queue = Volley.newRequestQueue();
        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

            @Override
            protected String getParamsEncoding() {
                return json;
            }
        };
        queue.add(request);

    }
}
