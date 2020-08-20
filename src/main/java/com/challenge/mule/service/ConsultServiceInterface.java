package com.challenge.mule.service;

import com.challenge.mule.model.dto.IndicatorDTO;
import com.challenge.mule.model.dto.SearchIndicatorParams;
import com.challenge.mule.model.dto.SearchParams;

import java.util.List;

public interface ConsultServiceInterface {

    List<IndicatorDTO> getWorldPopulationGrowth(SearchParams params);
    List<IndicatorDTO> getIndicatorGrowth(SearchIndicatorParams params);
}
