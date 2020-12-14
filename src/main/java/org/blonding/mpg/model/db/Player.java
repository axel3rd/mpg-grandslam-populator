package org.blonding.mpg.model.db;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long mpgId;

    private String name;

    @OneToMany
    @JoinTable(name = "team")
    private List<Team> teams;

    @OneToMany
    @JoinTable(name = "grand_slam_bonus")
    private List<GrandSlamBonus> grandSlamBonus;

    public Player() {
        super();
    }

    public Player(Long mpgId, String name) {
        super();
        this.mpgId = mpgId;
        this.name = name;
    }

    public Long getMpgId() {
        return mpgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
