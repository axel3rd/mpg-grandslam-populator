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
}
