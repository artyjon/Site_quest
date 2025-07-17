package com.quest.entity;

import com.quest.dto.QuestStatistics;
import com.quest.util.QuestMapConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    public User(String log, String name, String pass) {
        this.log = log;
        this.name = name;
        this.pass = pass;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String log;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String pass;

    @Convert(converter = QuestMapConverter.class)
    @Column(columnDefinition = "TEXT")
    private Map<String, QuestStatistics> quests = new HashMap<>();
}
