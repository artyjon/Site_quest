package com.quest.dto;

import com.quest.entity.User;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SessionUserTest {

    @Test
    void givenValidUser_whenCreatingSessionUser_thenFieldsAreCopiedCorrectly() {
        Map<String, QuestStatistics> stats = new HashMap<>();
        stats.put("plant_worker", new QuestStatistics(5, 2, 3));

        User user = new User();
        user.setId(1L);
        user.setName("Ростислав");
        user.setQuests(stats);

        SessionUser sessionUser = new SessionUser(user);

        assertEquals(1L, sessionUser.getId());
        assertEquals("Ростислав", sessionUser.getName());
        assertEquals(stats, sessionUser.getQuests());
    }

    @Test
    void givenUserWithNoQuests_whenCreatingSessionUser_thenQuestsMapIsNull() {
        User user = new User();
        user.setId(2L);
        user.setName("Глаша");
        user.setQuests(null);

        SessionUser sessionUser = new SessionUser(user);

        assertEquals(2L, sessionUser.getId());
        assertEquals("Глаша", sessionUser.getName());
        assertNull(sessionUser.getQuests());
    }

    @Test
    void givenEmptyUser_whenCreatingSessionUser_thenFieldsAreEmpty() {
        User user = new User();

        SessionUser sessionUser = new SessionUser(user);

        assertNull(sessionUser.getId());
        assertNull(sessionUser.getName());
        assertTrue(sessionUser.getQuests().isEmpty());
    }
}