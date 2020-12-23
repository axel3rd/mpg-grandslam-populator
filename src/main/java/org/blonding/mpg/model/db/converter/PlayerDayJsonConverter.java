package org.blonding.mpg.model.db.converter;

import java.io.IOException;
import java.util.List;

import javax.persistence.AttributeConverter;

import org.blonding.mpg.model.bean.PlayerDayJson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PlayerDayJsonConverter implements AttributeConverter<List<PlayerDayJson>, String> {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<PlayerDayJson> attribute) {
        try {
            return mapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    @Override
    public List<PlayerDayJson> convertToEntityAttribute(String json) {
        try {
            // TODO: Why this kind of mapper ??? To understand
            String s = json;
            if (s.startsWith("\"")) {
                s = mapper.readValue(s, String.class);
            }
            return mapper.readValue(s, new TypeReference<List<PlayerDayJson>>() {
            });
        } catch (IOException e) {
            throw new UnsupportedOperationException(String.format("Problem with Json; %s", json), e);
        }
    }
}
