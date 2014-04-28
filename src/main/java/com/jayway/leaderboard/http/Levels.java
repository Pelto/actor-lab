package com.jayway.leaderboard.http;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Inbox;
import com.jayway.leaderboard.dto.AccessToken;
import com.jayway.leaderboard.dto.Level;
import com.jayway.leaderboard.messages.TopScoresResponse;
import com.jayway.leaderboard.messages.ReportScoreMessage;
import com.jayway.leaderboard.messages.RequestTopScoreMessage;
import scala.concurrent.duration.Duration;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.TimeUnit;

@Path("/level/{levelid}")
public class Levels {

    private final ActorRef gameActor;

    private final ActorSystem system;

    private final long requestTimeout;

    public Levels(ActorRef gameActor, ActorSystem system, long requestTimeout) {
        this.gameActor = gameActor;
        this.system = system;
        this.requestTimeout = requestTimeout;
    }

    @POST
    @Path("/score")
    public Response postScore(@QueryParam("accessToken") String accessToken,
                              @PathParam("levelid") String levelId,
                              String body) {

        Level level = new Level(levelId);
        AccessToken token = AccessToken.fromString(accessToken);
        int score = Integer.parseInt(body);

        gameActor.tell(new ReportScoreMessage(level, score, token), ActorRef.noSender());

        return Response.ok().build();
    }

    @GET
    @Path("/highscorelist")
    @Produces(MediaType.APPLICATION_JSON)
    public Response highscoreList(@PathParam("levelid") String levelId) {
        RequestTopScoreMessage message = new RequestTopScoreMessage(new Level(levelId));

        Inbox inbox = Inbox.create(system);
        inbox.send(gameActor, message);

        TopScoresResponse scores = (TopScoresResponse)inbox.receive(Duration.create(requestTimeout, TimeUnit.MILLISECONDS));

        return Response.ok(scores).build();
    }
}
