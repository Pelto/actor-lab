# Leaderboard

This is a small lab on how to use actors.

## Requirements
The following is needed:
o
* Java 7
* Gradle

If you do not have Gradle installed, simply use the Gradle wrapper
(`./gradlew`).

If you want to use Java 8 instead all you have to do is to change Gradle file
to compile with Java 8.


## Lab instructions

In this lab you will finish the implementation of a small service where users
can report their scores that they had on a specific level in the game. A user
should also be able to retrive the top score for a specific level. A optional
requirement is that users should be notified when one of their friends reports
a beats the user's score.


### The application
The application is written in Java using Akka as a actor system and Dropwizard
for the HTTP service.

* [Link to Akka's documentation](http://doc.akka.io/docs/akka/2.3.2/java.html)

* [Link to Dropwizard's documentation](http://dropwizard.readthedocs.org/en/latest/getting-started.html)

To start the application type in the following command:

    ./gradlew run

To run all the test cases use the following gradle command:

    ./gradlew test

Newer versions of Intellij have execellent support for Gradle and you can import
the application as a project by just pointing out the `build.gradle` file.

The startup class for the application can be found in `LeaderboardApplication`
and is responsible for creating up our Actor system and our Jersey resources.
The application is structured as follows:

      └── com.jayway.leaderboard
          ├── actors
          ├── dto
          ├── health
          ├── http
          ├── infrastructure
          └── messages

 Package       | Content
-------------  | -------------
actors         | This package contains all our actors.
dto            | This package contains all our plain old java objects. Every dto used is immutable.
health         | This package contains our health checks, at the moment we only have one that checks that our `ActorSystem` is alive
http           | This package contains all our Jersey resources.
infrastructure | This package contains various infrastrcture, right now it only contains a managed wrapper that control the lifecycle of our `ActorSystem`
messages       | This package contains our messages that we send between our actors.


### Instructions

To complete the lab follow the these steps:

  1. Fix the failing unit tests, a good tip is to start implement the
     `UsersActor`

  2. If you have time, give users notifications when a user pass their score.
     You can add a resource at `user/<userid>/notifications` that displays
     notifications for that user.

If you get stuck there is a example solution in the branch `solution`

## The API

The game will need the following API to function:

### Login

When a user posts his or her's score they need to do so with a active
`accessToken`. To retrieve a `accessToken`

    Path: /user/{user}/login
    Method: GET
    returns: A access token for the user

    curl -i -H "Accept: text/plain" -H "Content-Type: text/plain" http://127.0.0.1/user/me/login

### Posting a score

Using their API key the user can then post a score to their levels. Only one
score per user should be saved.

    Path: /level/{level}/score
    Method: POST
    Returns: status 200

    curl --data "<score>" http://127.0.0.1:8080/level/<level-name>/score?accessToken=<token>


### Retreiving the highscore

    Path /level/{level}/highscorelist
    Method: GET
    returns: application/json

    curl -i -H "Accept: application/json" -H "Content-Type: application/json" http://127.0.0.1/level/<level>/highscorelist
