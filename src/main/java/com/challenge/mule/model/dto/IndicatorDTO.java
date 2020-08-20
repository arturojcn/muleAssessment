package com.challenge.mule.model.dto;


import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class IndicatorDTO {
    private String country;
    private String indicator;
    private List<IndicatorByYear> detail = new ArrayList<>();

    //TODO this is a list because and the next releases we can return a different growth average by different formulas
    // but now we only return a default rule
    private List<Growth> growths = new ArrayList<>();

}
