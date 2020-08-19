package com.challenge.mule.service.batching.meta_country;

import com.challenge.mule.model.csv.MetaCountry;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class MetaCountryItemProcessor implements ItemProcessor<MetaCountry, MetaCountry> {

    @Override
    public MetaCountry process(final MetaCountry metaCountry) throws Exception {
        return metaCountry;
    }
}