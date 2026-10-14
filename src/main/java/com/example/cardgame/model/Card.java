package com.example.cardgame.model;

import java.util.Objects;

/**
 * Modélise une carte à jouer composée d'une couleur (Suit) et d'une valeur (Rank).
 * Implémente Comparable pour permettre un tri naturel cohérent :
 * 1. D'abord par couleur (Suit)
 * 2. Ensuite par valeur (Rank)
 */
public class Card implements Comparable<Card> {

    private final Suit suit;
    private final Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = Objects.requireNonNull(suit, "La couleur (suit) ne peut pas être nulle");
        this.rank = Objects.requireNonNull(rank, "La valeur (rank) ne peut pas être nulle");
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    /**
     * Nom lisible en français : ex. "As de Cœur", "10 de Carreau"
     */
    public String getDisplayName() {
        return rank.getDisplayName() + " de " + suit.getDisplayName();
    }

    /**
     * Notation courte : ex. "A♥", "10♦", "K♠"
     */
    public String getShortDisplayName() {
        return rank.getShortLabel() + suit.getSymbol();
    }

    /**
     * Couleur visuelle pour le CSS : "red" ou "black"
     */
    public String getColor() {
        return suit.getColor();
    }

    /**
     * Symbole de la couleur : ♦, ♥, ♠, ♣
     */
    public String getSymbol() {
        return suit.getSymbol();
    }

    /**
     * Label court de la valeur : "A", "2", ..., "10", "J", "Q", "K"
     */
    public String getRankLabel() {
        return rank.getShortLabel();
    }

    /**
     * Nom du fichier image associé : ex. "carreau_as.svg"
     */
    public String getImageFileName() {
        return suit.getCode() + "_" + rank.getCode() + ".svg";
    }

    /**
     * Chemin de l'image servi par Spring Boot : ex. "/img/cards/carreau_as.svg"
     */
    public String getImagePath() {
        return "/img/cards/" + getImageFileName();
    }

    @Override
    public int compareTo(Card other) {
        if (other == null) {
            return 1;
        }
        int suitComparison = this.suit.compareTo(other.suit);
        if (suitComparison != 0) {
            return suitComparison;
        }
        return this.rank.compareTo(other.rank);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card card)) return false;
        return suit == card.suit && rank == card.rank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, rank);
    }

    @Override
    public String toString() {
        return getDisplayName() + " (" + getShortDisplayName() + ")";
    }
}
