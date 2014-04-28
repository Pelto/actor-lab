package com.jayway.leaderboard.messages;

import com.jayway.leaderboard.dto.Level;

public class RequestTopScoreMessage {

    private final Level level;

    public RequestTopScoreMessage(Level level) {
        this.level = level;
    }

    public Level level() {
        return level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestTopScoreMessage that = (RequestTopScoreMessage) o;

        if (!level.equals(that.level)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return level.hashCode();
    }
}
