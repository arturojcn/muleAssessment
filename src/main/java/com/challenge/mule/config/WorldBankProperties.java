package com.challenge.mule.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("world-bank")
public class WorldBankProperties {

    private String host;
    private String indicators;
    private String pathSaveData;
    private String years;
    private String downloadFormat;
    private String sourceParam;
}
