package com.ferl.model;

public enum Language {
    ENGLISH("English"),
    POLISH("Polski"),
    GERMAN("Deutsch"),
    SPANISH("Español");

    private final String displayName;

    Language(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
