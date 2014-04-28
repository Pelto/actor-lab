package com.jayway.leaderboard.messages;

public class LoginUser {

    private String name;

    public LoginUser(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }
}
