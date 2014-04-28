package com.jayway.leaderboard.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jayway.leaderboard.dto.Score;

import java.util.ArrayList;
import java.util.List;

/**
 * This holds the top scores for a certain level.
 */
public class TopScoresResponse {

    private List<Score> scores;

    public TopScoresResponse(List<Score> scores) {
        this.scores = new ArrayList<Score>();
        this.scores.addAll(scores);
    }

    @JsonProperty
    public List<Score> scores() {
        return scores;
    }
}
