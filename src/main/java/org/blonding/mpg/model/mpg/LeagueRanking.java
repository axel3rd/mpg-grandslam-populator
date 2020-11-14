package org.blonding.mpg.model.mpg;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LeagueRanking {

    private Map<String, Team> teams;

    @JsonProperty("ranking")
    private List<Rank> ranks;

    public Map<String, Team> getTeams() {
        return teams;
    }

    public List<Rank> getRanks() {
        return ranks;
    }
}
