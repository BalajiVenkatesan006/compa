package com.dungeoncrawler.dungeongame.service;

import org.junit.jupiter.api.*;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import com.dungeoncrawler.dungeongame.storage.FileStorageStrategy;

import com.dungeoncrawler.dungeongame.model.*;

class SaveServiceTest {
    private static final String TEST_SAVE = "test_save.ser";
    private SaveService saveService;

    @BeforeEach
    void setUp() {
        // Use a test-specific file for each test
        saveService = new SaveService(new FileStorageStrategy(TEST_SAVE));
    }

    @Test
    void saveAndLoad_preservesGameState() throws Exception {
        CharacterRole player = new CharacterRole("Test", 50, 100, 10, 5, 30, 2, 1, 1);
        GameState originalState = new GameState(player, List.of("Test message"), true, 
            new Monster("Slime", 20, 5, 0));
        
        saveService.saveGame(originalState);
        GameState loadedState = saveService.loadGame();
        
        assertEquals(originalState.player().name(), loadedState.player().name());
        assertEquals(originalState.messages().size(), loadedState.messages().size());
    }

    @Test
    void saveExists_returnsCorrectStatus() throws IOException {
        assertFalse(saveService.saveExists());
        
        CharacterRole player = new CharacterRole("Test", 100, 100, 10, 5, 0, 1, 0, 0);
        GameState state = new GameState(player, List.of(), false, null);
        saveService.saveGame(state);
        
        assertTrue(saveService.saveExists());
    }

    @AfterEach
    void tearDown() {
        // Clean up test file
        new java.io.File(TEST_SAVE).delete();
    }
}