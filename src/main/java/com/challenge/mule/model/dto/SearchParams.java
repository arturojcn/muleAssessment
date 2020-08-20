package com.challenge.mule.model.dto;


import lombok.Data;

import java.util.List;

@Data
public class SearchParams {
    private List<String> countries;
    private Integer numberResults;
    private List<Integer> years;
    private List<String> indicatorsCode;

}
