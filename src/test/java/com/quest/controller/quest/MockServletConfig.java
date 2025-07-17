package com.quest.controller.quest;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class MockServletConfig implements ServletConfig {
    @Override public String getServletName() { return "QuestController"; }
    @Override public ServletContext getServletContext() { return null; }
    @Override public String getInitParameter(String name) {
        if ("questsPath".equals(name)) return "quests";
        return null;
    }
    @Override public Enumeration<String> getInitParameterNames() {
        return Collections.enumeration(List.of("questsPath"));
    }
}