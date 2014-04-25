package com.jayway.leaderboard;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.JavaTestKit;
import com.jayway.leaderboard.actors.GameActor;
import com.jayway.leaderboard.dto.Level;
import com.jayway.leaderboard.dto.Score;
import com.jayway.leaderboard.messages.ReportScore;
import com.jayway.leaderboard.messages.RequestTopScore;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GameTests {

    private ActorRef gameActor;

    private ActorSystem system;

    @Before
    public void setupSystem() {
        this.system = ActorSystem.create("game");
        this.gameActor = system.actorOf(Props.create(GameActor.class));
    }

    @After
    public void killSystem() {
        JavaTestKit.shutdownActorSystem(system);
    }

    @Test
    public void report_score_returns_no_message() {
        new JavaTestKit(system) {{
            new Within(duration("1 second")) {
                @Override
                protected void run() {
                    gameActor.tell(new ReportScore(new Score(new Level("level"), "me", 100)), getRef());
                    expectNoMsg();
                }
            };
        }};
    }

    @Test
    public void possible_to_report_scores_and_request_the_top() {
        final Level level = new Level("level 1");

        final Score topScore = new Score(level, "me", 100);
        final Score middleScore = new Score(level, "me", 100);
        final Score lowestScore = new Score(level, "me", 100);

        new JavaTestKit(system) {{
            new Within(duration("3 seconds")) {
                @Override
                protected void run() {

                    gameActor.tell(new ReportScore(middleScore), ActorRef.noSender());
                    gameActor.tell(new ReportScore(topScore), ActorRef.noSender());
                    gameActor.tell(new ReportScore(lowestScore), ActorRef.noSender());
                    gameActor.tell(new RequestTopScore(level), getRef());

                    expectMsgEquals(topScore);
                }
            };
        }};
    }
}
