package com.jayway.leaderboard.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Level {

    private final String name;

    @JsonCreator
    public Level(@JsonProperty("name") String name) {
        this.name = name;
    }

    public String name() { return name; }
}
