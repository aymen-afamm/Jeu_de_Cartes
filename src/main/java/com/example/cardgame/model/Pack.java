package com.example.cardgame.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Représente un paquet de cartes (Pack).
 * Contient exactement 52 cartes uniques générées à partir du produit cartésien
 * des 4 couleurs et des 13 valeurs.
 */
public class Pack {

    public static final int STANDARD_PACK_SIZE = 52;

    private final List<Card> cards;

    /**
     * Constructeur par défaut : génère automatiquement le paquet de 52 cartes.
     */
    public Pack() {
        this.cards = generate52Cards();
        validatePack(this.cards);
    }

    /**
     * Constructeur permettant d'instancier un paquet avec une liste donnée.
     */
    public Pack(List<Card> cards) {
        if (cards == null) {
            throw new IllegalArgumentException("La liste de cartes ne peut pas être nulle");
        }
        validatePack(cards);
        this.cards = new ArrayList<>(cards);
    }

    /**
     * Génère automatiquement les 52 cartes à partir du produit cartésien des 4 couleurs et 13 valeurs.
     */
    private static List<Card> generate52Cards() {
        List<Card> generated = new ArrayList<>(STANDARD_PACK_SIZE);
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                generated.add(new Card(suit, rank));
            }
        }
        return generated;
    }

    /**
     * Valide qu'un paquet contient exactement 52 cartes sans aucun doublon.
     */
    public static void validatePack(List<Card> cardsToValidate) {
        if (cardsToValidate.size() != STANDARD_PACK_SIZE) {
            throw new IllegalStateException("Le paquet doit contenir exactement " 
                    + STANDARD_PACK_SIZE + " cartes, mais en contient " + cardsToValidate.size());
        }
        Set<Card> uniqueCards = new HashSet<>(cardsToValidate);
        if (uniqueCards.size() != STANDARD_PACK_SIZE) {
            throw new IllegalStateException("Le paquet contient des cartes en double ! Nombre de cartes uniques : " 
                    + uniqueCards.size());
        }
    }

    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }

    public int size() {
        return cards.size();
    }
}
