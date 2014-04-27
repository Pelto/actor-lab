package com.jayway.leaderboard.messages;

import com.jayway.leaderboard.dto.Level;
import com.jayway.leaderboard.dto.Score;

import java.io.Serializable;
import java.util.Objects;

public class ReportScore implements Serializable {

    private final Score score;

    private final Level level;

    public ReportScore(Level level, Score score) {
        this.score = score;
        this.level = level;
    }

    public Score score() {
        return score;
    }

    public Level level() { return level; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReportScore that = (ReportScore) o;

        if (!score.equals(that.score)) return false;
        if (!level.equals(that.level)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(score, level);
    }
}
