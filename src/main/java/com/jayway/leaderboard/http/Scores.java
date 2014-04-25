package com.jayway.leaderboard.http;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Inbox;
import com.jayway.leaderboard.dto.Level;
import com.jayway.leaderboard.dto.Score;
import com.jayway.leaderboard.messages.RequestTopScore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.concurrent.duration.FiniteDuration;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.TimeUnit;

@Path("scores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Scores {

    private final Logger log = LoggerFactory.getLogger(Scores.class);

    private final ActorRef scoreActor;

    private final ActorSystem system;

    private final int requestTimeout;

    public Scores(ActorSystem system, ActorRef scoreReporter, int requestTimeout) {
        this.scoreActor = scoreReporter;
        this.system = system;
        this.requestTimeout = requestTimeout;
    }

    @PUT
    public Response reportScore(Score scoreToReport) {
        log.info("Reporting score");

        scoreActor.tell(scoreToReport, ActorRef.noSender());

        return Response.ok().build();
    }

    @GET
    @Path("top/{level}")
    public Response topScore(@QueryParam("level") String level) {
        Inbox responseInbox = Inbox.create(system);
        RequestTopScore message = new RequestTopScore(new Level(level));

        scoreActor.tell(message, responseInbox.getRef());

        Score topScore =  (Score)responseInbox.receive(new FiniteDuration(requestTimeout, TimeUnit.MILLISECONDS));

        return Response.ok(topScore).build();
    }
}
