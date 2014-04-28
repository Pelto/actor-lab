package com.jayway.leaderboard.messages;

/**
 * This is a request that is sent to the Users actor once we want to sign in a
 * user. once a user is signed in a <code>AccessToken</code> is returned that
 * can later be used ot verify the user with.
 */
public class LoginUserMessage {

    private String name;

    public LoginUserMessage(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }
}
