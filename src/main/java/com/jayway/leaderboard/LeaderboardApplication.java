package com.jayway.leaderboard;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.jayway.leaderboard.actors.GameActor;
import com.jayway.leaderboard.health.ActorSystemRunning;
import com.jayway.leaderboard.http.Scores;
import com.jayway.leaderboard.infrastructure.ManagedActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.io.File;

public class LeaderboardApplication extends Application<LeaderboardConfiguration> {

    public static void main(String[] args) throws Exception {
        new LeaderboardApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<LeaderboardConfiguration> bootstrap) {
    }

    @Override
    public void run(LeaderboardConfiguration configuration, Environment environment) throws Exception {
        // Create our actor system
        ActorSystem system = createActorSystem(configuration);

        // Set up our core actors
        ActorRef scoresActor = system.actorOf(Props.create(GameActor.class), "game");

        // Set up our resources
        Scores scoresResource = new Scores(system, scoresActor, configuration.requestTimeout());

        // Register our actor system and resources in Dropwizard
        environment.lifecycle().manage(new ManagedActorSystem(system));
        environment.jersey().register(scoresResource);

        // Create a health check for our Actor system
        environment.healthChecks().register("actor-system", new ActorSystemRunning(system));
    }

    private ActorSystem createActorSystem(LeaderboardConfiguration configuration) {
        Config config = ConfigFactory.parseFile(new File(configuration.akkaConfigurationFile()));
        return ActorSystem.create("leaderboard", config);
    }
}
