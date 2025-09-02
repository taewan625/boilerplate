package com.exporum.core.configuration.mybatis;

import com.fasterxml.jackson.databind.JsonNode;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 19.
 * @description :
 */

@Configuration
public class MybatisConfiguration {
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {
            configuration.getTypeHandlerRegistry().register(JsonNode.class, JsonNodeTypeHandler.class);
        };
    }
}
