package com.jayway.leaderboard.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import com.jayway.leaderboard.messages.ReportScore;
import com.jayway.leaderboard.messages.RequestTopScore;


public class GameActor extends UntypedActor {

    private final ActorRef usersActor;

    public GameActor(ActorRef usersActor) {
        this.usersActor = usersActor;
    }

    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof ReportScore) {
            // Report the score
        } else if (o instanceof RequestTopScore) {
            // Return the top scores for the level to `context().sender()`
        } else {
            unhandled(o);
        }
    }
}
