package com.chris.white.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 史偕成
 * @date 2023/10/04 11:47
 **/
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "com.chris.white")
public class WhiteProperties {
    /**
     * 用户集合
     */
    private String users;

}
