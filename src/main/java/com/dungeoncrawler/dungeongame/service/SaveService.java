package com.dungeoncrawler.dungeongame.service;


import com.dungeoncrawler.dungeongame.model.GameState;
import com.dungeoncrawler.dungeongame.storage.FileStorageStrategy;


import com.dungeoncrawler.dungeongame.iservice.StorageStrategy;

import java.io.IOException;

public class SaveService {
    private final StorageStrategy storageStrategy;

    // Default constructor uses file storage
    public SaveService() {
        this(new FileStorageStrategy("dungeon_save.ser"));
    }

    // Constructor with custom strategy (for testing and extensions)
    public SaveService(StorageStrategy storageStrategy) {
        this.storageStrategy = storageStrategy;
    }
    
    public void saveGame(GameState state) throws IOException {
        storageStrategy.save(state);
    }
    
    public GameState loadGame() throws IOException, ClassNotFoundException {
        return storageStrategy.load();
    }
    
    public boolean saveExists() {
        return storageStrategy.exists();
    }
}