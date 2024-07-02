package org.example.pacman;

import java.util.LinkedHashMap;

public class User {
    private String username, password, scoreSeries;
    static public LinkedHashMap<String, User> signedUpUsers;

    public User() {
    }

    public User(String username, String password, String scoreSeries) {
        this.username = username;
        this.password = password;
        this.scoreSeries = scoreSeries;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getScoreSeries() {
        return scoreSeries;
    }
}
