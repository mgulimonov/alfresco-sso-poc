package com.max.alfboot.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import org.springframework.boot.autoconfigure.data.rest.SpringBootRepositoryRestMvcConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Maxim on 17-May-15.
 */
@Configuration
public class RestConfig extends SpringBootRepositoryRestMvcConfiguration {

    @Override
    protected void configureJacksonObjectMapper(ObjectMapper objectMapper) {
        super.configureJacksonObjectMapper(objectMapper);
        objectMapper.registerModule(new JSR310Module());
    }
}
