package com.quest.repository;

import com.quest.config.DBConfig;
import com.quest.dto.QuestStatistics;
import com.quest.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryTest {

    private SessionFactory sessionFactory;
    private UserRepository userRepository;

    @BeforeAll
    void setup() {
        sessionFactory = DBConfig.buildTestSessionFactory();
        userRepository = new UserRepository(sessionFactory);
    }

    @BeforeEach
    void cleanUp() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.createQuery("DELETE FROM User").executeUpdate();
        tx.commit();
        session.close();
    }

    @Test
    void givenNewUser_whenSave_thenUserIsPersisted() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        User user = new User("login1", "Name One", "pass123");
        session.persist(user);

        tx.commit();

        Optional<User> found = session.createQuery("FROM User WHERE log = :log", User.class)
                .setParameter("log", "login1")
                .uniqueResultOptional();
        session.close();

        assertTrue(found.isPresent());
        assertEquals("Name One", found.get().getName());
    }

    @Test
    void givenExistingLogin_whenIsUserExists_thenReturnsTrue() {
        User user = new User("login2", "Name Two", "pass123");
        userRepository.save(user);

        assertTrue(userRepository.isUserExists("login2"));
    }

    @Test
    void givenUnknownLogin_whenIsUserExists_thenReturnsFalse() {
        assertFalse(userRepository.isUserExists("not_existing"));
    }

    @Test
    void givenUserWithQuestStats_whenUpdateStats_thenStatsAreUpdated() {
        User user = new User("login3", "Name Three", "pass123");
        userRepository.save(user);

        QuestStatistics stats = new QuestStatistics();
        stats.incrementWin();

        Optional<User> saved = userRepository.findUserByLogin("login3");
        assertTrue(saved.isPresent());

        userRepository.updateStats(saved.get().getId(), "plant_worker", stats);

        Optional<User> updated = userRepository.findUserByLogin("login3");
        assertTrue(updated.isPresent());

        Map<String, QuestStatistics> questMap = updated.get().getQuests();
        assertNotNull(questMap);
        assertTrue(questMap.containsKey("plant_worker"));
        assertEquals(1, questMap.get("plant_worker").getTotalGamesPlayed());
        assertEquals(1, questMap.get("plant_worker").getWins());
        assertEquals(0, questMap.get("plant_worker").getLosses());
    }

    @Test
    void givenUnknownLogin_whenFindUserByLogin_thenReturnEmpty() {
        Optional<User> result = userRepository.findUserByLogin("unknown");
        assertTrue(result.isEmpty());
    }
}