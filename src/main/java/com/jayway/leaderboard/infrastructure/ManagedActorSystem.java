package com.jayway.leaderboard.infrastructure;

import akka.actor.ActorSystem;
import io.dropwizard.lifecycle.Managed;

public class ManagedActorSystem implements Managed {

    private final ActorSystem system;

    public ManagedActorSystem(ActorSystem system) {
        this.system = system;
    }

    @Override
    public void start() throws Exception {
        // The system is already started
    }

    @Override
    public void stop() throws Exception {
        system.shutdown();
    }
}
