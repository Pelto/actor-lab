package com.jayway.leaderboard.health;

import akka.actor.ActorSystem;
import com.codahale.metrics.health.HealthCheck;

public class ActorSystemRunning extends HealthCheck {

    private final ActorSystem system;

    public ActorSystemRunning(ActorSystem system) {
        this.system = system;
    }

    @Override
    protected Result check() throws Exception {
        return system.isTerminated()
                ? Result.unhealthy("The actor system is terminated")
                : Result.healthy();
    }
}
