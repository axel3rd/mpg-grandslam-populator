package org.blonding.mpg.model.mpg;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Team implements Serializable {

    private static final long serialVersionUID = -837193093869797806L;

    private String userId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("abbr")
    private String shortName;

    @JsonProperty("firstname")
    private String firstName;

    @JsonProperty("lastname")
    private String lastName;

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return this.name;
    }

    public String getShortName() {
        return shortName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
