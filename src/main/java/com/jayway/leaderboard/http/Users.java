package com.jayway.leaderboard.http;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Inbox;
import com.jayway.leaderboard.dto.AccessToken;
import com.jayway.leaderboard.messages.LoginUserMessage;
import scala.concurrent.duration.Duration;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.concurrent.TimeUnit;

@Path("user/{userid}")
public class Users {

    private final ActorSystem system;

    private final ActorRef userActor;

    private final long timeout;

    public Users(ActorSystem system, ActorRef userActor, Long timeout) {
        this.system = system;
        this.userActor = userActor;
        this.timeout = timeout;
    }

    @GET
    @Path("/login")
    public Response login(@PathParam("userid") String user) {
        LoginUserMessage message = new LoginUserMessage(user);
        Inbox inbox = Inbox.create(system);

        inbox.send(userActor, message);
        AccessToken token = (AccessToken)inbox.receive(Duration.create(timeout, TimeUnit.MILLISECONDS));

        return Response.ok(token.key().toString()).build();
    }
}
