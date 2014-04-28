package com.jayway.leaderboard.messages;

import com.google.common.base.Optional;

public class UserVerifiedResponse {

    private final Optional<String> verifiedUser;

    private UserVerifiedResponse(Optional<String> verifiedUser) {
        this.verifiedUser = verifiedUser;
    }

    public Optional<String> verifiedUser() {
        return verifiedUser;
    }

    public static UserVerifiedResponse verified(String user) {
        return new UserVerifiedResponse(Optional.of(user));
    }

    public static UserVerifiedResponse notVerified() {
        return new UserVerifiedResponse(Optional.<String>absent());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserVerifiedResponse that = (UserVerifiedResponse) o;

        if (!verifiedUser.equals(that.verifiedUser)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return verifiedUser.hashCode();
    }
}
