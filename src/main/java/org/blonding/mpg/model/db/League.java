package org.blonding.mpg.model.db;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class League {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mpgId;
    private String type;
    private String name;
    private String year;
    private String status;
    private Long grandSlamId;
    private Long gamePlayed;

    @OneToMany
    @JoinColumn(name = "league_id")
    private List<Team> teams;

    public League() {
        super();
    }

    public League(String mpgId, String type, String name, String year, String status, Long grandSlamId, Long gamePlayed) {
        super();
        this.mpgId = mpgId;
        this.type = type;
        this.name = name;
        this.year = year;
        this.status = status;
        this.grandSlamId = grandSlamId;
        this.gamePlayed = gamePlayed;
    }

    public String getMpgId() {
        return mpgId;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getYear() {
        return year;
    }

    public String getStatus() {
        return status;
    }

    public Long getGrandSlamId() {
        return grandSlamId;
    }

    public Long getGamePlayed() {
        return gamePlayed;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setGamePlayed(Long gamePlayed) {
        this.gamePlayed = gamePlayed;
    }
}
