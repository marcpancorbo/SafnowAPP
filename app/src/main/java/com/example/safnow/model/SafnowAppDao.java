package com.example.safnow.model;

import com.android.volley.Response;

public interface SafnowAppDao {


    User getUser(String identifier);
    void storeUser(User user, Response.Listener listener, Response.ErrorListener errorListener);
    void storeAlert(Alert alert);
    void deleteAlert(Alert alert);



}
