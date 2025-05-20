package com.dungeoncrawler.dungeongame;


import java.util.Scanner;

import java.io.IOException;

import com.dungeoncrawler.dungeongame.model.GameState;
import com.dungeoncrawler.dungeongame.model.CharacterRole;
import com.dungeoncrawler.dungeongame.model.Monster;

import com.dungeoncrawler.dungeongame.service.GameService;
import com.dungeoncrawler.dungeongame.service.SaveService;
import com.dungeoncrawler.dungeongame.util.ConsoleColors;

import java.util.List;


public class DungeongameApplication {
    private static GameState currentState;
    private static final GameService gameService = new GameService();
    private static final SaveService saveService = new SaveService();
    private static final Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        printWelcome();
        initializeGame();
        gameLoop();
        scanner.close();
    }
    
    private static void printWelcome() {
        System.out.println(ConsoleColors.PURPLE + "==================================");
        System.out.println("      DUNGEON CRAWLER RPG      ");
        System.out.println("==================================" + ConsoleColors.RESET);
    }
    
    private static void initializeGame() {
        if (saveService.saveExists()) {
            System.out.print("A saved game exists. Load it? (Y/N): ");
            String choice = scanner.nextLine().trim().toUpperCase();
            
            if (choice.equals("Y")) {
                try {
                    currentState = saveService.loadGame();
                    System.out.println(ConsoleColors.GREEN + "Game loaded successfully!" + ConsoleColors.RESET);
                    return;
                } catch (Exception e) {
                    System.out.println(ConsoleColors.RED + "Failed to load game: " + e.getMessage() + ConsoleColors.RESET);
                }
            }
        }
        
        System.out.print("Enter your CharacterRole name: ");
        String name = scanner.nextLine().trim();
        currentState = gameService.createNewGame(name);
    }
    
    private static void gameLoop() {
        while (true) {
            printGameState();
            
            if (currentState.player().health() <= 0) {
                System.out.println(ConsoleColors.RED_BOLD + "GAME OVER" + ConsoleColors.RESET);
                break;
            }
            
            System.out.print("> ");
            String input = scanner.nextLine().trim().toUpperCase();
            
            if (input.equals("Q")) {
                System.out.print("Save before quitting? (Y/N): ");
                String saveChoice = scanner.nextLine().trim().toUpperCase();
                if (saveChoice.equals("Y")) {
                    try {
                        saveService.saveGame(currentState);
                        System.out.println(ConsoleColors.GREEN + "Game saved!" + ConsoleColors.RESET);
                    } catch (IOException e) {
                        System.out.println(ConsoleColors.RED + "Failed to save game: " + e.getMessage() + ConsoleColors.RESET);
                    }
                }
                break;
            }
            
            if (currentState.inCombat()) {
                handleCombat(input);
            } else {
                handleExploration(input);
            }
        }
    }
    
    private static void printGameState() {
        clearConsole();
        
        CharacterRole player = currentState.player();
        System.out.println(ConsoleColors.CYAN + "=== " + player.name() + " ===" + ConsoleColors.RESET);
        System.out.printf("HP: %d/%d | ATK: %d | DEF: %d%n", 
            player.health(), player.maxHealth(), player.attack(), player.defense());
        System.out.printf("Level: %d | XP: %d/100%n", player.level(), player.experience());
        System.out.println("Position: (" + player.x() + "," + player.y() + ")");
        
        System.out.println(ConsoleColors.YELLOW + "----------------------------" + ConsoleColors.RESET);
        
        for (String message : currentState.messages()) {
            System.out.println(message);
        }
        
        System.out.println();
        
        if (currentState.inCombat()) {
            Monster monster = currentState.currentMonster();
            System.out.println(ConsoleColors.RED + "COMBAT! " + monster.name() + 
                " (HP: " + monster.health() + ")" + ConsoleColors.RESET);
            System.out.println("[1] Attack  [2] Defend  [3] Flee");
        } else {
            System.out.println("Move: [N]orth [S]outh [E]ast [W]est");
            System.out.println("[Q]uit");
        }
    }
    
    private static void handleCombat(String input) {
        Monster monster = currentState.currentMonster();
        
        switch (input) {
            case "1" -> { // Attack
                var result = gameService.executeCombat(currentState.player(), monster);
                currentState = currentState
                    .withPlayer(result.player())
                    .withMessages(result.messages());
                
                if (result.monster() == null) {
                    currentState = currentState.withoutCombat();
                } else {
                    currentState = currentState.withCombat(result.monster());
                }
            }
            case "2" -> { // Defend
                int reducedDamage = Math.max(1, monster.attack() / 2 - currentState.player().defense());
                CharacterRole defendedPlayer = currentState.player().withHealth(
                    currentState.player().health() - reducedDamage
                );
                
                currentState = currentState
                    .withPlayer(defendedPlayer)
                    .withMessages(List.of(
                        "You defend against the " + monster.name() + "!",
                        "Took " + reducedDamage + " damage (reduced)."
                    ));
                
                if (defendedPlayer.health() <= 0) {
                    currentState = currentState.withMessages(
                        List.of("You were defeated by the " + monster.name() + "!")
                    );
                }
            }
            case "3" -> { // Flee
                if (Math.random() < 0.5) {
                    currentState = currentState
                        .withoutCombat()
                        .withMessages(List.of("You successfully fled from combat!"));
                } else {
                    int damage = Math.max(1, monster.attack() - currentState.player().defense());
                    CharacterRole damagedPlayer = currentState.player().withHealth(
                        currentState.player().health() - damage
                    );
                    
                    currentState = currentState
                        .withPlayer(damagedPlayer)
                        .withMessages(List.of(
                            "You failed to flee!",
                            "The " + monster.name() + " hits you for " + damage + " damage!"
                        ));
                    
                    if (damagedPlayer.health() <= 0) {
                        currentState = currentState.withMessages(
                            List.of("You were defeated by the " + monster.name() + "!")
                        );
                    }
                }
            }
            default -> {
                currentState = currentState.withMessages(
                    List.of("Invalid command. Use 1 (Attack), 2 (Defend), or 3 (Flee).")
                );
            }
        }
    }
    
    private static void handleExploration(String input) {
        if (input.equals("N") || input.equals("S") || input.equals("E") || input.equals("W")) {
            currentState = gameService.movePlayer(currentState, input);
        } else {
            currentState = currentState.withMessages(
                List.of("Invalid command. Use N/S/E/W to move or Q to quit.")
            );
        }
    }
    
    private static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("\n".repeat(50));
        }
    }
}