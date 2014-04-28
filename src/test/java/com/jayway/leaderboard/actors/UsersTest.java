package com.jayway.leaderboard.actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.JavaTestKit;
import com.jayway.leaderboard.dto.AccessToken;
import com.jayway.leaderboard.messages.LoginUserMessage;
import com.jayway.leaderboard.messages.UserVerifiedResponse;
import com.jayway.leaderboard.messages.VerifyAccessTokenMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UsersTest {

    private ActorRef usersActor;

    private ActorSystem system;

    private long tokenTimeInMillis = 500;

    @Before
    public void setupSystem() {
        this.system = ActorSystem.create("game");
        this.usersActor = system.actorOf(Props.create(UsersActor.class, tokenTimeInMillis));
    }

    @After
    public void killSystem() {
        JavaTestKit.shutdownActorSystem(system);
    }

    @Test
    public void loginAndVerify() {
        new JavaTestKit(system) {{
            new Within(duration("1 second")) {
                @Override
                protected void run() {
                    usersActor.tell(new LoginUserMessage("test"), getRef());

                    AccessToken key = expectMsgClass(AccessToken.class);

                    usersActor.tell(new VerifyAccessTokenMessage(key), getRef());

                    expectMsgEquals(UserVerifiedResponse.verified("test"));
                }
            };
        }};
    }

    @Test
    public void wrongTokenIsInvalid() {
        new JavaTestKit(system) {{
            new Within(duration("1 second")) {
                @Override
                protected void run() {
                    usersActor.tell(new VerifyAccessTokenMessage(AccessToken.newToken()), getRef());
                    expectMsgEquals(UserVerifiedResponse.notVerified());
                }
            };
        }};
    }

    @Test
    public void tokenBecomesInvalid() {
        new JavaTestKit(system) {{
            new Within(duration("1 second")) {
                @Override
                protected void run() {
                    usersActor.tell(new LoginUserMessage("test"), getRef());

                    AccessToken key = expectMsgClass(AccessToken.class);

                    try {
                        Thread.sleep(tokenTimeInMillis + 200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    usersActor.tell(new VerifyAccessTokenMessage(key), getRef());

                    expectMsgEquals(UserVerifiedResponse.notVerified());
                }
            };
        }};
    }
}
