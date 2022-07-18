package com.example.doan_food.Login;

import java.io.Serializable;

public class User implements Serializable {
    public User() {
    }

    public User(String tenND, String email) {
        this.tenND = tenND;
        this.email = email;
    }
    String tenND;
    String email;


    @Override
    public String toString() {
        return "User{" +
                "tenND='" + tenND + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String gettenND() {
        return tenND;
    }

    public void settenND(String tenND) {
        this.tenND = tenND;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
