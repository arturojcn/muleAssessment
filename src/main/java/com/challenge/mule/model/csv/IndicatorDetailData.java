package com.challenge.mule.model.csv;

import lombok.Data;

@Data
public class IndicatorDetailData {
    private String countryName;
    private String countryCode;
    private String indicatorName;
    private String indicatorCode;
    private String year0;
    private String year1;
    private String year2;
    private String year3;
    private String year4;
    private String year5;
    private String year6;
    private String year7;
    private String year8;
    private String year9;
    private String none;

    public IndicatorDetailData() {}
    public IndicatorDetailData(String countryName, String countryCode, String indicatorName, String indicatorCode) {
        this.countryName = countryName;
        this.countryCode = countryCode;
        this.indicatorName = indicatorName;
        this.indicatorCode = indicatorCode;
    }
}
