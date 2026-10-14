package com.example.cardgame.controller;

import com.example.cardgame.model.CardGameRound;
import com.example.cardgame.service.CardService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Contrôleur Spring MVC pour le jeu de cartes.
 * Reçoit les requêtes HTTP, sollicite le CardService et alimente la vue Thymeleaf.
 */
@Controller
public class CardController {

    private final CardService cardService;

    // Injection de dépendances via le constructeur (recommandé par Spring)
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    /**
     * Redirige la racine vers l'URL exigée par le sujet : /random-hand-cards
     */
    @GetMapping("/")
    public String index() {
        return "redirect:/random-hand-cards";
    }

    /**
     * Endpoint principal demandé dans le test technique :
     * GET /random-hand-cards
     * 
     * Génère :
     * - Le paquet complet de 52 cartes
     * - Une main aléatoire de 10 cartes non triées
     * - La même main triée par couleur puis par valeur
     */
    @GetMapping("/random-hand-cards")
    public String getRandomHandCards(Model model) {
        CardGameRound round = cardService.playRound();

        model.addAttribute("fullDeck", round.getFullDeck());
        model.addAttribute("randomHand", round.getRandomHand());
        model.addAttribute("sortedHand", round.getSortedHand());
        model.addAttribute("handSize", round.getRandomHand().size());
        model.addAttribute("totalCards", round.getFullDeck().size());

        return "random-hand-cards";
    }

    /**
     * Endpoint REST optionnel retournant les données en format JSON structuré.
     * Utile pour tester l'API par script ou démontrer la polyvalence du contrôleur.
     */
    @GetMapping(value = "/api/random-hand-cards", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<CardGameRound> getRandomHandCardsJson() {
        CardGameRound round = cardService.playRound();
        return ResponseEntity.ok(round);
    }
}
