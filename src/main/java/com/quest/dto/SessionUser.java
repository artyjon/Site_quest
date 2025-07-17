package com.quest.dto;

import com.quest.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
public class SessionUser implements Serializable {

    private final Long id;
    private final String name;
    private final Map<String, QuestStatistics> quests;

    public SessionUser(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.quests = user.getQuests();
    }
}
