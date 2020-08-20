package com.challenge.mule.service;

import com.challenge.mule.model.dto.IndicatorDTO;
import com.challenge.mule.model.dto.SearchIndicatorParams;
import com.challenge.mule.model.dto.SearchParams;

public interface ConsultServiceInterface {

    IndicatorDTO getWorldPopulationGrowth(SearchParams params);
    IndicatorDTO getIndicatorGrowth(SearchIndicatorParams params);
}
