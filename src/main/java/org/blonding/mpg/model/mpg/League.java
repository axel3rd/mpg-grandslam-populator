package org.blonding.mpg.model.mpg;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class League {

    private String id;
    private String name;
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

}
