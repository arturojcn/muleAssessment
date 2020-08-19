package com.challenge.mule.service.batching.data_indicator;

import com.challenge.mule.config.WorldBankProperties;
import com.challenge.mule.model.csv.IndicatorDetailData;
import com.challenge.mule.util.FileHelper;
import org.springframework.batch.item.*;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.stereotype.Component;

@Component
public class IndicatorDetailItemReader implements ItemReader<FlatFileItemReader<IndicatorDetailData>> {
    @Autowired
    private WorldBankProperties worldBankProperties;

    @Override
    public FlatFileItemReader<IndicatorDetailData> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        String absolutePath = this.worldBankProperties.getPathSaveData()+this.worldBankProperties.getPrefixIndicatorFile();
        if (!FileHelper.isPathExist(absolutePath)) {
            FileHelper.mkdir(this.worldBankProperties.getResourceWorldBankFolder());
            String origin = new ClassPathResource(this.worldBankProperties.getResourceWorldBankFolder()+this.worldBankProperties.getPrefixIndicatorFile()).getPath();
            FileHelper.copy(origin, absolutePath);
        }
        return new FlatFileItemReaderBuilder<IndicatorDetailData>()
                .name("IndicatorDetailItemReader")
                .linesToSkip(4)
                .resource(new PathResource(absolutePath))
                .delimited()
                .names(new String[] {"countryName", "countryCode", "indicatorName", "indicatorCode",
                        "year0", "year1", "year2", "year3", "year4", "year5", "year6", "year7", "year8", "year9"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<IndicatorDetailData>() {{
                    setTargetType(IndicatorDetailData.class);
                }})
                .lineMapper(new IndicatorDetailLineMapper())
                .build();
    }
}
