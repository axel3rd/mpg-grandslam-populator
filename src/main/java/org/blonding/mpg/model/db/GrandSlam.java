package org.blonding.mpg.model.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class GrandSlam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String year;
    private String status;

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
}
