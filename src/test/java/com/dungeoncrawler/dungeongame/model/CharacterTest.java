package com.dungeoncrawler.dungeongame.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {
    @Test
    void CharacterRoleCreation_validStats_success() {
        CharacterRole CharacterRole = new CharacterRole("Hero", 100, 150, 10, 5, 0, 1, 2, 2);
        assertEquals("Hero", CharacterRole.name());
        assertEquals(100, CharacterRole.health());
    }

    @Test
    void withHealth_updatesCorrectly() {
        CharacterRole original = new CharacterRole("Test", 100, 100, 10, 5, 0, 1, 0, 0);
        CharacterRole updated = original.withHealth(50);
        assertEquals(50, updated.health());
        assertEquals(100, original.health()); // Original unchanged
    }

    @Test
    void addExperience_levelUpIncreasesStats() {
        CharacterRole CharacterRole = new CharacterRole("Test", 100, 100, 10, 5, 95, 1, 0, 0);
        CharacterRole leveledUp = CharacterRole.addExperience(10);
        assertEquals(2, leveledUp.level());
        assertEquals(12, leveledUp.attack());
        assertEquals(5, leveledUp.defense());
    }
}