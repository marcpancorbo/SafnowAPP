package com.example.safnow.model;

import com.android.volley.Response;

public interface SafnowAppDao {


    User getUser(String identifier);
    void storeUser(User user, Response.Listener listener, Response.ErrorListener errorListener);
    void storeAlert(Alert alert, Response.Listener listener, Response.ErrorListener errorListener);
    void deleteAlert(Alert alert);
    void sendVerificationCode(User user, String code, Response.Listener listener, Response.ErrorListener errorListener);



}
