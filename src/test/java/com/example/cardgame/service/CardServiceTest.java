package com.example.cardgame.service;

import com.example.cardgame.exception.CardGameException;
import com.example.cardgame.model.Card;
import com.example.cardgame.model.CardGameRound;
import com.example.cardgame.model.Pack;
import com.example.cardgame.model.Rank;
import com.example.cardgame.model.Suit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests Unitaires - Logique Métier CardService")
class CardServiceTest {

    private CardService cardService;

    @BeforeEach
    void setUp() {
        cardService = new CardService();
    }

    @Test
    @DisplayName("1. Le paquet complet doit contenir exactement 52 cartes")
    void testPackContainsExactly52Cards() {
        Pack pack = cardService.createPack();

        assertNotNull(pack, "Le paquet ne doit pas être nul");
        assertEquals(52, pack.size(), "Le paquet doit contenir exactement 52 cartes");
        assertEquals(52, pack.getCards().size(), "La liste de cartes doit contenir 52 éléments");
    }

    @Test
    @DisplayName("2. Toutes les 52 cartes du paquet doivent être uniques (sans doublon)")
    void testAll52CardsAreUnique() {
        Pack pack = cardService.createPack();
        Set<Card> uniqueCards = new HashSet<>(pack.getCards());

        assertEquals(52, uniqueCards.size(), "Il doit y avoir exactement 52 cartes uniques sans aucun doublon");
    }

    @Test
    @DisplayName("3. Le paquet doit contenir exactement 13 cartes de chaque couleur")
    void testEachSuitHas13Cards() {
        Pack pack = cardService.createPack();

        for (Suit suit : Suit.values()) {
            long count = pack.getCards().stream()
                    .filter(c -> c.getSuit() == suit)
                    .count();
            assertEquals(13, count, "La couleur " + suit.getDisplayName() + " doit contenir 13 cartes");
        }
    }

    @Test
    @DisplayName("4. Le mélange conserve l'intégralité des 52 cartes d'origine")
    void testShufflePreservesAllCards() {
        Pack pack = cardService.createPack();
        List<Card> original = pack.getCards();
        List<Card> shuffled = cardService.shuffle(original);

        assertNotNull(shuffled);
        assertEquals(52, shuffled.size(), "Le paquet mélangé doit toujours contenir 52 cartes");
        assertEquals(new HashSet<>(original), new HashSet<>(shuffled), "Toutes les cartes d'origine doivent être présentes après le mélange");
    }

    @Test
    @DisplayName("5. Le tirage d'une main doit retourner exactement 10 cartes")
    void testDrawHandReturnsExactly10Cards() {
        Pack pack = cardService.createPack();
        List<Card> shuffled = cardService.shuffle(pack.getCards());
        List<Card> hand = cardService.drawHand(shuffled, 10);

        assertNotNull(hand);
        assertEquals(10, hand.size(), "La main doit contenir exactement 10 cartes");
    }

    @Test
    @DisplayName("6. Les 10 cartes tirées dans la main sont toutes uniques")
    void testHandCardsAreUnique() {
        Pack pack = cardService.createPack();
        List<Card> shuffled = cardService.shuffle(pack.getCards());
        List<Card> hand = cardService.drawHand(shuffled, 10);

        Set<Card> uniqueHand = new HashSet<>(hand);
        assertEquals(10, uniqueHand.size(), "Les 10 cartes de la main doivent être distinctes (sans doublon)");
    }

