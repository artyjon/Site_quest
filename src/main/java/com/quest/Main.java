package com.quest;


import com.quest.config.DBConfig;

public class Main {
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(DBConfig::shutdown));
    }
}