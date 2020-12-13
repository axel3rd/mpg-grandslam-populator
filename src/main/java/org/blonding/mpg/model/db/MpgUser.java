package org.blonding.mpg.model.db;

import java.io.Serializable;

public class MpgUser implements Serializable {

    private static final long serialVersionUID = 5332602874805437205L;

    private long mpgId;
    private String name;

    public MpgUser(long mpgId, String name) {
        this.mpgId = mpgId;
        this.name = name;
    }

    public long getMpgId() {
        return mpgId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("%s/%s", getMpgId(), getName());
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MpgUser other = (MpgUser) o;
        return getMpgId() == other.getMpgId() && getName().equals(other.getName());
    }

}
