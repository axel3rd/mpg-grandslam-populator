package org.blonding.mpg.model.mpg;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Rank {

    @JsonProperty("teamid")
    private String teamId;
    private int victory;
    private int draw;
    private int defeat;
    private int difference;

    public String getTeamId() {
        return teamId;
    }

    public int getVictory() {
        return victory;
    }

    public int getDraw() {
        return draw;
    }

    public int getDefeat() {
        return defeat;
    }

    public int getDifference() {
        return difference;
    }
}
