package com.example.demoapp.config;

import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.core.StreamReadFeature;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.ValueSerializer;
import tools.jackson.databind.cfg.DateTimeFeature;
import tools.jackson.databind.module.SimpleModule;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class JacksonConfig
{
    private static final DateTimeFormatter ZONED_DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    @Bean
    public JsonMapperBuilderCustomizer jsonMapperBuilderCustomizer()
    {
        SimpleModule zonedDateTimeModule = new SimpleModule();
        zonedDateTimeModule.addSerializer(ZonedDateTime.class, new ValueSerializer<>()
        {
            @Override
            public void serialize(ZonedDateTime value, JsonGenerator gen, SerializationContext ctxt) throws JacksonException
            {
                gen.writeString(ZONED_DATE_TIME_FORMATTER.format(value));
            }
        });

        return builder -> builder
                .addModule(zonedDateTimeModule)
                .enable(SerializationFeature.INDENT_OUTPUT)
                .enable(StreamReadFeature.STRICT_DUPLICATE_DETECTION)
                .disable(DateTimeFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS)
                .enable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES)
                .enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }
}
