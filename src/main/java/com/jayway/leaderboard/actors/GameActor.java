package com.jayway.leaderboard.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import com.jayway.leaderboard.messages.ReportScoreMessage;
import com.jayway.leaderboard.messages.RequestTopScoreMessage;

/**
 * This is the main actor in the game. It is responsible for maintaining and
 * reporting the scores of the player. Before each score is reported the access
 * token given in the message must be validated.
 */
public class GameActor extends UntypedActor {

    private final ActorRef usersActor;

    public GameActor(ActorRef usersActor) {
        this.usersActor = usersActor;
    }

    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof ReportScoreMessage) {
            // Report the score
        } else if (o instanceof RequestTopScoreMessage) {
            // Return the top scores for the level to `context().sender()`
        } else {
            unhandled(o);
        }
    }
}
