package org.blonding.mpg.model.db;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

@Entity
public class GrandSlam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String year;
    private String status;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "grandSlamId", updatable = false)
    private List<League> leagues;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "grandSlamId", updatable = false)
    private List<GrandSlamDay> grandSlamDays;

    public GrandSlam() {
        super();
    }

    /**
     * Return a {@link GrandSlam} with "Running" status usage as {@link Example} in {@link JpaRepository#findOne(Example)}
     * 
     * @return Current {@link GrandSlam}
     */
    public static GrandSlam fromCurrentRunning() {
        GrandSlam gs = new GrandSlam();
        gs.status = "Running";
        return gs;
    }

    public GrandSlam(String year, String status) {
        super();
        this.year = year;
        this.status = status;
    }

    public Integer getId() {
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

    public List<GrandSlamDay> getGrandSlamDays() {
        return grandSlamDays;
    }

}
