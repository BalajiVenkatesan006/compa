package com.dungeoncrawler.dungeongame.combat;


import com.dungeoncrawler.dungeongame.model.CharacterRole;
import com.dungeoncrawler.dungeongame.model.Monster;
import java.util.List;
import com.dungeoncrawler.dungeongame.iservice.CombatStrategy;
import java.util.stream.Stream;
import com.dungeoncrawler.dungeongame.iservice.RandomGenerator;
import com.dungeoncrawler.dungeongame.randomgenerator.DefaultRandomGenerator;

public class CriticalHitCombatStrategy implements CombatStrategy {
    private static final int BASE_EXP_REWARD = 20;
    private static final double CRITICAL_CHANCE = 0.2; // 20% chance
    private static final int CRITICAL_MULTIPLIER = 2;
    private final RandomGenerator random;

    public CriticalHitCombatStrategy() {
        this.random = new DefaultRandomGenerator();
    }

    // For testing with controlled random
    public CriticalHitCombatStrategy(RandomGenerator random) {
        this.random = random;
    }

    @Override
    public CombatResult execute(CharacterRole player, Monster monster) {
        // Player attack phase
        boolean isCritical = random.nextDouble() < CRITICAL_CHANCE;
        int playerDamage = calculateDamage(player, monster, isCritical);
        Monster damagedMonster = monster.withHealth(monster.health() - playerDamage);

        List<String> messages = List.of(
            buildAttackMessage(player, monster, playerDamage, isCritical)
        );

        // Check if monster is defeated
        if (damagedMonster.health() <= 0) {
            return new CombatResult(
                awardExperience(player, monster, isCritical),
                null,
                addMessage(messages, monster.name() + " defeated!")
            );
        }

        // Monster counter-attack
        int monsterDamage = Math.max(1, monster.attack() - player.defense());
        CharacterRole damagedPlayer = player.withHealth(player.health() - monsterDamage);

        messages = addMessage(messages, 
            monster.name() + " hits back for " + monsterDamage + " damage!");

        // Check if player is defeated
        if (damagedPlayer.health() <= 0) {
            return new CombatResult(
                damagedPlayer,
                null,
                addMessage(messages, "You were defeated!")
            );
        }

        return new CombatResult(damagedPlayer, damagedMonster, messages);
    }

    private int calculateDamage(CharacterRole player, Monster monster, boolean isCritical) {
        int baseDamage = Math.max(1, player.attack() - monster.defense());
        return isCritical ? baseDamage * CRITICAL_MULTIPLIER : baseDamage;
    }

    private CharacterRole awardExperience(CharacterRole player, Monster monster, boolean isCritical) {
        int expGained = BASE_EXP_REWARD + monster.attack() * 2;
        if (isCritical) {
            expGained += 10; // Bonus XP for critical hits
        }
        return player.addExperience(expGained);
    }

    private String buildAttackMessage(CharacterRole player, Monster monster, int damage, boolean isCritical) {
        String base = player.name() + " hits " + monster.name() + " for " + damage + " damage";
        return isCritical ? "CRITICAL HIT! " + base + "!" : base + ".";
    }

    private List<String> addMessage(List<String> existing, String newMessage) {
        return Stream.concat(existing.stream(), Stream.of(newMessage))
                    .toList();
    }
}