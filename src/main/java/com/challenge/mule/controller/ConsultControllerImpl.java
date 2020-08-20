package com.challenge.mule.controller;

import com.challenge.mule.model.dto.IndicatorDTO;
import com.challenge.mule.model.dto.SearchIndicatorParams;
import com.challenge.mule.model.dto.SearchParams;
import com.challenge.mule.service.ConsultServiceInterface;
import com.challenge.mule.util.SearchParamsHelper;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ConsultControllerImpl implements ConsultControllerInterface {
    private ConsultServiceInterface consultService;

    public ConsultControllerImpl(ConsultServiceInterface consultService) {
        this.consultService = consultService;
    }

    @Override
    public IndicatorDTO getWorldPopulationGrowth(SearchParams params) {
        SearchParamsHelper.validate(params);
        return this.consultService.getWorldPopulationGrowth(params);
    }

    @Override
    public IndicatorDTO getIndicatorGrowth(SearchIndicatorParams params) {
        SearchParamsHelper.validate(params);
        return this.consultService.getIndicatorGrowth(params);
    }
}
