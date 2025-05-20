package com.dungeoncrawler.dungeongame.storage;


import com.dungeoncrawler.dungeongame.model.GameState;
import java.io.IOException;
import  com.dungeoncrawler.dungeongame.iservice.StorageStrategy;

public class DatabaseStorageStrategy implements StorageStrategy {
    @Override
    public void save(GameState state) throws IOException {
        // Implement database save logic
    }

    @Override
    public GameState load() throws IOException, ClassNotFoundException {
        // Implement database load logic
        return null;
    }

    @Override
    public boolean exists() {
        // Implement database check
        return false;
    }
}