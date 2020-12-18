package org.blonding.mpg.model.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "player_id")
    private Long playerId;
    @Column(name = "league_id")
    private Long leagueId;
    private String name;
    private String shortName;
    private int victory;
    private int draw;
    private int defeat;
    private int goalDiff;

    public Long getId() {
        return id;
    }

    public Long getPlayerId() {
        return playerId;
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
