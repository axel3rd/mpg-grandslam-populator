package org.blonding.mpg.model.mpg;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Team implements Serializable {

    private static final long serialVersionUID = -837193093869797806L;

    // Warn, could be teamId or userId depending URL retrieved
    private String id;

    private String userId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("abbreviation")
    private String shortName;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("username")
    private String username;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return "(" + username + ")";
    }
}
