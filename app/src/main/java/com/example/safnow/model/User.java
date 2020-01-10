package com.example.safnow.model;

import java.util.ArrayList;
import java.util.List;

public class User extends Identifiable{

    private String name;
    private String phoneNumber;
    private List<Alert> alerts = new ArrayList<>();


}
