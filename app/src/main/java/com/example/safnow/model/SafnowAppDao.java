package com.example.safnow.model;

public interface SafnowAppDao {


    User getUser(String identifier);
    void storeUser(User user);
    void storeAlert(Alert alert);
    void deleteAlert(Alert alert);



}
