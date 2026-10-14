package com.example.cardgame.controller;

import com.example.cardgame.exception.CardGameException;
import com.example.cardgame.model.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Gestionnaire centralisé des erreurs (requis par le sujet du test).
 * Renvoie une réponse structurée de type ApiError, soit en JSON pour les appels d'API,
 * soit injectée dans la page error.html pour les requêtes du navigateur.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CardGameException.class)
    public Object handleCardGameException(CardGameException ex, HttpServletRequest request, Model model) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiError apiError = new ApiError(
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );

        if (isJsonRequest(request)) {
            return new ResponseEntity<>(apiError, status);
        }

        model.addAttribute("apiError", apiError);
        return "error";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Object handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request, Model model) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiError apiError = new ApiError(
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );

        if (isJsonRequest(request)) {
            return new ResponseEntity<>(apiError, status);
        }

        model.addAttribute("apiError", apiError);
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public Object handleGeneralException(Exception ex, HttpServletRequest request, Model model) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiError apiError = new ApiError(
                status.value(),
                status.getReasonPhrase(),
                "Une erreur inattendue est survenue : " + ex.getMessage(),
                request.getRequestURI()
        );

        if (isJsonRequest(request)) {
            return new ResponseEntity<>(apiError, status);
        }

        model.addAttribute("apiError", apiError);
        return "error";
    }

    /**
     * Détecte si la requête attend une réponse JSON (API) ou une page HTML (navigateur).
     */
    private boolean isJsonRequest(HttpServletRequest request) {
        String acceptHeader = request.getHeader("Accept");
        String uri = request.getRequestURI();
        return (uri != null && uri.startsWith("/api/")) ||
               (acceptHeader != null && acceptHeader.contains(MediaType.APPLICATION_JSON_VALUE));
    }
}
