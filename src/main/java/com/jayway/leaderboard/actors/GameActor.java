package com.jayway.leaderboard.actors;

import akka.actor.UntypedActor;
import com.jayway.leaderboard.messages.ReportScore;
import com.jayway.leaderboard.messages.RequestTopScore;

public class GameActor extends UntypedActor {

    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof ReportScore) {
            // Report the score
        } else if (o instanceof RequestTopScore) {
            // Return the top score for the level to `context().sender()`
        } else {
            unhandled(o);
        }
    }
}
