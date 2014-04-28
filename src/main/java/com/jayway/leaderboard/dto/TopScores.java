package com.jayway.leaderboard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class TopScores {

    private List<Score> scores;

    public TopScores(List<Score> scores) {
        this.scores = new ArrayList<Score>();
        this.scores.addAll(scores);
    }

    @JsonProperty
    public List<Score> scores() { return scores; }
}
