package com.challenge.mule.service;

import com.challenge.mule.model.dto.IndicatorDTO;
import com.challenge.mule.model.dto.SearchIndicatorParams;
import com.challenge.mule.model.dto.SearchParams;
import com.challenge.mule.repository.IndicatorDetailRepository;
import org.springframework.stereotype.Service;

@Service
public class ConsultServiceImpl implements ConsultServiceInterface {

    private IndicatorDetailRepository indicatorDetailRepository;

    public ConsultServiceImpl(IndicatorDetailRepository indicatorDetailRepository) {
        this.indicatorDetailRepository = indicatorDetailRepository;
    }

    @Override
    public IndicatorDTO getWorldPopulationGrowth(SearchParams params) {
        return new IndicatorDTO();
    }

    @Override
    public IndicatorDTO getIndicatorGrowth(SearchIndicatorParams params) {
        return new IndicatorDTO();
    }
}
