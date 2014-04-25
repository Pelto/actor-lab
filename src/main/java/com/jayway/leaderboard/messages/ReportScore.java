package com.jayway.leaderboard.messages;

import com.jayway.leaderboard.dto.Score;

import java.io.Serializable;

public class ReportScore implements Serializable {

    private final Score score;

    public ReportScore(Score score) {
        this.score = score;
    }

    public Score score() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReportScore that = (ReportScore) o;

        if (!score.equals(that.score)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return score.hashCode();
    }
}
