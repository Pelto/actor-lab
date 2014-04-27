package com.jayway.leaderboard.messages;

import com.jayway.leaderboard.dto.AccessToken;

public class VerifyUser {

    private final AccessToken accessToken;

    public VerifyUser(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    public AccessToken accessToken() { return accessToken; }
}
