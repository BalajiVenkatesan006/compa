package com.dungeoncrawler.dungeongame.service;




import com.dungeoncrawler.dungeongame.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class GameServiceTest {
    private final GameService gameService = new GameService();

    @Test
    void movePlayer_validMove_updatesPosition() {
        CharacterRole player = new CharacterRole ("Test", 100, 100, 10, 5, 0, 1, 2, 2);
        GameState state = new GameState(player, List.of(), false, null);
        
        GameState newState = gameService.movePlayer(state, "N");
        assertEquals(1, newState.player().y());
    }

    @Test
    void movePlayer_invalidMove_keepsPosition() {
        CharacterRole player = new CharacterRole("Test", 100, 100, 10, 5, 0, 1, 0, 0);
        GameState state = new GameState(player, List.of(), false, null);
        
        GameState newState = gameService.movePlayer(state, "W");
        assertEquals(0, newState.player().x());
        assertTrue(newState.messages().get(0).contains("wall"));
    }
}