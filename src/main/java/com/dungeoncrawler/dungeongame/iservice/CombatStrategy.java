package com.dungeoncrawler.dungeongame.iservice;

import com.dungeoncrawler.dungeongame.model.CharacterRole;
import com.dungeoncrawler.dungeongame.model.Monster;
import java.util.List;

public interface CombatStrategy {
    CombatResult execute(CharacterRole player, Monster monster);
    
    record CombatResult(
        CharacterRole player, 
        Monster monster, 
        List<String> messages
    ) {}
}