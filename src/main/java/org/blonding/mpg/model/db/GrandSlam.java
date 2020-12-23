package org.blonding.mpg.model.db;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class GrandSlam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String year;
    private String status;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "grandSlamId", updatable = false)
    private List<League> leagues;

    public GrandSlam() {
        super();
    }

    public GrandSlam(String year, String status) {
        super();
        this.year = year;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getYear() {
        return year;
    }

    public String getStatus() {
        return status;
    }

    public List<League> getLeagues() {
        return leagues;
    }

}
