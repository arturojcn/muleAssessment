package com.challenge.mule.controller;

import com.challenge.mule.model.dto.IndicatorDTO;
import com.challenge.mule.model.dto.SearchIndicatorParams;
import com.challenge.mule.model.dto.SearchParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ConsultImpl implements ConsultInterface {
    private final static Logger logger = LoggerFactory.getLogger(ConsultImpl.class);

    @Override
    public IndicatorDTO getWorldPopulationGrow(SearchParams params) {
        return new IndicatorDTO();
    }

    @Override
    public IndicatorDTO getIndicatorGrowth(SearchIndicatorParams params) {
        return new IndicatorDTO();
    }
}
