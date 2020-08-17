package com.challenge.mule.service.batching.meta_country;

import com.challenge.mule.model.DataControl;
import com.challenge.mule.model.csv.MetaCountry;
import com.challenge.mule.repository.DataControlRepository;
import com.challenge.mule.util.NameFileHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.PathResource;
import org.springframework.stereotype.Component;

@Component
public class MetaCountryItemReader implements ItemReader<FlatFileItemReader<MetaCountry>> {

    private final static Logger logger = LoggerFactory.getLogger(MetaCountryItemReader.class);
    private final static String PREFIX_FILE_NAME = "Metadata_Country_*.csv";

    @Autowired
    private DataControlRepository dataControlRepository;

    @Override
    public FlatFileItemReader<MetaCountry> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        DataControl data = this.dataControlRepository.getLatestActiveFolder();
        String absolutePath = NameFileHelper.getFileName(data.getFolderName(), PREFIX_FILE_NAME);
        data.setIsActive(false);
        this.dataControlRepository.save(data);

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