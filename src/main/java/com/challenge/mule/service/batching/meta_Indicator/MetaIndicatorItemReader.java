package com.challenge.mule.service.batching.meta_Indicator;

import com.challenge.mule.config.WorldBankProperties;
import com.challenge.mule.model.csv.MetaIndicator;
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
public class MetaIndicatorItemReader implements ItemReader<FlatFileItemReader<MetaIndicator>> {
    @Autowired
    private WorldBankProperties worldBankProperties;

    @Override
    public FlatFileItemReader<MetaIndicator> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        String absolutePath = this.worldBankProperties.getPathSaveData()+this.worldBankProperties.getPrefixMetaIndicatorFile();
        if (!FileHelper.isPathExist(absolutePath)) {
            FileHelper.mkdir(this.worldBankProperties.getResourceWorldBankFolder());
            String origin = new ClassPathResource(this.worldBankProperties.getResourceWorldBankFolder()+this.worldBankProperties.getPrefixMetaIndicatorFile()).getPath();
            FileHelper.copy(origin, absolutePath);
        }
        return new FlatFileItemReaderBuilder<MetaIndicator>()
                .name("MetaIndicatorReader")
                .linesToSkip(1)
                .resource(new PathResource(absolutePath))
                .recordSeparatorPolicy(new MetaIndicatorSeparatorPolicy())
                .delimited()
                .names(new String[]{"indicatorCode", "indicatorName", "note", "organization", "none"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<MetaIndicator>(){{
                    setTargetType(MetaIndicator.class);
                }})
                .build();
    }
}
