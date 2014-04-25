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
}
