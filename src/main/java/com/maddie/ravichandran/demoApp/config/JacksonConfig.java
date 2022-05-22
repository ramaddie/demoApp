package com.maddie.ravichandran.demoApp.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class JacksonConfig
{
    @Bean
    @Primary
    public JavaTimeModule javaTimeModule()
    {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(ZonedDateTime.class,
                new ZonedDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")));
        return javaTimeModule;
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder, JavaTimeModule javaTimeModule, SimpleModule uriIdModule)
    {
        ObjectMapper objectMapper = builder.modules(javaTimeModule, uriIdModule)
                .indentOutput(true)
                .createXmlMapper(false)
                .build()
                .enable(JsonParser.Feature.STRICT_DUPLICATE_DETECTION)
                .configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false)
                .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,true);
        return objectMapper;
    }
}
