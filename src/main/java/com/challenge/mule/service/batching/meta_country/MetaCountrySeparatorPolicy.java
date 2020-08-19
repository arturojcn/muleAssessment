package com.challenge.mule.service.batching.meta_country;

import org.springframework.batch.item.file.separator.RecordSeparatorPolicy;

public class MetaCountrySeparatorPolicy implements RecordSeparatorPolicy {
    private final static String DELIMITER = "\",\"";
    @Override
    public boolean isEndOfRecord(String s) {
        return s.split(DELIMITER).length == 5;
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
