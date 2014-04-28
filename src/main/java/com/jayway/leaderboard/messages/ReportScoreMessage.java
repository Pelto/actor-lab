package com.jayway.leaderboard.messages;

import com.jayway.leaderboard.dto.AccessToken;
import com.jayway.leaderboard.dto.Level;

import java.io.Serializable;
import java.util.Objects;

/**
 * This message is sent to the Game actor when we are reporting a score. This
 * message does not have a associated response.
 */
public class ReportScoreMessage implements Serializable {

    private final int score;

    private final Level level;

    private final AccessToken token;

    public ReportScoreMessage(Level level, int score, AccessToken token) {
        this.score = score;
        this.level = level;
        this.token = token;
    }

    public int score() {
        return score;
    }

    public Level level() { return level; }

    public AccessToken accessToken() { return token; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReportScoreMessage that = (ReportScoreMessage) o;

        if (score != that.score) return false;
        if (!level.equals(that.level)) return false;
        if (!token.equals(that.token)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(score, level, token);
    }
}
