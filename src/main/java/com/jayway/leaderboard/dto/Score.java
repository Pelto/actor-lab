package com.jayway.leaderboard.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Score score1 = (Score) o;

        if (score != score1.score) return false;
        if (!level.equals(score1.level)) return false;
        if (!user.equals(score1.user)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, score, level);
    }
}
