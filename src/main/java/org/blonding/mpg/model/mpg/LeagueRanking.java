package org.blonding.mpg.model.mpg;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LeagueRanking implements Serializable {

    private static final long serialVersionUID = 6713320764976504293L;

    private Map<String, Team> teams;

    @JsonProperty("standings")
    private List<Rank> ranks;

    private int statusCode;

    @JsonProperty("teamsUsers")
    public Map<String, Team> getTeams() {
        return teams;
    }

    public List<Rank> getRanks() {
        return ranks;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
