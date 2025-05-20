package com.dungeoncrawler.dungeongame.model;

import java.io.Serializable;
import java.util.List;

public record GameState(
    CharacterRole player,
    List<String> messages,
    boolean inCombat,
    Monster currentMonster
) implements Serializable {
    public GameState withPlayer(CharacterRole newPlayer) {
        return new GameState(newPlayer, messages, inCombat, currentMonster);
    }
    
    public GameState withMessages(List<String> newMessages) {
        return new GameState(player, newMessages, inCombat, currentMonster);
    }
    
    public GameState withCombat(Monster monster) {
        return new GameState(player, messages, true, monster);
    }
    
    public GameState withoutCombat() {
        return new GameState(player, messages, false, null);
    }
}