package com.jayway.leaderboard.dto;

import java.util.UUID;

public class AccessToken {

    private final UUID key;

    public AccessToken() {
        this.key = UUID.randomUUID();
    }

    public AccessToken(UUID key) {
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

    public static AccessToken fromString(String token) {
        UUID accessToken = UUID.fromString(token);
        return new AccessToken(accessToken);
    }
}
