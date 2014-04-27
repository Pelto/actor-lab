package com.jayway.leaderboard.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Objects;

public class Score implements Comparable<Score> {

    private final AccessToken token;

    private final int score;

    @JsonCreator
    public Score(AccessToken token, int score) {
        this.token = token;
        this.score = score;
    }

    public AccessToken accessToken() { return token; }

    public int score() { return score; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Score score1 = (Score) o;

        if (score != score1.score) return false;
        if (!token.equals(score1.token)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, score);
    }

    @Override
    public int compareTo(Score o) {
        return Integer.compare(score, o.score);
    }
}
