package com.example.safnow.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class  Alert {

    private String name;
    private String phoneDest;
    private User usuario;
    private Ubication ubication;
    public Set<String> To = new HashSet<>();


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

    public Set<String> getTo() {
        return To;
    }

    public void setTo(Set<String> to) {
        To = to;
    }
}
