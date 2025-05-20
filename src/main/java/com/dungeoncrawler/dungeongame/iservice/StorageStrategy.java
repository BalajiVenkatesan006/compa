package com.dungeoncrawler.dungeongame.iservice;

import com.dungeoncrawler.dungeongame.model.GameState;
import java.io.IOException;

public interface StorageStrategy {
    void save(GameState state) throws IOException;
    GameState load() throws IOException, ClassNotFoundException;
    boolean exists();
}