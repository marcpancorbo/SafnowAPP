package com.example.safnow.model;

public class Alert {

    private String message;
    private String phoneDest;
    private User user;
    private Ubication ubication;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPhoneDest() {
        return phoneDest;
    }

    public void setPhoneDest(String phoneDest) {
        this.phoneDest = phoneDest;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Ubication getUbication() {
        return ubication;
    }

    public void setUbication(Ubication ubication) {
        this.ubication = ubication;
    }
}
