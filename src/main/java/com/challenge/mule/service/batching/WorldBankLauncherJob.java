package com.challenge.mule.service.batching;

import com.challenge.mule.worldbank.WorldBankClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class WorldBankLauncherJob {
    private final static Logger logger = LoggerFactory.getLogger(WorldBankLauncherJob.class);

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job dataWorldBankJob;

    @Autowired
    private WorldBankClient worldBankClient;

    @Scheduled(fixedDelay = 10000, cron = "0 0 1 00 01 * ?")
    public void populate() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, IOException {
        this.worldBankClient.getWorldBankIndicatorsCSV();
        this.loadData();
    }

    public BatchStatus loadData() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        logger.info("loading data from CSV");
        Map<String, JobParameter> maps = new HashMap<>();
        maps.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters parameters = new JobParameters(maps);
        JobExecution jobExecution = jobLauncher.run(dataWorldBankJob, parameters);

        logger.info("JobExecution: " + jobExecution.getStatus());
        logger.info("Batch is Running...");
        while (jobExecution.isRunning()) {
            logger.info("processing data...");
        }

        return jobExecution.getStatus();
    }
}
