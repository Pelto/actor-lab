package com.jayway.leaderboard.messages;

import com.jayway.leaderboard.dto.AccessToken;

/**
 * This message is sent to the User actor to validate a
 * <code>AccessToken</code> and when it is validated a
 * <code>UserVerifiedResponse</code> will be returned to the sender.
 */
public class VerifyAccessTokenMessage {

    private final AccessToken accessToken;

    public VerifyAccessTokenMessage(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    public AccessToken accessToken() { return accessToken; }
}
