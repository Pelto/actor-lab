package com.jayway.leaderboard.actors;

import akka.actor.UntypedActor;
import com.jayway.leaderboard.dto.AccessToken;
import com.jayway.leaderboard.messages.TokenExpiredMessage;
import com.jayway.leaderboard.messages.UserVerifiedResponse;
import com.jayway.leaderboard.messages.VerifyAccessTokenMessage;
import scala.concurrent.duration.FiniteDuration;

import java.util.concurrent.TimeUnit;

public class SessionActor extends UntypedActor {

    private final String user;

    private final AccessToken accessToken;

    private final long timeToLiveInMillis;

    public SessionActor(String user, AccessToken accessToken, long timeToLiveInMillis) {
        this.user = user;
        this.accessToken = accessToken;
        this.timeToLiveInMillis = timeToLiveInMillis;
    }

    @Override
    public void preStart() throws Exception {
        context().system().scheduler().scheduleOnce(
                new FiniteDuration(timeToLiveInMillis, TimeUnit.MILLISECONDS),
                context().parent(),
                new TokenExpiredMessage(user),
                context().system().dispatcher(),
                self());
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof VerifyAccessTokenMessage) {
            VerifyAccessTokenMessage verifyUserRequest = (VerifyAccessTokenMessage)message;

            UserVerifiedResponse response = (verifyUserRequest.accessToken().equals(accessToken))
                    ? UserVerifiedResponse.verified(user)
                    : UserVerifiedResponse.notVerified();

            sender().tell(response, self());
        } else {
            unhandled(message);
        }
    }
}
