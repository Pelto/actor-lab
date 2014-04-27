package com.jayway.leaderboard.actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.JavaTestKit;
import com.jayway.leaderboard.dto.AccessToken;
import com.jayway.leaderboard.dto.Level;
import com.jayway.leaderboard.dto.Score;
import com.jayway.leaderboard.dto.TopScores;
import com.jayway.leaderboard.messages.ReportScore;
import com.jayway.leaderboard.messages.RequestTopScore;
import com.jayway.leaderboard.messages.UserVerifiedResponse;
import com.jayway.leaderboard.messages.VerifyUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class GameTests extends TestSupport{

    private ActorSystem system;

    @Before
    public void setupSystem() {
        this.system = ActorSystem.create("game");
    }

    @After
    public void killSystem() {
        JavaTestKit.shutdownActorSystem(system);
    }

    @Test
    public void report_score_returns_no_message() {
        new JavaTestKit(system) {{
            new Within(duration("2 second")) {
                @Override
                protected void run() {
                    ActorRef userActor = createUserMock(system, UserVerifiedResponse.Valid);
                    ActorRef gameActor = system.actorOf(Props.create(GameActor.class, userActor));
                    gameActor.tell(new ReportScore(new Level("level"), new Score(new AccessToken(), 100)), ActorRef.noSender());
                    expectNoMsg();
                }
            };
        }};
    }

    @Test
    public void asks_the_user_service_if_the_accesstoken_is_valid() {
        new JavaTestKit(system) {{
            new Within(duration("3 seconds")) {
                @Override
                protected void run() {
                    Level level = new Level("level");

                    ActorRef gameActor = system.actorOf(Props.create(GameActor.class, getRef()));
                    ReportScore message = new ReportScore(new Level("level"), new Score(new AccessToken(), 100));

                    gameActor.tell(message, getRef());

                    // The user is verified with our user actor
                    VerifyUser verificationRequest = expectMsgClass(VerifyUser.class);
                    assertThat(verificationRequest.accessToken(), equalTo(message.score().accessToken()));
                    reply(UserVerifiedResponse.Valid);

                    // We sleep for a while to allow all our high scores to be processed.
                    // If you do block the game actor when you do the verification of the
                    // access token this won't matter. However if you choose to do a future
                    // instead then all the scores might not have been processed yet.
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    gameActor.tell(new RequestTopScore(level), getRef());
                    TopScores topScores = expectMsgClass(TopScores.class);

                    assertThat(topScores.scores().size(), equalTo(1));
                }
            };
        }};
    }

    @Test
    public void do_not_add_the_score_if_not_validated() {
        new JavaTestKit(system) {{
            new Within(duration("3 seconds")) {
                @Override
                protected void run() {
                    Level level = new Level("level");

                    ActorRef userActor = createUserMock(system, UserVerifiedResponse.Invalid);
                    ActorRef gameActor = system.actorOf(Props.create(GameActor.class, userActor));
                    ReportScore message = new ReportScore(level, new Score(new AccessToken(), 100));

                    gameActor.tell(message, getRef());

                    // We sleep for a while to allow all our high scores to be processed.
                    // If you do block the game actor when you do the verification of the
                    // access token this won't matter. However if you choose to do a future
                    // instead then all the scores might not have been processed yet.
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    gameActor.tell(new RequestTopScore(level), getRef());
                    TopScores topScores = expectMsgClass(TopScores.class);

                    assertThat(topScores.scores().size(), equalTo(0));
                }
            };
        }};
    }

    @Test
    public void only_the_highest_score_per_user_is_saved() {
        new JavaTestKit(system) {{
            new Within(duration("3 seconds")) {
                @Override
                protected void run() {
                    Level level = new Level("level");

                    ActorRef userActor = createUserMock(system, UserVerifiedResponse.Valid);
                    ActorRef gameActor = system.actorOf(Props.create(GameActor.class, userActor));

                    ReportScore lowScore = new ReportScore(level, new Score(new AccessToken(), 5));
                    ReportScore highScore = new ReportScore(level, new Score(lowScore.score().accessToken(), 10));

                    gameActor.tell(lowScore, getRef());
                    gameActor.tell(highScore, getRef());
                    gameActor.tell(lowScore, getRef());

                    // We sleep for a while to allow all our high scores to be processed.
                    // If you do block the game actor when you do the verification of the
                    // access token this won't matter. However if you choose to do a future
                    // instead then all the scores might not have been processed yet.
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    gameActor.tell(new RequestTopScore(level), getRef());
                    TopScores topScores = expectMsgClass(TopScores.class);

                    assertThat(topScores.scores().size(), equalTo(1));
                    assertThat(topScores.scores().first().score(), equalTo(10));
                }
            };
        }};
    }

    @Test
    public void only_fifteen_highest_are_saved() {
        new JavaTestKit(system) {{
            new Within(duration("3 seconds")) {
                @Override
                protected void run() {
                    Level level = new Level("level");

                    ActorRef userActor = createUserMock(system, UserVerifiedResponse.Valid);
                    ActorRef gameActor = system.actorOf(Props.create(GameActor.class, userActor));

                    for (int i = 0; i <= 20; i++) {
                        ReportScore message = new ReportScore(level, new Score(new AccessToken(), i));
                        gameActor.tell(message, getRef());
                    }

                    // We sleep for a while to allow all our high scores to be processed.
                    // If you do block the game actor when you do the verification of the
                    // access token this won't matter. However if you choose to do a future
                    // instead then all the scores might not have been processed yet.
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    gameActor.tell(new RequestTopScore(level), getRef());
                    TopScores topScores = expectMsgClass(TopScores.class);

                    assertThat(topScores.scores().size(), equalTo(15));
                    assertThat(topScores.scores().first().score(), equalTo(6));
                    assertThat(topScores.scores().last().score(), equalTo(20));
                }
            };
        }};
    }
}
