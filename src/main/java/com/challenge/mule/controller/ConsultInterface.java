package com.challenge.mule.controller;

import com.challenge.mule.model.dto.ExceptionResponseDTO;
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
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = String.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = ExceptionResponseDTO.class),
            @ApiResponse(code = 404, message = "Not found, resource wasn't found", response = ExceptionResponseDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ExceptionResponseDTO.class ),
            @ApiResponse(code = 503, message = "Time out", response = ExceptionResponseDTO.class )
    })
    String getWorldPopulation();


    @GetMapping("/gdp_ppp")
    @ApiOperation(value = "get Gross Domestic Product and Purchasing-Power-Parity", tags = {"Consult"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = String.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = ExceptionResponseDTO.class),
            @ApiResponse(code = 404, message = "Not found, resource wasn't found", response = ExceptionResponseDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ExceptionResponseDTO.class),
            @ApiResponse(code = 503, message = "Time out", response = ExceptionResponseDTO.class)
    })
    String getGdpPpp();
}
