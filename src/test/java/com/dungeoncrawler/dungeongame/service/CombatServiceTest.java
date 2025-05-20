package com.dungeoncrawler.dungeongame.service;

import com.dungeoncrawler.dungeongame.combat.CriticalHitCombatStrategy;
import com.dungeoncrawler.dungeongame.iservice.CombatStrategy.CombatResult;
import com.dungeoncrawler.dungeongame.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.Random;
import com.dungeoncrawler.dungeongame.iservice.RandomGenerator;
import com.dungeoncrawler.dungeongame.randomgenerator.DefaultRandomGenerator;


class CombatServiceTest {
    private final CombatService combatService = new CombatService();

    @Test
    void fight_playerWins_gainsExperience() {
        CharacterRole player = new CharacterRole("Hero", 100, 100, 20, 5, 0, 1, 0, 0);
        Monster monster = new Monster("Goblin", 10, 5, 0);
        
        CombatResult result = combatService.fight(player, monster);
        assertNull(result.monster());
        assertTrue(result.player().experience() > 0);
    }

    @Test
    void fight_monsterSurvives_counterAttacks() {
        CharacterRole player = new CharacterRole("Hero", 100, 100, 5, 2, 0, 1, 0, 0);
        Monster monster = new Monster("Orc", 50, 10, 5);
        
        CombatResult result = combatService.fight(player, monster);
        assertNotNull(result.monster());
        assertTrue(result.player().health() < 100);
    }

    @Test
    void execute_criticalHit_appliesDoubleDamage() {
        // Create a mock of our interface
        RandomGenerator mockRandom = mock(RandomGenerator.class);
        when(mockRandom.nextDouble()).thenReturn(0.1); // < CRITICAL_CHANCE
        
        CharacterRole player = new CharacterRole("Hero", 100, 100, 10, 5, 0, 1, 0, 0);
        Monster monster = new Monster("Orc", 50, 10, 5);
        
        CombatResult result = new CriticalHitCombatStrategy(mockRandom)
            .execute(player, monster);
        
        assertTrue(result.messages().get(0).contains("CRITICAL HIT"));
        assertEquals(40, result.monster().health()); 
    }
}