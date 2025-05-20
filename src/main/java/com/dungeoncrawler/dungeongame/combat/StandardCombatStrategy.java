package com.dungeoncrawler.dungeongame.combat;

import com.dungeoncrawler.dungeongame.iservice.CombatStrategy;
import com.dungeoncrawler.dungeongame.model.CharacterRole;
import com.dungeoncrawler.dungeongame.model.Monster;
import java.util.List;

public class StandardCombatStrategy implements CombatStrategy {
    private static final int BASE_EXP_REWARD = 20;
    
    @Override
    public CombatResult execute(CharacterRole player, Monster monster) {
        int playerDamage = calculatePlayerDamage(player, monster);
        Monster damagedMonster = applyDamage(monster, playerDamage);
        
        if (damagedMonster.health() <= 0) {
            return createVictoryResult(player, monster);
        }
        
        return handleMonsterCounter(player, damagedMonster);
    }
    
    private int calculatePlayerDamage(CharacterRole player, Monster monster) {
        return Math.max(1, player.attack() - monster.defense());
    }
    
    private Monster applyDamage(Monster monster, int damage) {
        return monster.withHealth(monster.health() - damage);
    }
    
    private CombatResult createVictoryResult(CharacterRole player, Monster monster) {
        int expGained = BASE_EXP_REWARD + monster.attack() * 2;
        return new CombatResult(
            player.addExperience(expGained),
            null,
            List.of(
                "You defeated the " + monster.name() + "!",
                "Gained " + expGained + " XP!"
            )
        );
    }
    
    private CombatResult handleMonsterCounter(CharacterRole player, Monster monster) {
        int monsterDamage = Math.max(1, monster.attack() - player.defense());
        CharacterRole damagedPlayer = player.withHealth(player.health() - monsterDamage);
        
        if (damagedPlayer.health() <= 0) {
            return new CombatResult(
                damagedPlayer,
                null,
                List.of(
                    "You were defeated by the " + monster.name() + "!",
                    "Game Over!"
                )
            );
        }
        
        return new CombatResult(
            damagedPlayer,
            monster,
            List.of(
                "You hit for " + (player.attack() - monster.defense()) + " damage!",
                monster.name() + " hits back for " + monsterDamage + " damage!"
            )
        );
    }
}