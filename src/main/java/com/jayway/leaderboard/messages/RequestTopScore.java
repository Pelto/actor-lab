package com.jayway.leaderboard.messages;

import com.jayway.leaderboard.dto.Level;

public class RequestTopScore {

    private final Level level;

    public RequestTopScore(Level level) {
        this.level = level;
    }

    public Level level() { return level; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestTopScore that = (RequestTopScore) o;

        if (!level.equals(that.level)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return level.hashCode();
    }
}
