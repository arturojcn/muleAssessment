package com.challenge.mule.config;

import com.challenge.mule.model.csv.IndicatorDetailData;
import com.challenge.mule.model.csv.MetaCountry;
import com.challenge.mule.model.csv.MetaIndicator;
import com.challenge.mule.service.batching.data_indicator.IndicatorDetailItemProcessor;
import com.challenge.mule.service.batching.data_indicator.IndicatorDetailItemReader;
import com.challenge.mule.service.batching.data_indicator.IndicatorDetailItemWriter;
import com.challenge.mule.service.batching.meta_Indicator.MetaIndicatorItemProcessor;
import com.challenge.mule.service.batching.meta_Indicator.MetaIndicatorItemReader;
import com.challenge.mule.service.batching.meta_Indicator.MetaIndicatorItemWriter;
import com.challenge.mule.service.batching.meta_country.MetaCountryJobNotificationListener;
import com.challenge.mule.service.batching.meta_country.MetaCountryItemReader;
import com.challenge.mule.service.batching.meta_country.MetaCountryItemProcessor;
import com.challenge.mule.service.batching.meta_country.MetaCountryItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    public JobBuilderFactory jobBuilderFactory;
    public StepBuilderFactory stepBuilderFactory;
    private MetaCountryItemReader metaCountryItemReader;
    private MetaCountryItemProcessor metaCountryItemProcessor;
    private MetaIndicatorItemReader metaIndicatorItemReader;
    private MetaIndicatorItemProcessor metaIndicatorItemProcessor;
    private IndicatorDetailItemProcessor indicatorDetailItemProcessor;
    private IndicatorDetailItemReader indicatorDetailItemReader;

    public BatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                              MetaCountryItemReader metaCountryItemReader,
                              MetaCountryItemProcessor metaCountryItemProcessor, MetaIndicatorItemReader metaIndicatorItemReader,
                              MetaIndicatorItemProcessor metaIndicatorItemProcessor, IndicatorDetailItemProcessor indicatorDetailItemProcessor,
                              IndicatorDetailItemReader indicatorDetailItemReader) {

        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.metaCountryItemReader = metaCountryItemReader;
        this.metaCountryItemProcessor = metaCountryItemProcessor;
        this.metaIndicatorItemReader = metaIndicatorItemReader;
        this.metaIndicatorItemProcessor = metaIndicatorItemProcessor;
        this.indicatorDetailItemProcessor = indicatorDetailItemProcessor;
        this.indicatorDetailItemReader = indicatorDetailItemReader;
    }

    @Bean
    public Job metaCountryJob(MetaCountryJobNotificationListener listener, Step metaCountryStep,
                              Step metaIndicatorStep, Step indicatorDetailStep) {
        return this.jobBuilderFactory.get("dataWorldBankJob")
                .incrementer(new RunIdIncrementer())
                .start(metaIndicatorStep)
                .next(metaCountryStep)
                .next(indicatorDetailStep)
                .build();
    }

    @Bean
    public Step metaCountryStep(MetaCountryItemWriter writer) throws Exception {
        return this.stepBuilderFactory.get("metaCountryStep")
                .<MetaCountry, MetaCountry> chunk(100)
                .reader(this.metaCountryItemReader.read())
                .processor(this.metaCountryItemProcessor)
                .writer(writer)
                .build();
    }

    @Bean
    public Step metaIndicatorStep(MetaIndicatorItemWriter metaIndicatorItemWriter) throws Exception {
        return this.stepBuilderFactory.get("metaIndicatorStep")
                .allowStartIfComplete(true)
                .<MetaIndicator, MetaIndicator> chunk(100)
                .chunk(100)
                .reader(this.metaIndicatorItemReader.read())
                .processor(this.metaIndicatorItemProcessor)
                .writer(metaIndicatorItemWriter)
                .build();
    }

    @Bean
    public Step indicatorDetailStep(IndicatorDetailItemWriter indicatorDetailItemWriter) throws Exception {
        return this.stepBuilderFactory.get("indicatorDetailStep")
                .allowStartIfComplete(true)
                .<IndicatorDetailData, IndicatorDetailData> chunk(100)
                .chunk(1000)
                .reader(this.indicatorDetailItemReader.read())
                .processor(this.indicatorDetailItemProcessor)
                .writer(indicatorDetailItemWriter)
                .build();
    }
}
