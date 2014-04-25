package com.jayway.leaderboard.messages;

import com.jayway.leaderboard.dto.Level;

public class RequestTopScore {

    private final Level level;

    public RequestTopScore(Level level) {
        this.level = level;
    }

    public Level level() { return level; }
}
