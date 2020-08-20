package com.challenge.mule.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class IndicatorByYear {
    private Integer year;
    private BigDecimal Value;
}
