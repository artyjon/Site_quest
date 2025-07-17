package com.quest.repository;

import com.quest.dto.QuestStatistics;
import com.quest.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserRepository {

    private static final Logger LOG = LoggerFactory.getLogger(UserRepository.class);
    private final SessionFactory sessionFactory;

    public UserRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(User user)  {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.persist(user);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                LOG.error("Ошибка при сохранении пользователя {}", e.getMessage());
            }
        }
    }

    public boolean isUserExists(String login) {
        try (Session session = sessionFactory.openSession()) {
            Optional<User> user = session.createQuery("FROM User WHERE log = :log", User.class)
                    .setParameter("log", login)
                    .uniqueResultOptional();
            return user.isPresent();
        }
    }

    public Optional<User> findUserByLogin(String login) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User WHERE log = :log", User.class)
                    .setParameter("log", login)
                    .uniqueResultOptional();
        }
    }

    public void updateStats(Long id, String questName, QuestStatistics stats) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                User user = session.get(User.class, id);
                if (user != null) {
                    Map<String, QuestStatistics> questStats = user.getQuests();
                    if (questStats == null) {
                        questStats = new HashMap<>();
                    }
                    questStats.put(questName, stats);
                    user.setQuests(questStats);
                    session.merge(user);
                }
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                LOG.error("Ошибка при обновлении статистики пользователя: {}", e.getMessage());
            }
        }
    }
}