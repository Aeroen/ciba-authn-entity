package com.hitachi.oss.ciba.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHelper {

    private static final ObjectMapper mapper = new ObjectMapper();

    private JsonHelper() {
    }

    public static String convertToJson(Object entity) {
        try {
            return mapper.writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Cannot convert object to string, error: " + e.getMessage());
        }
    }
}
