package com.example.safnow.model;

import java.util.ArrayList;
import java.util.List;

public class  Alert {

    private String name;
    private String phoneDest;
    private User usuario;
    private Ubication ubication;
    public List<String> To = new ArrayList<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneDest() {
        return phoneDest;
    }

    public void setPhoneDest(String phoneDest) {
        this.phoneDest = phoneDest;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public Ubication getUbication() {
        return ubication;
    }

    public void setUbication(Ubication ubication) {
        this.ubication = ubication;
    }

    public List<String> getTo() {
        return To;
    }

    public void setTo(List<String> to) {
        To = to;
    }
}
