package org.blonding.mpg.model.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mpgId;

    private String name;

    public Long getId() {
        return id;
    }

    public String getMpgId() {
        return mpgId;
    }

    public String getName() {
        return name;
    }

}
