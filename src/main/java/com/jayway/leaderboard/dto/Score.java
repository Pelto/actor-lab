package com.jayway.leaderboard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Score implements Comparable<Score> {

    private final String user;

    private final int score;

    public Score(String user, int score) {
        this.user = user;
        this.score = score;
    }

    @JsonProperty
    public String user() { return user; }

    @JsonProperty
    public int score() { return score; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Score score1 = (Score) o;

        if (score != score1.score) return false;
        if (!user.equals(score1.user)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, score);
    }

    @Override
    public int compareTo(Score o) {
        return Integer.compare(score, o.score);
    }
}
