package com.example.cardgame.model;

import java.util.Collections;
import java.util.List;

/**
 * Contient les données d'un tirage complet du jeu :
 * - Le paquet complet (52 cartes)
 * - La main aléatoire (10 cartes non triées)
 * - La main triée (10 cartes triées par couleur puis valeur)
 */
public class CardGameRound {

    private final List<Card> fullDeck;
    private final List<Card> randomHand;
    private final List<Card> sortedHand;

    public CardGameRound(List<Card> fullDeck, List<Card> randomHand, List<Card> sortedHand) {
        this.fullDeck = Collections.unmodifiableList(fullDeck);
        this.randomHand = Collections.unmodifiableList(randomHand);
        this.sortedHand = Collections.unmodifiableList(sortedHand);
    }

    public List<Card> getFullDeck() {
        return fullDeck;
    }

    public List<Card> getRandomHand() {
        return randomHand;
    }

    public List<Card> getSortedHand() {
        return sortedHand;
    }
}
