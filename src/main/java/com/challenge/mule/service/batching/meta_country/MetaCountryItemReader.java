package com.challenge.mule.service.batching.meta_country;

import com.challenge.mule.config.WorldBankProperties;
import com.challenge.mule.model.csv.MetaCountry;
import com.challenge.mule.repository.DataControlRepository;
import com.challenge.mule.util.FileHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.*;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.stereotype.Component;

@Component
public class MetaCountryItemReader implements ItemReader<FlatFileItemReader<MetaCountry>> {
    private final static Logger logger = LoggerFactory.getLogger(MetaCountryItemReader.class);

    @Autowired
    private DataControlRepository dataControlRepository;
    @Autowired
    private WorldBankProperties worldBankProperties;

    @Override
    public FlatFileItemReader<MetaCountry> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        String absolutePath = this.worldBankProperties.getPathSaveData()+this.worldBankProperties.getPrefixMetaCountryFile();
        if (!FileHelper.isPathExist(absolutePath)) {
            FileHelper.mkdir(this.worldBankProperties.getResourceWorldBankFolder());
            String origin = new ClassPathResource(this.worldBankProperties.getResourceWorldBankFolder()+this.worldBankProperties.getPrefixMetaCountryFile()).getPath();
            FileHelper.copy(origin, absolutePath);
        }
        return new FlatFileItemReaderBuilder<MetaCountry>()
                .name("MetaCountryItemReader")
                .linesToSkip(1)
                .resource(new PathResource(absolutePath))
                .recordSeparatorPolicy(new MetaCountrySeparatorPolicy())
                .delimited()
                .names(new String[]{"countryCode", "region", "incomeGroup", "specialNote", "countryName", "none"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<MetaCountry>(){{
                    setTargetType(MetaCountry.class);
                }})
                .build();
    }
}