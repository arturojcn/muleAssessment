package com.challenge.mule.service.batching.meta_Indicator;

import com.challenge.mule.model.Indicator;
import com.challenge.mule.model.csv.MetaIndicator;
import com.challenge.mule.repository.IndicatorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MetaIndicatorItemWriter implements ItemWriter<MetaIndicator>{

    private final static Logger logger = LoggerFactory.getLogger(MetaIndicatorItemWriter.class);

    @Autowired
    private IndicatorRepository indicatorRepository;

    @Override
    public void write(List<? extends MetaIndicator> list) throws Exception {
        list.forEach(l -> {
            try {
                if (l.getNote().length()>255) l.setNote(l.getNote().substring(0, 255));
                if (l.getOrganization().length()>255) l.setOrganization(l.getOrganization().substring(0, 255));
                this.indicatorRepository.saveAndFlush(new Indicator.IndicatorBuilder()
                        .setId(l.getIndicatorCode())
                        .setName(l.getIndicatorName())
                        .setSourceNote(l.getNote())
                        .setSourceOrganization(l.getOrganization())
                        .createIndicator());
            } catch (DataIntegrityViolationException e) {
                logger.error("indicator already exist", e);
            } catch (Exception e) {
                logger.error("something was wrong trying to save indicator " + l.getIndicatorName(), e);
            }
        });

    }

}
