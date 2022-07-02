package com.darksoldier1404.dpr.enums;

import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public enum IllustrateType {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9)
    ;

    private final int value;

    IllustrateType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Nullable
    public static IllustrateType valueOf(int value) {
        for (IllustrateType type : values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        return null;
    }

}
