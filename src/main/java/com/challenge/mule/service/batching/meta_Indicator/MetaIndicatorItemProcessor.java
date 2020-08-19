package com.challenge.mule.service.batching.meta_Indicator;

import com.challenge.mule.model.csv.MetaIndicator;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class MetaIndicatorItemProcessor implements ItemProcessor<MetaIndicator, MetaIndicator> {

    @Override
    public MetaIndicator process(final MetaIndicator metaIndicator) throws Exception {
        return metaIndicator;
    }
}