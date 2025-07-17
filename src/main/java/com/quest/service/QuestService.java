package com.quest.service;

import com.quest.dto.QuestStatistics;
import com.quest.repository.UserRepository;

public class QuestService {

    private final UserRepository userRepository;

    public QuestService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void updateQuestStatistics(Long id, String questName, QuestStatistics stats) {
        userRepository.updateStats(id, questName, stats);
    }
}