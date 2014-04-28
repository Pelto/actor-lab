package com.jayway.leaderboard.actors;

import akka.actor.UntypedActor;
import com.jayway.leaderboard.messages.LoginUserMessage;
import com.jayway.leaderboard.messages.VerifyAccessTokenMessage;

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
        if (message instanceof LoginUserMessage) {

        } else if (message instanceof VerifyAccessTokenMessage) {

        } else {
            unhandled(message);
        }
    }
}
