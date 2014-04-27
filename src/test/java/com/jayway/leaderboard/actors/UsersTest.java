package com.jayway.leaderboard.actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.JavaTestKit;
import com.jayway.leaderboard.dto.AccessToken;
import com.jayway.leaderboard.messages.LoginUser;
import com.jayway.leaderboard.messages.UserVerifiedResponse;
import com.jayway.leaderboard.messages.VerifyUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UsersTest {

    private ActorRef usersActor;

    private ActorSystem system;

    @Before
    public void setupSystem() {
        this.system = ActorSystem.create("game");
        this.usersActor = system.actorOf(Props.create(UsersActor.class));
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
                    usersActor.tell(new LoginUser(), getRef());

                    AccessToken key = expectMsgClass(AccessToken.class);

                    usersActor.tell(new VerifyUser(key), getRef());

                    expectMsgEquals(UserVerifiedResponse.Valid);
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
                    usersActor.tell(new VerifyUser(new AccessToken()), getRef());
                    expectMsgEquals(UserVerifiedResponse.Invalid);
                }
            };
        }};
    }
}
