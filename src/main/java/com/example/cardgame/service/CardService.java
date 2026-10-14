package com.example.cardgame.service;

import com.example.cardgame.exception.CardGameException;
import com.example.cardgame.model.Card;
import com.example.cardgame.model.CardGameRound;
import com.example.cardgame.model.Pack;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Service métier responsable de la logique du jeu de cartes :
 * 1. Création du paquet de 52 cartes
 * 2. Mélange aléatoire
 * 3. Tirage de la main de 10 cartes
 * 4. Tri de la main (par couleur puis par valeur)
 */
@Service
public class CardService {

    public static final int DEFAULT_HAND_SIZE = 10;

    /**
     * Crée un nouveau paquet standard de 52 cartes.
     */
    public Pack createPack() {
        return new Pack();
    }

    /**
     * Mélange une copie des cartes d'un paquet de manière aléatoire.
     * Le paquet d'origine n'est pas modifié.
     */
    public List<Card> shuffle(List<Card> cards) {
        if (cards == null || cards.isEmpty()) {
            throw new CardGameException("Impossible de mélanger une liste de cartes vide ou nulle");
        }
        List<Card> shuffledList = new ArrayList<>(cards);
        Collections.shuffle(shuffledList);
        return shuffledList;
    }

    /**
     * Tire une main de n cartes (par défaut 10) depuis un paquet mélangé.
     * Les cartes sont tirées sans remise (sans doublon).
     */
    public List<Card> drawHand(List<Card> shuffledDeck, int count) {
        if (shuffledDeck == null) {
            throw new CardGameException("Le paquet de cartes fourni est nul");
        }
        if (count <= 0) {
            throw new CardGameException("Le nombre de cartes à tirer doit être supérieur à 0");
        }
        if (count > shuffledDeck.size()) {
            throw new CardGameException("Le paquet ne contient pas assez de cartes pour tirer " 
                    + count + " cartes (taille actuelle : " + shuffledDeck.size() + ")");
        }

        // On prend les 10 premières cartes après mélange
        List<Card> hand = new ArrayList<>(shuffledDeck.subList(0, count));

        // Vérification de sécurité : aucune carte en double dans la main
        Set<Card> uniqueCards = new HashSet<>(hand);
        if (uniqueCards.size() != count) {
            throw new CardGameException("La main tirée contient des doublons inattendus");
        }

        return hand;
    }

    /**
     * Trie une copie de la main de cartes.
     * Règle de tri :
     * 1. Par couleur : Carreau, Cœur, Pique, Trèfle (selon l'ordre de déclaration de l'enum Suit)
     * 2. Par valeur : As, 2, 3, ..., 10, Valet, Dame, Roi (selon l'ordre de déclaration de l'enum Rank)
     *
     * La liste initiale passée en paramètre n'est JAMAIS modifiée.
     */
    public List<Card> sortHand(List<Card> hand) {
        if (hand == null) {
            throw new CardGameException("La main à trier ne peut pas être nulle");
        }

        // On fait une copie indépendante de la main pour ne pas altérer la main non triée
        List<Card> sorted = new ArrayList<>(hand);

        // Comparator explicite : d'abord par couleur (Suit), puis par valeur (Rank)
        Comparator<Card> cardComparator = Comparator
                .comparing(Card::getSuit)
                .thenComparing(Card::getRank);

        sorted.sort(cardComparator);

        // Vérification d'intégrité : même taille et mêmes cartes
        if (sorted.size() != hand.size() || !new HashSet<>(sorted).equals(new HashSet<>(hand))) {
            throw new CardGameException("Erreur lors du tri : des cartes ont été perdues ou altérées");
        }

        return sorted;
    }

    /**
     * Exécute une manche complète selon les exigences du test technique :
     * 1. Créer le paquet de 52 cartes
     * 2. Mélanger le paquet
     * 3. Tirer 10 cartes
     * 4. Trier une copie de ces 10 cartes
     * 5. Retourner l'ensemble des données
     */
    public CardGameRound playRound() {
        Pack pack = createPack();
        List<Card> shuffled = shuffle(pack.getCards());
        List<Card> randomHand = drawHand(shuffled, DEFAULT_HAND_SIZE);
        List<Card> sortedHand = sortHand(randomHand);

        return new CardGameRound(pack.getCards(), randomHand, sortedHand);
    }
}
