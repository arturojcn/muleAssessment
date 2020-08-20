package com.challenge.mule.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Growth {
    private BigDecimal average;
    //TODO this two variable are hardcode because in the next release the idea is make this more dynamic
    // to calculate the growth average, so we can do this with a engine rules maybe or something like that,
    // and we can have all formulas in DB and choice formula by queryParam
    private String growthFormulaCode="SUM_AVG_YEAR";
    private String growthFormulaName="SUM average per year";

    public Growth(BigDecimal average) {
        this.average = average;
    }
}
