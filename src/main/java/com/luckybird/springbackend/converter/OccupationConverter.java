package com.luckybird.springbackend.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;

import java.util.Arrays;
import java.util.List;

/**
 * @author 新云鸟
 */
public class OccupationConverter implements AttributeConverter<List<String>, String> {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> occupation) {
        try {
            return occupation != null ? OBJECT_MAPPER.writeValueAsString(occupation) : null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        try {
            return dbData != null ? Arrays.asList(OBJECT_MAPPER.readValue(dbData, String[].class)) : null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}