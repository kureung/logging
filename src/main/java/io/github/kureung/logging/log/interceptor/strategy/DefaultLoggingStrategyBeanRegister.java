package io.github.kureung.logging.log.interceptor.strategy;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultLoggingStrategyBeanRegister {
    @Bean
    @ConditionalOnMissingBean
    public DefaultLoggingStrategy defaultLoggingStrategy() {
        return new DefaultLoggingStrategy();
    }
}
