package com.example.cardgame.controller;

import com.example.cardgame.service.CardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CardController.class)
@Import(CardService.class)
@DisplayName("Tests d'Intégration Web - CardController")
class CardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET / doit rediriger vers /random-hand-cards")
    void testRootRedirectsToRandomHandCards() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/random-hand-cards"));
    }

    @Test
    @DisplayName("GET /random-hand-cards doit retourner 200 OK avec le modèle complet")
    void testGetRandomHandCardsReturnsViewWithModel() throws Exception {
        mockMvc.perform(get("/random-hand-cards"))
                .andExpect(status().isOk())
                .andExpect(view().name("random-hand-cards"))
                .andExpect(model().attributeExists("fullDeck"))
                .andExpect(model().attributeExists("randomHand"))
                .andExpect(model().attributeExists("sortedHand"))
                .andExpect(model().attribute("randomHand", hasSize(10)))
                .andExpect(model().attribute("sortedHand", hasSize(10)))
                .andExpect(model().attribute("fullDeck", hasSize(52)));
    }

    @Test
    @DisplayName("GET /api/random-hand-cards doit retourner un JSON structuré 200 OK")
    void testGetRandomHandCardsJsonEndpoint() throws Exception {
        mockMvc.perform(get("/api/random-hand-cards").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.fullDeck", hasSize(52)))
                .andExpect(jsonPath("$.randomHand", hasSize(10)))
                .andExpect(jsonPath("$.sortedHand", hasSize(10)));
    }
}
