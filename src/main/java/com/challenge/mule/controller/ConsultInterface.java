package com.challenge.mule.controller;

import com.challenge.mule.model.dto.IndicatorDTO;
import com.challenge.mule.model.dto.SearchIndicatorParams;
import com.challenge.mule.model.dto.SearchParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/api/v1", consumes = { MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE })
@Api(value = "ConsultData", tags = { "Consult" })
public interface ConsultInterface {

    @GetMapping("/worldPopulation")
    @ApiOperation(value = "This endpoint get the top 20 countries by population growth from 2012 to 2019 Year-to-Year", tags = {"Consult"})
    @ApiResponses(value = @ApiResponse(code = 200, message = "Success", response = String.class))
    IndicatorDTO getWorldPopulationGrow(SearchParams params);


    @GetMapping("/indicators")
    @ApiOperation(value = "This endpoint get by default the top 5 countries by GDP growth Indicator " +
            "from the top 20 countries by population growth from 2012 to 2019 Year-to-Year", tags = {"Consult"})
    @ApiResponses(value = @ApiResponse(code = 200, message = "Success", response = String.class))
    IndicatorDTO getIndicatorGrowth(SearchIndicatorParams params);
}
