package com.example.playwrightdemo.ui.enums;

public enum SortOption {
    NAME_ASC("az"),
    NAME_DESC("za"),
    PRICE_ASC("lohi"),
    PRICE_DESC("hilo");

    private final String value;

    SortOption(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
