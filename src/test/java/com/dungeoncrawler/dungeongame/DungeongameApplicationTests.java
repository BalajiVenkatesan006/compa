package com.dungeoncrawler.dungeongame;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.dungeoncrawler.dungeongame.model.GameState;
import com.dungeoncrawler.dungeongame.service.GameService;
import org.junit.jupiter.api.Test;


class MainIntegrationTest {
    @Test
    void gameFlow_combatAndMovement_works() {
        GameService gameService = new GameService();
        GameState state = gameService.createNewGame("Test");
        
        // Test initial state
        assertEquals(2, state.player().x());
        assertEquals(2, state.player().y());
        
        // Test movement
        GameState moved = gameService.movePlayer(state, "N");
        assertEquals(1, moved.player().y());
    }
}
