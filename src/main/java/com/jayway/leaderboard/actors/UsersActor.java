package com.jayway.leaderboard.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.jayway.leaderboard.messages.LoginUserMessage;
import com.jayway.leaderboard.messages.TokenExpiredMessage;
import com.jayway.leaderboard.messages.VerifyAccessTokenMessage;
import com.jayway.leaderboard.dto.AccessToken;
import com.jayway.leaderboard.messages.UserVerifiedResponse;
import scala.Option;

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
            LoginUserMessage loginMessage = (LoginUserMessage)message;
            AccessToken token = AccessToken.newToken();

            // Register a session
            context().actorOf(Props.create(SessionActor.class, loginMessage.name(), token, tokenTimeInMillis), token.key().toString());

            sender().tell(token, self());
        } else if (message instanceof VerifyAccessTokenMessage) {
            VerifyAccessTokenMessage verifyUserRequest = (VerifyAccessTokenMessage) message;
            Option<ActorRef> sessionActor = context().child(verifyUserRequest.accessToken().key().toString());

            if (sessionActor.isDefined()) {
                sessionActor.get().forward(message, context());
            } else {
                sender().tell(UserVerifiedResponse.notVerified(), self());
            }
        } else if (message instanceof TokenExpiredMessage) {
            context().stop(sender());
        } else {
            unhandled(message);
        }
    }
}
