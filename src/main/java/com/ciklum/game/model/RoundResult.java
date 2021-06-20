package com.ciklum.game.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RoundResult {
    DRAW(0),
    FIRST_PLAYER_WINS(1),
    SECOND_PLAYER_WINS(2);

    private final int value;

    @JsonValue
    public int getValue() {
        return value;
    }

    @JsonCreator
    public static RoundResult of(Integer value) {
        if (null == value) {
            return null;
        }
        for (RoundResult item : RoundResult.values()) {
            if (value.equals(item.getValue())) {
                return item;
            }
        }
        throw new IllegalArgumentException("RoundResult: unknown value: " + value);
    }

    RoundResult(int value) {
        this.value = value;
    }
}

