package org.beob2020.util;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Provider
public class CustomLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        String date = jsonParser.getText();
        return LocalDateTime.parse(date, formatter);
    }
}
