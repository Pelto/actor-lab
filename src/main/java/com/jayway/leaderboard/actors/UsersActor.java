package com.jayway.leaderboard.actors;

import akka.actor.UntypedActor;
import com.jayway.leaderboard.messages.LoginUser;
import com.jayway.leaderboard.messages.VerifyUser;

/**
 * This actor handles the verification of the user' access keys
 */
public class UsersActor extends UntypedActor {

    public final long tokenTimeInMillis;

    public UsersActor(long tokenTimeInMillis) {
        this.tokenTimeInMillis = tokenTimeInMillis;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof LoginUser) {

        } else if (message instanceof VerifyUser) {

        } else {
            unhandled(message);
        }
    }
}
