package com.challenge.mule.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class IndicatorByYear {
    private Integer year;
    private BigDecimal Value;
}
