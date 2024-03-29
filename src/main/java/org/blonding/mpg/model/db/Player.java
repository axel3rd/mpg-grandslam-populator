package org.blonding.mpg.model.db;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long mpgId;

    private String name;

    private boolean active;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "playerId", updatable = false)
    private List<Team> teams;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "playerId", updatable = false)
    private List<GrandSlamBonus> grandSlamBonus;

    public Player() {
        super();
    }

    public Player(Long mpgId, String name) {
        super();
        this.mpgId = mpgId;
        this.name = name;
        this.active = true;
    }

    public Integer getId() {
        return id;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
