package com.challenge.mule.service.batching.meta_Indicator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.separator.RecordSeparatorPolicy;

public class MetaIndicatorSeparatorPolicy implements RecordSeparatorPolicy {
    Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    public boolean isEndOfRecord(String s) {
        return s.split("\",\"").length == 4;
    }

    @Override
    public String postProcess(String s) {
        return s;
    }

    @Override
    public String preProcess(String s) {
        return s;
    }
}
