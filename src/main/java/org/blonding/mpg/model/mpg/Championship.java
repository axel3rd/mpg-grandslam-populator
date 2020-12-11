
package org.blonding.mpg.model.mpg;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Championship {

    LIGUE_1(1, "L1"), PREMIER_LEAGUE(2, "PREMIER"), LIGA(3, "LIGA"), LIGUE_2(4, "L2"), SERIE_A(5, "SERIEA"), CHAMPIONS_LEAGUE(6, "C1");

    private final int value;
    private final String name;

    private Championship(final int value, final String name) {
        this.value = value;
        this.name = name;
    }

    @JsonCreator
    public static Championship getNameByValue(final int value) {
        for (final Championship s : Championship.values()) {
            if (s.value == value) {
                return s;
            }
        }
        throw new UnsupportedOperationException(String.format("Championship type not supported: %s", value));
    }

    public int value() {
        return this.value;
    }

    public String ame() {
        return name;
    }
}
