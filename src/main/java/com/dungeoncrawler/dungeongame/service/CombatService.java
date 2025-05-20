package com.dungeoncrawler.dungeongame.service;

import com.dungeoncrawler.dungeongame.iservice.CombatStrategy.CombatResult;
import com.dungeoncrawler.dungeongame.iservice.CombatStrategy;
import com.dungeoncrawler.dungeongame.combat.StandardCombatStrategy;
import com.dungeoncrawler.dungeongame.model.CharacterRole;
import com.dungeoncrawler.dungeongame.model.Monster;

public class CombatService {
    private final CombatStrategy strategy;
    
    // Default constructor uses standard combat
    public CombatService() {
        this(new StandardCombatStrategy());
    }
    
    // Constructor for custom strategies
    public CombatService(CombatStrategy strategy) {
        this.strategy = strategy;
    }
    
    public CombatResult fight(CharacterRole player, Monster monster) {
        return strategy.execute(player, monster);
    }
}