package org.blonding.mpg.model.mpg;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class League implements Serializable {

    private static final long serialVersionUID = 4749106603974234336L;

    private String id;
    private String name;
    private Championship championship;
    private LeagueStatus leagueStatus;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LeagueStatus getLeagueStatus() {
        return leagueStatus;
    }

    public Championship getChampionship() {
        return championship;
    }

}
