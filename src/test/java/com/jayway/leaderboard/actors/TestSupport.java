package com.jayway.leaderboard.actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.jayway.leaderboard.messages.UserVerifiedResponse;

public class TestSupport {

    private static class MockUserActor extends UntypedActor {

        private final UserVerifiedResponse response;

        public MockUserActor(UserVerifiedResponse response) {
            this.response = response;
        }

        @Override
        public void onReceive(Object message) throws Exception {
            sender().tell(response, getSelf());
        }
    }

    protected ActorRef createUserMock(ActorSystem system,  UserVerifiedResponse response) {
        return system.actorOf(Props.create(MockUserActor.class, response));
    }
}
