package com.challenge.mule.controller;

import com.challenge.mule.model.dto.IndicatorDTO;
import com.challenge.mule.model.dto.SearchIndicatorParams;
import com.challenge.mule.model.dto.SearchParams;
import com.challenge.mule.service.ConsultServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ConsultControllerImpl implements ConsultControllerInterface {
    private ConsultServiceInterface consultService;

    public ConsultControllerImpl(ConsultServiceInterface consultService) {
        this.consultService = consultService;
    }

    @Override
    public IndicatorDTO getWorldPopulationGrowth(SearchParams params) {

        return this.consultService.getWorldPopulationGrowth(params);
    }

    @Override
    public IndicatorDTO getIndicatorGrowth(SearchIndicatorParams params) {
        return this.consultService.getIndicatorGrowth(params);
    }
}
