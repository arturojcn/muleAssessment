package com.challenge.mule.service.batching.data_indicator;

import com.challenge.mule.model.csv.IndicatorDetailData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.LineMapper;

import java.util.Arrays;
import java.util.List;

public class IndicatorDetailLineMapper implements LineMapper<IndicatorDetailData> {

    private final static Logger logger = LoggerFactory.getLogger(IndicatorDetailLineMapper.class);
    private final static String DELIMITER = "\",\"";
    @Override
    public IndicatorDetailData mapLine(String s, int i) throws Exception {
        List<String> line = Arrays.asList(s.split(DELIMITER));
        int limitField = Math.min(line.size(), 15);
        int index = 4;
        IndicatorDetailData indicator = new IndicatorDetailData(line.get(0), line.get(1), line.get(2), line.get(3));
        try {
            //TODO this was the best idea that i got to made the reader file a little flexibility
            // with this i can read from 1 to 10 years simultaneously
            indicator.setYear0(line.get(index++));
            indicator.setYear1(index < limitField?line.get(index++):null);
            indicator.setYear2(index < limitField?line.get(index++):null);
            indicator.setYear3(index < limitField?line.get(index++):null);
            indicator.setYear4(index < limitField?line.get(index++):null);
            indicator.setYear5(index < limitField?line.get(index++):null);
            indicator.setYear6(index < limitField?line.get(index++):null);
            indicator.setYear7(index < limitField?line.get(index++):null);
            indicator.setYear8(index < limitField?line.get(index++):null);
            indicator.setYear9(index < limitField?line.get(index):null);
        } catch (Exception e) {
            logger.error("Unable to parse line number <<{}>> with line <<{}>>.", i, s);
            logger.error("stackTracer error.", e);
        }
        return indicator;
    }
}