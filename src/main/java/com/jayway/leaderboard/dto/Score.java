package com.jayway.leaderboard.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Score {

    public final String user;

    public final int score;

    public final Level level;

    @JsonCreator
    public Score(@JsonProperty("level") Level level,
                 @JsonProperty("user") String user,
                 @JsonProperty("score") int score) {
        this.user = user;
        this.score = score;
        this.level = level;
    }

    public String user() { return user; }

    public int score() { return score; }

    public Level level() { return level; }
}
