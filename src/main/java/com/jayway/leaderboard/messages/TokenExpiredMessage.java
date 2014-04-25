package com.jayway.leaderboard.messages;

public class TokenExpiredMessage {

    private final String user;

    public TokenExpiredMessage(String user) {
        this.user = user;
    }

    public String user() {
        return user;
    }
}
