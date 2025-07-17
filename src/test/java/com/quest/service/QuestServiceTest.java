package com.quest.service;

import com.quest.dto.QuestStatistics;
import com.quest.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class QuestServiceTest {

    private UserRepository userRepository;
    private QuestService questService;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        questService = new QuestService(userRepository);
    }

    @Test
    void givenValidInputs_whenUpdateQuestStatistics_thenRepositoryMethodCalledOnce() {
        Long userId = 42L;
        String questName = "plant_worker";
        QuestStatistics statistics = new QuestStatistics();
        statistics.setTotalGamesPlayed(5);
        statistics.setWins(2);
        statistics.setLosses(3);

        questService.updateQuestStatistics(userId, questName, statistics);

        verify(userRepository, times(1)).updateStats(userId, questName, statistics);
    }

    @Test
    void givenNullStats_whenUpdateQuestStatistics_thenRepositoryMethodCalledWithNull() {
        Long userId = 1L;
        String questName = "snatch";

        questService.updateQuestStatistics(userId, questName, null);

        verify(userRepository, times(1)).updateStats(userId, questName, null);
    }

    @Test
    void givenEmptyQuestName_whenUpdateQuestStatistics_thenRepositoryStillCalled() {
        Long userId = 7L;
        String questName = "";
        QuestStatistics statistics = new QuestStatistics();

        questService.updateQuestStatistics(userId, questName, statistics);

        verify(userRepository, times(1)).updateStats(userId, questName, statistics);
    }
}