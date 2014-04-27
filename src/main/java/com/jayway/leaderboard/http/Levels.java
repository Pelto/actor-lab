package com.jayway.leaderboard.http;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Inbox;
import com.jayway.leaderboard.dto.AccessToken;
import com.jayway.leaderboard.dto.Level;
import com.jayway.leaderboard.dto.Score;
import com.jayway.leaderboard.dto.TopScores;
import com.jayway.leaderboard.messages.ReportScore;
import com.jayway.leaderboard.messages.RequestTopScore;
import scala.concurrent.duration.Duration;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.concurrent.TimeUnit;

@Path("/level/levelid")
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
    public Response postScore(@QueryParam("sessionKey") String sessionKey,
                              @PathParam("levelid") String levelId,
                              String body) {

        Score scoreToReport = new Score(
                AccessToken.fromString(sessionKey),
                Integer.parseInt(body));

        Level level = new Level(levelId);

        gameActor.tell(new ReportScore(level, scoreToReport), ActorRef.noSender());

        return Response.ok().build();
    }

    @GET
    @Path("/highscorelist")
    public Response highscoreList(@QueryParam("levelid") String levelId) {
        RequestTopScore message = new RequestTopScore(new Level(levelId));

        Inbox inbox = Inbox.create(system);
        inbox.send(gameActor, message);

        TopScores scores = (TopScores)inbox.receive(Duration.create(requestTimeout, TimeUnit.MILLISECONDS));

        return Response.ok(scores).build();
    }
}
