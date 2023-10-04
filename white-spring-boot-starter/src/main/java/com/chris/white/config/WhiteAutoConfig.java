package com.chris.white.config;

import com.chris.white.WhiteJoinPoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 史偕成
 * @date 2023/10/04 11:49
 **/
@Configuration
@ConditionalOnClass(WhiteProperties.class)
@EnableConfigurationProperties(WhiteProperties.class)
public class WhiteAutoConfig {

    @Bean("whiteConfig")
    @ConditionalOnMissingBean
    public String whiteConfig(WhiteProperties whiteProperties) {
        return whiteProperties.getUsers();
    }

    @Bean
    @ConditionalOnMissingBean
    public WhiteJoinPoint point(){
        return new WhiteJoinPoint();
    }

}
