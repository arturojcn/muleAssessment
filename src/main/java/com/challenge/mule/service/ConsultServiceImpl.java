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
        this.processPopulationGrowth(null, null, 0, new BigDecimal(0), response, null);
        return response;
    }

    @Override
    public List<IndicatorDTO> getIndicatorGrowth(SearchIndicatorParams params) {
        return new ArrayList<>();
    }

    private void processPopulationGrowth(List<IndicatorDetail> detailList, IndicatorDetail singleDetail,
                                                       int index, BigDecimal sum, List<IndicatorDTO> response, IndicatorDTO indicatorDTO) {
        if (singleDetail==null) {
            Indicator indicator = new Indicator.IndicatorBuilder().setId("SP.POP.TOTL").createIndicator();
            IndicatorDetail indicatorDetail = new IndicatorDetail.IndicatorDetailBuilder().setIndicator(indicator).createIndicatorDetail();

            detailList = this.indicatorDetailRepository.findAll(Example.of(indicatorDetail));

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
        processPopulationGrowth( detailList, detailList.get(index), index, sum, response, indicatorDTO);
    }
}
