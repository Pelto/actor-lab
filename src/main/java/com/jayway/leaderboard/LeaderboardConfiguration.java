package com.jayway.leaderboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;

public class LeaderboardConfiguration extends Configuration {

    @JsonProperty
    @Min(1)
    private int requestTimeout;

    @NotEmpty
    @JsonProperty
    private String akkaConfigurationFile;

    public int requestTimeout() {
        return requestTimeout;
    }

    public String akkaConfigurationFile() { return akkaConfigurationFile; }
}
