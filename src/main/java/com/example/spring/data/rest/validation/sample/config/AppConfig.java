package com.example.spring.data.rest.validation.sample.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class AppConfig {

    @Bean
    public JpaProperties jpaProperties() {
        JpaProperties jpaProperties = new JpaProperties();
        jpaProperties.setOpenInView(false);
        return jpaProperties;
    }

    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();

        Hibernate5JakartaModule hibernate5JakartaModule = new Hibernate5JakartaModule();
        hibernate5JakartaModule.disable(Hibernate5JakartaModule.Feature.USE_TRANSIENT_ANNOTATION);
        hibernate5JakartaModule.enable(Hibernate5JakartaModule.Feature.FORCE_LAZY_LOADING);
        objectMapper.registerModule(hibernate5JakartaModule);

        return objectMapper;
    }

}
