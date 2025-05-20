package com.dungeoncrawler.dungeongame.service;

import com.dungeoncrawler.dungeongame.model.*;
import java.util.List;
import java.util.Random;
import com.dungeoncrawler.dungeongame.iservice.CombatStrategy.CombatResult;
import com.dungeoncrawler.dungeongame.combat.CriticalHitCombatStrategy;


public class GameService {
    private static final int DUNGEON_SIZE = 5;
    private static final Random RANDOM = new Random();
    private static final List<Monster> MONSTERS = List.of(
        new Monster("Goblin", 30, 8, 2),
        new Monster("Skeleton", 25, 10, 3),
        new Monster("Orc", 40, 12, 5),
        new Monster("Slime", 20, 5, 0)
    );
    
    private final CombatService combatService;
    
    public GameService() {
        this.combatService = new CombatService(new CriticalHitCombatStrategy());
    }
    
    public GameState createNewGame(String playerName) {
        CharacterRole player = new CharacterRole(
            playerName,
            100, 100, 10, 5, 0, 1,
            DUNGEON_SIZE / 2, DUNGEON_SIZE / 2
        );
        
        return new GameState(
            player,
            List.of(
                "Welcome to the Dungeon Crawler, " + playerName + "!",
                "Navigate with N/S/E/W commands.",
                "Good luck!"
            ),
            false,
            null
        );
    }

    // In GameService.java
    public CombatResult executeCombat(CharacterRole player, Monster monster) {
        return combatService.fight(player, monster);
    }
    
    public GameState movePlayer(GameState currentState, String direction) {
        CharacterRole player = currentState.player();
        int newX = player.x();
        int newY = player.y();

        switch (direction.toUpperCase()) {
            case "N" -> newY--;
            case "S" -> newY++;
            case "E" -> newX++;
            case "W" -> newX--;
            default -> {
                return currentState.withMessages(
                    List.of("Invalid direction. Use N/S/E/W.")
                );
            }
        }

        if (newX < 0 || newX >= DUNGEON_SIZE || newY < 0 || newY >= DUNGEON_SIZE) {
            return currentState.withMessages(
                List.of("You can't go that way - the dungeon wall blocks your path.")
            );
        }

        CharacterRole movedPlayer = player.withPosition(newX, newY);
        GameState newState = currentState.withPlayer(movedPlayer);

        if (RANDOM.nextDouble() < 0.3) {
            Monster monster = MONSTERS.get(RANDOM.nextInt(MONSTERS.size()));
            return newState.withMessages(
                List.of(
                    "You enter room (" + newX + "," + newY + ")...",
                    "A " + monster.name() + " appears!"
                )
            ).withCombat(monster);
        }

        return newState.withMessages(
            List.of("You enter room (" + newX + "," + newY + ")... Nothing here.")
        );
    }
}