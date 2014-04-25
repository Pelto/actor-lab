# Leaderboard

This is a small labb on how to use actors.

## Requirements
The following is needed:
o
* Java 7
* Gradle

If you do not have gradle installed, simply use the gradle wrapper
(`./gradlew`). If you want to use Java 8 you can change gradle file to compile
with Java 8 instead.

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


### The API
