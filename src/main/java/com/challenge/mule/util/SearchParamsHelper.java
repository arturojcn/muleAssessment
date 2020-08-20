package com.challenge.mule.util;

import com.challenge.mule.config.WorldBankProperties;
import com.challenge.mule.exception.BadRequestException;
import com.challenge.mule.model.dto.SearchIndicatorParams;
import com.challenge.mule.model.dto.SearchParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class SearchParamsHelper {
    private final static Logger logger = LoggerFactory.getLogger(SearchParamsHelper.class);
    private final static String GROWTH_POPULATION = "SP.POP.TOTL";
    private final static String GDP = "NY.GDP.MKTP.PP.CD";

    public static void validate(SearchParams params) {
        if (params instanceof SearchIndicatorParams) {
            //TODO for now only make search by default FIX this
           /* for (String indicator: params.getIndicatorsCode()) {
                if (!indicatorValidate(indicator)) {
                    throw new BadRequestException(indicator+"Indicator missing match format");
                }
            }*/
            params.setIndicatorsCode(Arrays.asList(GROWTH_POPULATION, GDP));
            //}
        } else {
            params.setIndicatorsCode(Arrays.asList(GDP));
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
