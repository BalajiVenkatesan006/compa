package com.dungeoncrawler.dungeongame.model;

import java.io.Serializable;

public record Monster(
    String name,
    int health,
    int attack,
    int defense
) implements Serializable {
    public Monster withHealth(int newHealth) {
        return new Monster(name, newHealth, attack, defense);
    }
}
