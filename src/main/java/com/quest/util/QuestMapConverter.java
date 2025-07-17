package com.quest.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quest.dto.QuestStatistics;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Converter
public final class QuestMapConverter implements AttributeConverter<Map<String, QuestStatistics>, String> {

    private static final Logger LOG = LoggerFactory.getLogger(QuestMapConverter.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, QuestStatistics> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            LOG.error("Ошибка сериализации карты квестов: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public Map<String, QuestStatistics> convertToEntityAttribute(String dbData) {
        try {
            if (dbData == null || dbData.isBlank()) {
                return new HashMap<>();
            }
            return objectMapper.readValue(dbData, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            LOG.error("Ошибка десериализации карты квестов: {}", e.getMessage());
            return new HashMap<>();
        }
    }
}