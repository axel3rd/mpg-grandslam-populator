package org.blonding.mpg.model.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int playerId;
    private int leagueId;
    private String name;
    private String shortName;
    private int victory;
    private int draw;
    private int defeat;
    private int goalDiff;

    public Integer getId() {
        return id;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getLeagueId() {
        return leagueId;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
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

    public int getGoalDiff() {
        return goalDiff;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public void setLeagueId(int leagueId) {
        this.leagueId = leagueId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public void setVictory(int victory) {
        this.victory = victory;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public void setDefeat(int defeat) {
        this.defeat = defeat;
    }

    public void setGoalDiff(int goalDiff) {
        this.goalDiff = goalDiff;
    }
}
