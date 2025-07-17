package com.quest.util;

import com.quest.dto.QuestStatistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class QuestMapConverterTest {

    private QuestMapConverter converter;

    @BeforeEach
    void setUp() {
        converter = new QuestMapConverter();
    }

    @Test
    void givenValidMap_whenConvertToDatabaseColumn_thenReturnJsonString() {
        Map<String, QuestStatistics> map = new HashMap<>();
        map.put("plant_worker", new QuestStatistics(10, 5, 5));

        String json = converter.convertToDatabaseColumn(map);

        assertNotNull(json);
        assertTrue(json.contains("plant_worker"));
        assertTrue(json.contains("totalGamesPlayed"));
    }

    @ParameterizedTest
    @NullSource
    void givenNullMap_whenConvertToDatabaseColumn_thenReturnNull(Map<String, QuestStatistics> map) {
        String json = converter.convertToDatabaseColumn(map);
        assertNull(json);
    }

    @Test
    void givenValidJson_whenConvertToEntityAttribute_thenReturnMap() {
        String json = "{\"plant_worker\":{\"totalGamesPlayed\":10,\"wins\":4,\"losses\":6}}";

        Map<String, QuestStatistics> result = converter.convertToEntityAttribute(json);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.containsKey("plant_worker"));
        assertEquals(10, result.get("plant_worker").getTotalGamesPlayed());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"   ", "this is not json"})
    void givenIncorrectJson_whenConvertToEntityAttribute_thenReturnEmptyMap(String json) {
        Map<String, QuestStatistics> result = converter.convertToEntityAttribute(json);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}