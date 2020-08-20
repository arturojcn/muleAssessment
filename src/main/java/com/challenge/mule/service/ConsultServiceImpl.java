package com.challenge.mule.service;

import com.challenge.mule.model.Indicator;
import com.challenge.mule.model.IndicatorDetail;
import com.challenge.mule.model.dto.*;
import com.challenge.mule.repository.IndicatorDetailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ConsultServiceImpl implements ConsultServiceInterface {
    private final static Logger logger = LoggerFactory.getLogger(ConsultServiceImpl.class);

    private IndicatorDetailRepository indicatorDetailRepository;

    public ConsultServiceImpl(IndicatorDetailRepository indicatorDetailRepository) {
        this.indicatorDetailRepository = indicatorDetailRepository;
    }


    @Override
    public List<IndicatorDTO> getWorldPopulationGrowth(SearchParams params) {
        List<IndicatorDTO> response = new ArrayList<>();
        //TODO order and get 20 countries default or numbersCountries response
        this.processPopulationGrowth(null, null, 0, new BigDecimal(0), response, null, params);
        return response;
    }

    @Override
    public List<IndicatorDTO> getIndicatorGrowth(SearchIndicatorParams params) {
        List<IndicatorDTO> response = new ArrayList<>();
        //TODO only to response somethings
        this.processPopulationGrowth(null, null, 0, new BigDecimal(0), response, null, params);
        return response;
    }

    //TODO improve this with params got in the request and change hardcode by properties and handler possibles IndexOutOfBoundsException
    private void processPopulationGrowth(List<IndicatorDetail> detailList, IndicatorDetail singleDetail,
                                                       int index, BigDecimal sum, List<IndicatorDTO> response,
                                                        IndicatorDTO indicatorDTO, SearchParams params) {
        if (singleDetail==null) {

            detailList = this.indicatorDetailRepository.findAllByRangeDateAndIndicators(params.getIndicatorsCode(), 2012, 2019);

            singleDetail = detailList.get(index);
            indicatorDTO = IndicatorDTO.builder()
                                            .country(singleDetail.getCountry().getName())
                                            .indicator(singleDetail.getIndicator().getId())
                                            .build();
            response.add(indicatorDTO);
        }

        //validate it are the same country and indicator
        int before = index == 0 ? index : index-1;
        if (singleDetail.equals(detailList.get(before))){
            sum = singleDetail.getValue().subtract(detailList.get(before).getValue());
            if (indicatorDTO.getDetail() != null) {
                indicatorDTO.getDetail().add(new IndicatorByYear(singleDetail.getYear(), singleDetail.getValue()));
            } else {
                indicatorDTO.setDetail(Stream.of(new IndicatorByYear(singleDetail.getYear(), singleDetail.getValue()))
                        .collect(Collectors.toList()));
            }
        } else {
            //save the average
            indicatorDTO.setGrowths(Stream.of(new Growth(sum))
                    .collect(Collectors.toList()));

            //create new object to new country indicator
            indicatorDTO = IndicatorDTO.builder()
                    .country(singleDetail.getCountry().getName())
                    .indicator(singleDetail.getIndicator().getId())
                    .build();

            //initializer a details
            indicatorDTO.setDetail(Stream.of(new IndicatorByYear(singleDetail.getYear(), singleDetail.getValue()))
                    .collect(Collectors.toList()));
            response.add(indicatorDTO);
            sum = new BigDecimal(0);
        }
        if(++index >= detailList.size() ) return;
        processPopulationGrowth( detailList, detailList.get(index), index, sum, response, indicatorDTO, null);
    }
}
