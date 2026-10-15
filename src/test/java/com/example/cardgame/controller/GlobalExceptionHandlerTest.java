package com.example.cardgame.controller;

import com.example.cardgame.exception.CardGameException;
import com.example.cardgame.service.CardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CardController.class)
@DisplayName("Tests de Gestion Centralisée des Erreurs - GlobalExceptionHandler")
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CardService cardService;

    @Test
    @DisplayName("Requête HTML avec CardGameException : doit retourner HTTP 400 et la vue d'erreur avec ApiError")
    void testHtmlErrorHandling() throws Exception {
        when(cardService.playRound()).thenThrow(new CardGameException("Erreur métier simulée"));

        mockMvc.perform(get("/random-hand-cards").accept(MediaType.TEXT_HTML))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("apiError"))
                .andExpect(model().attribute("apiError", org.hamcrest.Matchers.hasProperty("status", org.hamcrest.Matchers.is(400))))
                .andExpect(model().attribute("apiError", org.hamcrest.Matchers.hasProperty("message", org.hamcrest.Matchers.is("Erreur métier simulée"))));
    }

    @Test
    @DisplayName("Requête JSON avec CardGameException : doit retourner HTTP 400 et une structure ApiError en JSON")
    void testJsonCardGameExceptionHandling() throws Exception {
        when(cardService.playRound()).thenThrow(new CardGameException("Erreur métier API"));

        mockMvc.perform(get("/api/random-hand-cards").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Erreur métier API"))
                .andExpect(jsonPath("$.path").value("/api/random-hand-cards"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    @DisplayName("Exception imprévue : doit retourner HTTP 500 et une structure ApiError adaptée")
    void testUnexpectedExceptionHandling() throws Exception {
        when(cardService.playRound()).thenThrow(new RuntimeException("Crash serveur inattendu"));

        mockMvc.perform(get("/api/random-hand-cards").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.error").value("Internal Server Error"))
                .andExpect(jsonPath("$.message").value("Une erreur inattendue est survenue : Crash serveur inattendu"))
                .andExpect(jsonPath("$.path").value("/api/random-hand-cards"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }
}
