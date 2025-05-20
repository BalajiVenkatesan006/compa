package com.dungeoncrawler.dungeongame.model;




import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {
    @Test
    void withPlayer_updatesPlayerOnly() {
        CharacterRole oldPlayer = new CharacterRole("Old", 100, 100, 10, 5, 0, 1, 0, 0);
        CharacterRole newPlayer = new CharacterRole("New", 50, 100, 10, 5, 0, 1, 0, 0);
        GameState state = new GameState(oldPlayer, List.of("Test"), false, null);
        
        GameState updated = state.withPlayer(newPlayer);
        assertEquals("New", updated.player().name());
        assertEquals("Old", state.player().name()); // Original unchanged
    }
}