package com.challenge.mule.controller;

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
    @ApiOperation(value = "get world population", tags = {"Consult"})
    @ApiResponses(value = @ApiResponse(code = 200, message = "Success", response = String.class))
    String getWorldPopulation();


    @GetMapping("/gdp_ppp")
    @ApiOperation(value = "get Gross Domestic Product and Purchasing-Power-Parity", tags = {"Consult"})
    @ApiResponses(value = @ApiResponse(code = 200, message = "Success", response = String.class))
    String getGdpPpp();
}
