package com.jayway.leaderboard.messages;

import com.google.common.base.Optional;

/**
 * This is the response that the UsersActor sends when a
 * <code>VerifyAccessTokenMessage</code> message has been received. If there is
 * a user with the given <code>AccessToken</code> available a
 * <code>UserVerifiedResponse.verified(user)</code> is returned, otherwise
 * <code>UserVerifiedResponse.notVerified()</code> is returned.
 */
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
