package org.blonding.mpg.model.mpg;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Rank implements Serializable {

    private static final long serialVersionUID = 7331725661359464274L;

    @JsonProperty("teamid")
    private String teamId;
    private int played;
    @JsonProperty("won")
    private int victory;
    @JsonProperty("dranw")
    private int draw;
    @JsonProperty("lost")
    private int defeat;
    private int difference;

    public String getTeamId() {
        return teamId;
    }

    public int getPlayed() {
        return played;
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
