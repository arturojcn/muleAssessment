package com.challenge.mule.util;

import com.challenge.mule.exception.BadRequestException;
import com.challenge.mule.model.dto.SearchIndicatorParams;
import com.challenge.mule.model.dto.SearchParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchParamsHelper {
    private final static Logger logger = LoggerFactory.getLogger(SearchParamsHelper.class);

    public static void validate(SearchParams params) {
        if (params instanceof SearchIndicatorParams && params.getIndicatorsCode() != null) {
            for (String indicator: params.getIndicatorsCode()) {
                if (!indicatorValidate(indicator)) {
                    throw new BadRequestException(indicator+"Indicator missing match format");
                }
            }
        }

        if (params.getCountries() != null && !params.getCountries().isEmpty() && params.getNumberResults() != null) {
            throw new BadRequestException("You can't choose countries and number of results");
        }

        if (params.getCountries() != null && !params.getCountries().isEmpty()) {
            for (String country: params.getCountries()) {
                if (!countryValidate(country)) {
                    throw new BadRequestException(country+"country missing match format or too long");
                }
            }
        }
    }


    public static boolean indicatorValidate(String str){
        return (!str.equals(""))
                && (str != null)
                && (str.matches("^[a-zA-Z.]*$"));
    }
    public static boolean countryValidate(String str){
        return (!str.equals(""))
                && (str != null)
                && (str.matches("^[a-zA-Z ]{3,20}$"));
    }
}
