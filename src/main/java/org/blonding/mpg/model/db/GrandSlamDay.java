package org.blonding.mpg.model.db;

import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.blonding.mpg.model.bean.PlayerDayJson;
import org.blonding.mpg.model.db.converter.PlayerDayJsonConverter;

@Entity
public class GrandSlamDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int grandSlamId;
    private int day;
    private String label;
    @Convert(converter = PlayerDayJsonConverter.class)
    private List<PlayerDayJson> players;

    public GrandSlamDay() {
        super();
    }

    public GrandSlamDay(int grandSlamId, int day) {
        super();
        this.grandSlamId = grandSlamId;
        this.day = day;
    }

    public int getGrandSlamId() {
        return grandSlamId;
    }

    public int getDay() {
        return day;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setPlayers(List<PlayerDayJson> players) {
        this.players = players;
    }

}
