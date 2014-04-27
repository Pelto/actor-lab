package com.jayway.leaderboard.dto;

public class User {
    private String accessToken;

    public User(String accessToken) {
        this.accessToken = accessToken;
    }

    public String accessToken() { return accessToken; }
}
