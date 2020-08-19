package com.challenge.mule.config;

import com.challenge.mule.service.batching.WorldBankLauncherJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DataInitializerConfiguration implements CommandLineRunner {

    private final static Logger logger = LoggerFactory.getLogger(DataInitializerConfiguration.class);

    @Autowired
    private WorldBankLauncherJob worldBankLauncherJob;

    @Override
    public void run(String... args) throws Exception {
        logger.info("I'm going to prepare data for application");
        this.worldBankLauncherJob.populate();
    }
}
