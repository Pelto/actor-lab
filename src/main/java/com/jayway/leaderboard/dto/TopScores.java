package com.jayway.leaderboard.dto;


import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class TopScores {

    private SortedSet<Score> scores;

    public TopScores(List<Score> scores) {
        this.scores = new TreeSet<Score>();
        this.scores.addAll(scores);
    }

    public SortedSet<Score> scores() { return scores; }
}
