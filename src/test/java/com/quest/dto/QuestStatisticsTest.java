package com.quest.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestStatisticsTest {

    @Test
    void givenNewInstance_whenCreatedWithNoArgs_thenAllFieldsZero() {
        QuestStatistics stats = new QuestStatistics();

        assertEquals(0, stats.getTotalGamesPlayed());
        assertEquals(0, stats.getWins());
        assertEquals(0, stats.getLosses());
    }

    @Test
    void givenNewInstance_whenIncrementWin_thenWinsAndTotalGamesIncrease() {
        QuestStatistics stats = new QuestStatistics();

        stats.incrementWin();

        assertEquals(1, stats.getTotalGamesPlayed());
        assertEquals(1, stats.getWins());
        assertEquals(0, stats.getLosses());
    }

    @Test
    void givenNewInstance_whenIncrementLoss_thenLossesAndTotalGamesIncrease() {
        QuestStatistics stats = new QuestStatistics();

        stats.incrementLoss();

        assertEquals(1, stats.getTotalGamesPlayed());
        assertEquals(0, stats.getWins());
        assertEquals(1, stats.getLosses());
    }

    @Test
    void givenStatsWithInitialValues_whenCreatedWithArgs_thenFieldsAreSet() {
        QuestStatistics stats = new QuestStatistics(10, 4, 6);

        assertEquals(10, stats.getTotalGamesPlayed());
        assertEquals(4, stats.getWins());
        assertEquals(6, stats.getLosses());
    }

    @Test
    void givenStats_whenIncrementMultipleTimes_thenAllCountersUpdatedCorrectly() {
        QuestStatistics stats = new QuestStatistics();

        stats.incrementWin();
        stats.incrementWin();
        stats.incrementLoss();

        assertEquals(3, stats.getTotalGamesPlayed());
        assertEquals(2, stats.getWins());
        assertEquals(1, stats.getLosses());
    }
}