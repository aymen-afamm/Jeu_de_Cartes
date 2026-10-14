package com.example.cardgame.exception;

/**
 * Exception métier spécifique pour les erreurs liées au jeu de cartes.
 */
public class CardGameException extends RuntimeException {

    public CardGameException(String message) {
        super(message);
    }

    public CardGameException(String message, Throwable cause) {
        super(message, cause);
    }
}
