package com.challenge.mule.controller;

import com.challenge.mule.model.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(value = "/api/v1", consumes = { MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE })
@Api(value = "ConsultData", tags = { "Consult" })
public interface ConsultControllerInterface {

    @GetMapping("/worldPopulation")
    @ApiOperation(value = "This endpoint get the top 20 countries by population growth from 2012 to 2019 Year-to-Year", tags = {"Consult"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = IndicatorDTO.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = ExceptionResponseDTO.class),
            @ApiResponse(code = 404, message = "Not found, resource wasn't found", response = ExceptionResponseDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ExceptionResponseDTO.class),
            @ApiResponse(code = 503, message = "Time out", response = ExceptionResponseDTO.class)
    })
    List<IndicatorDTO> getWorldPopulationGrowth(SearchGrowthParams params);


    @GetMapping("/indicators")
    @ApiOperation(value = "This endpoint get by default the top 5 countries by GDP growth Indicator " +
            "from the top 20 countries by population growth from 2012 to 2019 Year-to-Year", tags = {"Consult"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = IndicatorDTO.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = ExceptionResponseDTO.class),
            @ApiResponse(code = 404, message = "Not found, resource wasn't found", response = ExceptionResponseDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ExceptionResponseDTO.class),
            @ApiResponse(code = 503, message = "Time out", response = ExceptionResponseDTO.class)
    })
    List<IndicatorDTO> getIndicatorGrowth(SearchIndicatorParams params);
}
