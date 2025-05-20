package com.dungeoncrawler.dungeongame.storage;

import com.dungeoncrawler.dungeongame.iservice.StorageStrategy;
import com.dungeoncrawler.dungeongame.model.GameState;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileStorageStrategy implements StorageStrategy {
    private final Path filePath;

    public FileStorageStrategy(String filename) {
        this.filePath = Path.of(filename);
    }

    @Override
    public void save(GameState state) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
            Files.newOutputStream(filePath))) {
            oos.writeObject(state);
        }
    }

    @Override
    public GameState load() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(
            Files.newInputStream(filePath))) {
            return (GameState) ois.readObject();
        }
    }

    @Override
    public boolean exists() {
        return Files.exists(filePath);
    }
}