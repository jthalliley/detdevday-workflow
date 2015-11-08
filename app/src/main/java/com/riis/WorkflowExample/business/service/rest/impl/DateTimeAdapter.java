package com.riis.WorkflowExample.business.service.rest.impl;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.lang.reflect.Type;

/**
 * Serializes/deserializes DateTime objects.
 */
public class DateTimeAdapter implements JsonSerializer<DateTime>, JsonDeserializer<DateTime> {

    // Handles, for example, 2015-08-31T13:44:30.740Z
    private static final String PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    @Override public JsonElement serialize(final DateTime                 src,
                                           final Type                     typeOfSrc,
                                           final JsonSerializationContext context) {

        final DateTimeFormatter fmt = DateTimeFormat.forPattern(PATTERN);
        return new JsonPrimitive(fmt.print(src));
    }

    @Override public DateTime deserialize(final JsonElement                json,
                                          final Type                       typeOfT,
                                          final JsonDeserializationContext context)
        throws JsonParseException {

        if (json == null || json.getAsString() == null || json.getAsString().isEmpty()) return null;

        final DateTimeFormatter fmt = DateTimeFormat.forPattern(PATTERN);
        return fmt.parseDateTime(json.getAsString());
    }
}
