package com.dungeoncrawler.dungeongame.model;

import java.io.Serializable;

public record CharacterRole(
    String name,
    int health,
    int maxHealth,
    int attack,
    int defense,
    int experience,
    int level,
    int x,
    int y
) implements Serializable {
    public CharacterRole withHealth(int newHealth) {
        return new CharacterRole(name, newHealth, maxHealth, attack, defense, experience, level, x, y);
    }
    
    public CharacterRole withPosition(int newX, int newY) {
        return new CharacterRole(name, health, maxHealth, attack, defense, experience, level, newX, newY);
    }
    
    public CharacterRole addExperience(int exp) {
        int newExp = experience + exp;
        int newLevel = level;
        int newMaxHealth = maxHealth;
        int newAttack = attack;
        
        while (newExp >= 100) {
            newExp -= 100;
            newLevel++;
            newMaxHealth += 20;
            newAttack += 2;
        }
        
        return new CharacterRole(
            name, 
            Math.min(health, newMaxHealth),
            newMaxHealth,
            newAttack,
            defense,
            newExp,
            newLevel,
            x,
            y
        );
    }
}