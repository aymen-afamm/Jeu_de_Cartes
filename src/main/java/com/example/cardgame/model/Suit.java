package com.example.cardgame.model;

/**
 * Représente les 4 couleurs possibles d'un jeu de 52 cartes.
 * L'ordre de déclaration définit l'ordre naturel de tri selon le sujet du test :
 * 1. Carreau (♦)
 * 2. Cœur (♥)
 * 3. Pique (♠)
 * 4. Trèfle (♣)
 */
public enum Suit {
    CARREAU("Carreau", "♦", "red", "carreau"),
    COEUR("Cœur", "♥", "red", "coeur"),
    PIQUE("Pique", "♠", "black", "pique"),
    TREFLE("Trèfle", "♣", "black", "trefle");

    private final String displayName;
    private final String symbol;
    private final String color; // "red" ou "black" pour le style CSS
    private final String code;  // Identifiant pour les noms de fichiers images

    Suit(String displayName, String symbol, String color, String code) {
        this.displayName = displayName;
        this.symbol = symbol;
        this.color = color;
        this.code = code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getColor() {
        return color;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
