package com.jayway.leaderboard.dto;

import java.util.UUID;

/**
 * This is a access token that holds a key that is generated
 * by our user actor.
 */
public class AccessToken {

    private final UUID key;

    private AccessToken() {
        this.key = UUID.randomUUID();
    }

    private AccessToken(UUID key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccessToken that = (AccessToken) o;

        if (!key.equals(that.key)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    public UUID key() {
        return key;
    }

    public static AccessToken newToken() {
        return new AccessToken();
    }

    public static AccessToken fromString(String token) {
        UUID accessToken = UUID.fromString(token);
        return new AccessToken(accessToken);
    }
}
