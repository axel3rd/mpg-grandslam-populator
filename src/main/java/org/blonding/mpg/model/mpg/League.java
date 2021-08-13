package org.blonding.mpg.model.mpg;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class League implements Serializable {

    private static final long serialVersionUID = 4749106603974234336L;

    @JsonProperty("leagueId")
    private String leagueId;
    private String divisionId;
    private String name;
    @JsonProperty("championshipId")
    private Championship championshipId;
    @JsonProperty("status")
    private LeagueStatus status;

    public String getId() {
        return leagueId.substring("mpg_league_".length());
    }

    public String getDivisionId() {
        return divisionId;
    }

    public String getName() {
        return name;
    }

    public LeagueStatus getLeagueStatus() {
        return status;
    }

    public Championship getChampionship() {
        return championshipId;
    }

}
