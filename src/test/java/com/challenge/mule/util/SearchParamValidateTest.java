package com.challenge.mule.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SearchParamValidateTest {

    @Test
    public void indicatorValidateTest() {

        Assertions.assertTrue(SearchParamsHelper.indicatorValidate("SP.POP.TOTL"), "Indicator would be correct");
        Assertions.assertFalse(SearchParamsHelper.indicatorValidate("SP.POP.TO5TL"), "Indicator would be incorrect");
    }

    @Test
    public void countryFormatAndLengthTest() {

        Assertions.assertTrue(SearchParamsHelper.countryValidate("South Africa"), "South Africa country name would be correct");
        Assertions.assertTrue(SearchParamsHelper.countryValidate("ZWE"), "ZWE Country code would be incorrect");

        Assertions.assertFalse(SearchParamsHelper.countryValidate("South & Africa"), "South & Africa would be correct");
        Assertions.assertFalse(SearchParamsHelper.countryValidate("ZWE1"), "ZWE1 would be correct");
    }
}
