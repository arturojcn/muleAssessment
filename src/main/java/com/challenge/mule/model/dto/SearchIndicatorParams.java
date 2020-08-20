package com.challenge.mule.model.dto;


import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Generated
public class SearchIndicatorParams extends SearchParams{
    @Getter
    @Setter
    private List<String> indicatorsCode;
}