    @Test
    @DisplayName("7. Le tri classe les cartes d'abord par couleur puis par valeur")
    void testSortHandDeterministicOrder() {
        // Préparation d'une main non triée prédéterminée
        List<Card> unsorted = List.of(
                new Card(Suit.TREFLE, Rank.KING),
                new Card(Suit.COEUR, Rank.TEN),
                new Card(Suit.CARREAU, Rank.FIVE),
                new Card(Suit.PIQUE, Rank.ACE),
                new Card(Suit.CARREAU, Rank.ACE),
                new Card(Suit.COEUR, Rank.TWO),
                new Card(Suit.PIQUE, Rank.QUEEN),
                new Card(Suit.TREFLE, Rank.TWO),
                new Card(Suit.CARREAU, Rank.JACK),
                new Card(Suit.COEUR, Rank.ACE)
        );

        List<Card> sorted = cardService.sortHand(unsorted);

        // Ordre attendu :
        // 1. CARREAU : As, 5, Valet
        // 2. COEUR : As, 2, 10
        // 3. PIQUE : As, Dame
        // 4. TREFLE : 2, Roi
        List<Card> expected = List.of(
                new Card(Suit.CARREAU, Rank.ACE),
                new Card(Suit.CARREAU, Rank.FIVE),
                new Card(Suit.CARREAU, Rank.JACK),
                new Card(Suit.COEUR, Rank.ACE),
                new Card(Suit.COEUR, Rank.TWO),
                new Card(Suit.COEUR, Rank.TEN),
                new Card(Suit.PIQUE, Rank.ACE),
                new Card(Suit.PIQUE, Rank.QUEEN),
                new Card(Suit.TREFLE, Rank.TWO),
                new Card(Suit.TREFLE, Rank.KING)
        );

        assertEquals(expected, sorted, "Les cartes doivent être triées d'abord par couleur (Carreau, Coeur, Pique, Trefle) puis par valeur (As -> Roi)");
    }

    @Test
    @DisplayName("8. Le tri ne modifie pas la liste initiale et conserve exactement les mêmes cartes")
    void testSortDoesNotMutateOriginalAndPreservesCards() {
        Pack pack = cardService.createPack();
        List<Card> shuffled = cardService.shuffle(pack.getCards());
        List<Card> originalHand = cardService.drawHand(shuffled, 10);
        List<Card> originalHandCopy = new ArrayList<>(originalHand);

        List<Card> sortedHand = cardService.sortHand(originalHand);

        // La liste originale n'est pas modifiée
        assertEquals(originalHandCopy, originalHand, "La main d'origine ne doit pas être modifiée lors du tri");
        // Les cartes sont identiques (même ensemble)
        assertEquals(new HashSet<>(originalHand), new HashSet<>(sortedHand), "La main triée doit contenir exactement les mêmes cartes que la main initiale");
        assertEquals(10, sortedHand.size(), "La main triée doit toujours comporter 10 cartes");
    }

    @Test
    @DisplayName("9. Tirages répétés : chaque manche complète produit des données valides")
    void testMultipleRoundsValidity() {
        for (int i = 0; i < 5; i++) {
            CardGameRound round = cardService.playRound();

            assertEquals(52, round.getFullDeck().size());
            assertEquals(10, round.getRandomHand().size());
            assertEquals(10, round.getSortedHand().size());

            // Mêmes cartes entre tirage aléatoire et trié
            assertEquals(new HashSet<>(round.getRandomHand()), new HashSet<>(round.getSortedHand()));

            // Vérification que le tri est bien ordonné
            for (int j = 0; j < round.getSortedHand().size() - 1; j++) {
                Card c1 = round.getSortedHand().get(j);
                Card c2 = round.getSortedHand().get(j + 1);
                assertTrue(c1.compareTo(c2) <= 0, "La carte " + c1 + " doit être <= à la carte suivante " + c2);
            }
        }
    }

    @Test
    @DisplayName("10. Gestion des erreurs : tirage d'un nombre invalide de cartes")
    void testInvalidDrawCountThrowsException() {
        Pack pack = cardService.createPack();
        List<Card> cards = pack.getCards();

        assertThrows(CardGameException.class, () -> cardService.drawHand(cards, 0), "Tirer 0 carte doit lever une exception");
        assertThrows(CardGameException.class, () -> cardService.drawHand(cards, -5), "Tirer un nombre négatif doit lever une exception");
        assertThrows(CardGameException.class, () -> cardService.drawHand(cards, 100), "Tirer plus de cartes que le paquet doit lever une exception");
        assertThrows(CardGameException.class, () -> cardService.drawHand(null, 10), "Un paquet nul doit lever une exception");
    }
}
