package com.example.cardgame.model;

/**
 * Représente les 13 valeurs d'une couleur de cartes.
 * L'ordre de déclaration définit l'ordre naturel de tri (As -> 2 -> ... -> 10 -> Valet -> Dame -> Roi).
 */
public enum Rank {
    ACE("As", 1, "A", "as"),
    TWO("2", 2, "2", "2"),
    THREE("3", 3, "3", "3"),
    FOUR("4", 4, "4", "4"),
    FIVE("5", 5, "5", "5"),
    SIX("6", 6, "6", "6"),
    SEVEN("7", 7, "7", "7"),
    EIGHT("8", 8, "8", "8"),
    NINE("9", 9, "9", "9"),
    TEN("10", 10, "10", "10"),
    JACK("Valet", 11, "J", "valet"),
    QUEEN("Dame", 12, "Q", "dame"),
    KING("Roi", 13, "K", "roi");

    private final String displayName;
    private final int value;
    private final String shortLabel;
    private final String code; // Pour les noms de fichiers images

    Rank(String displayName, int value, String shortLabel, String code) {
        this.displayName = displayName;
        this.value = value;
        this.shortLabel = shortLabel;
        this.code = code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getValue() {
        return value;
    }

    public String getShortLabel() {
        return shortLabel;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
