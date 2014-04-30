package com.jayway.leaderboard.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.dispatch.OnComplete;
import akka.pattern.Patterns;
import akka.util.Timeout;
import com.jayway.leaderboard.dto.Level;
import com.jayway.leaderboard.dto.Score;
import com.jayway.leaderboard.messages.ReportScoreMessage;
import com.jayway.leaderboard.messages.RequestTopScoreMessage;
import com.jayway.leaderboard.messages.UserVerifiedResponse;
import com.jayway.leaderboard.messages.VerifyAccessTokenMessage;
import scala.Option;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * This is the main actor in the game. It is responsible for maintaining and
 * reporting the scores of the player. Before each score is reported the access
 * token given in the message must be validated.
 */
public class GameActor extends UntypedActor {

    private final ActorRef usersActor;

    private final Props levelProps = Props.create(LevelActor.class);

    public GameActor(ActorRef usersActor) {
        this.usersActor = usersActor;
    }

    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof ReportScoreMessage) {
            reportScore((ReportScoreMessage)o);
        } else if (o instanceof RequestTopScoreMessage) {
            RequestTopScoreMessage message = (RequestTopScoreMessage)o;

            ActorRef level = actorForLevel(message.level());

            level.forward(message, context());
        } else {
            unhandled(o);
        }
    }

    private void reportScore(final ReportScoreMessage message) throws Exception {
        VerifyAccessTokenMessage verifyUserRequest = new VerifyAccessTokenMessage(message.accessToken());
        Timeout t = new Timeout(Duration.create(5, TimeUnit.SECONDS));
        Future<Object> verify = Patterns.ask(usersActor, verifyUserRequest, t);

        verify.andThen(
                reportScoreToLevel(actorForLevel(message.level()), message.score()),
                context().dispatcher());
    }

    private final OnComplete<Object> reportScoreToLevel(final ActorRef levelActor, final int score) {
        return new OnComplete<Object>() {
            @Override
            public void onComplete(Throwable failure, Object success) throws Throwable {
                UserVerifiedResponse response = (UserVerifiedResponse)success;

                if (response.verifiedUser().isPresent()) {
                    levelActor.tell(new Score(response.verifiedUser().get(), score), self());
                }
            }
        };
    }

    private ActorRef actorForLevel(Level level) {
        Option<ActorRef> child = context().child(level.name());

        if (child.isEmpty()) {
            return context().actorOf(levelProps, level.name());
        }

        return child.get();
    }
}
