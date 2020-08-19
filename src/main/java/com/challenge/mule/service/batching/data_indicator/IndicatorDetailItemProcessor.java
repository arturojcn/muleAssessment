package com.challenge.mule.service.batching.data_indicator;

import com.challenge.mule.model.csv.IndicatorDetailData;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class IndicatorDetailItemProcessor implements ItemProcessor<IndicatorDetailData, IndicatorDetailData> {
    @Override
    public IndicatorDetailData process(IndicatorDetailData indicatorDetailData) throws Exception {
        return indicatorDetailData;
    }
}
