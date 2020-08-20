package com.challenge.mule.service.batching.data_indicator;

import com.challenge.mule.model.Country;
import com.challenge.mule.model.Indicator;
import com.challenge.mule.model.IndicatorDetail;
import com.challenge.mule.model.IndicatorDetailControlTmp;
import com.challenge.mule.model.csv.IndicatorDetailData;
import com.challenge.mule.repository.CountryRepository;
import com.challenge.mule.repository.IndicatorDetailControlTmpRepository;
import com.challenge.mule.repository.IndicatorDetailRepository;
import com.challenge.mule.repository.IndicatorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class IndicatorDetailItemWriter implements ItemWriter<IndicatorDetailData> {
    private final static Logger logger = LoggerFactory.getLogger(IndicatorDetailItemWriter.class);
    private final static String COUNTRY_NAME = "Country Name";
    private final static String NAME_REPLACE = "\"";
    private final static String YEAR_REPLACE = "\",";

    private final static int INDEX = 0;
    private IndicatorDetailControlTmp controlTmp;

    @Autowired
    IndicatorDetailControlTmpRepository controlRepository;

    @Autowired
    private IndicatorDetailRepository indicatorDetailRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private IndicatorRepository indicatorRepository;

    private Optional<Country> countrySaved;


    boolean isTitleFirst = false;

    @Override
    public void write(List<? extends IndicatorDetailData> list) throws Exception {
        logger.info("indicator detail");

        IndicatorDetailData indicator = list.get(INDEX);
        if (indicator.getCountryName().replace(NAME_REPLACE, "").equalsIgnoreCase(COUNTRY_NAME)){
            logger.debug("This is the title line: {}", indicator.toString());
            isTitleFirst = true;

            controlTmp = new IndicatorDetailControlTmp(Integer.parseInt(indicator.getYear0()), this.getEndYear(indicator));
            this.controlRepository.saveAndFlush(controlTmp);
            logger.debug("control table save with id {} startYear {} and endYear {}", controlTmp.getId(), controlTmp.getStart(), controlTmp.getEnd());
        } else  if (controlTmp == null) {
            controlTmp = this.controlRepository.findAll().get(INDEX);
        }

        list.forEach(indicatorDetail -> {
            if (!isTitleFirst) {
                try {
                    List<IndicatorDetail> indicatorDetailsSaved = this.indicatorDetailRepository.findAllByUnique(
                            indicatorDetail.getCountryCode(),
                            indicatorDetail.getIndicatorCode(),
                            controlTmp.getStart(),
                            controlTmp.getEnd());
                    if (indicatorDetailsSaved.isEmpty()) {
                        this.indicatorDetailRepository.saveAll(this.getIndicatorsToSave(indicatorDetail));
                    } else {
                        //TODO I'm not proud of this, but at least I think with this I can avoid to call to db multiple times,
                        // ej: Indicator * n-years * N-indicators and catch all constraintExceptions and it try to save again or maybe update it!
                        // or first get a record one by one and validate it, to save later. Probable (sure) there are several best way to do this.
                        // I need to say that in environment work I was discuss this with someone else or maybe I was delegate this work to DB
                        // by store procedure although it's harder to debugging
                        List<IndicatorDetail> indicatorsToSave= new ArrayList<>();
                        List<IndicatorDetail> newIndicatorDetails = this.getIndicatorsToSave(indicatorDetail);

                        newIndicatorDetails.forEach(newIterable -> {
                            boolean found = false;
                            for (IndicatorDetail savedIterable: indicatorDetailsSaved) {
                                if (savedIterable.getYear().equals(newIterable.getYear())) {
                                    if (savedIterable.getValue().intValue() != newIterable.getValue().intValue()) {
                                        this.indicatorDetailRepository.updateValueById(newIterable.getValue(), savedIterable.getId());
                                    }
                                    indicatorDetailsSaved.remove(savedIterable);
                                    found=true;
                                    break;
                                }
                            }
                            if (!found) { this.indicatorDetailRepository.save(newIterable); }
                        });
                    }
                } catch (DataIntegrityViolationException e) {
                    logger.error("indicator already exist", e);
                } catch (Exception e) {
                    logger.error("something was wrong trying to save indicator  {}", indicatorDetail.toString());
                    logger.error("stackTracer error: ", e);
                }
            } else {
                logger.debug("skip this element because it's the title line {}", indicatorDetail.toString());
                isTitleFirst=false;
            }
        });
    }

    //TODO change this ternary operator by a Predicate implementation
    private int getEndYear(IndicatorDetailData indicator) {
        int endYear = Integer.parseInt(indicator.getYear0().replace(YEAR_REPLACE, ""));
        endYear = indicator.getYear1() != null ? Integer.parseInt(indicator.getYear1().replace(YEAR_REPLACE, "")) : endYear;
        endYear = indicator.getYear2() != null ? Integer.parseInt(indicator.getYear2().replace(YEAR_REPLACE, "")) : endYear;
        endYear = indicator.getYear3() != null ? Integer.parseInt(indicator.getYear3().replace(YEAR_REPLACE, "")) : endYear;
        endYear = indicator.getYear4() != null ? Integer.parseInt(indicator.getYear4().replace(YEAR_REPLACE, "")) : endYear;
        endYear = indicator.getYear5() != null ? Integer.parseInt(indicator.getYear5().replace(YEAR_REPLACE, "")) : endYear;
        endYear = indicator.getYear6() != null ? Integer.parseInt(indicator.getYear6().replace(YEAR_REPLACE, "")) : endYear;
        endYear = indicator.getYear7() != null ? Integer.parseInt(indicator.getYear7().replace(YEAR_REPLACE, "")) : endYear;
        endYear = indicator.getYear8() != null ? Integer.parseInt(indicator.getYear8().replace(YEAR_REPLACE, "")) : endYear;

        return indicator.getYear9() != null ? Integer.parseInt(indicator.getYear9().replace(YEAR_REPLACE, "")) : endYear;

    }

    private List<IndicatorDetail> getIndicatorsToSave(IndicatorDetailData indicator) {
        int startYear = controlTmp.getStart();
        int endYear = controlTmp.getEnd();
        if (countrySaved == null || !countrySaved.isPresent() || !countrySaved.get().getCode().equals(indicator.getCountryCode())) {
            countrySaved = this.countryRepository.findById(indicator.getCountryCode());
        }

        List<IndicatorDetail> indicatorsRS = new ArrayList<>();

        indicatorsRS.add(this.parseSingleIndicator(indicator, startYear++, indicator.getYear0(), countrySaved.get()));
        if (startYear > endYear) return indicatorsRS;

        indicatorsRS.add(this.parseSingleIndicator(indicator, startYear++, indicator.getYear1(), countrySaved.get()));
        if (startYear > endYear) return indicatorsRS;

        indicatorsRS.add(this.parseSingleIndicator(indicator, startYear++, indicator.getYear2(), countrySaved.get()));
        if (startYear > endYear) return indicatorsRS;

        indicatorsRS.add(this.parseSingleIndicator(indicator, startYear++, indicator.getYear3(), countrySaved.get()));
        if (startYear > endYear) return indicatorsRS;

        indicatorsRS.add(this.parseSingleIndicator(indicator, startYear++, indicator.getYear4(), countrySaved.get()));
        if (startYear > endYear) return indicatorsRS;

        indicatorsRS.add(this.parseSingleIndicator(indicator, startYear++, indicator.getYear5(), countrySaved.get()));
        if (startYear > endYear) return indicatorsRS;

        indicatorsRS.add(this.parseSingleIndicator(indicator, startYear++, indicator.getYear6(), countrySaved.get()));
        if (startYear > endYear) return indicatorsRS;

        indicatorsRS.add(this.parseSingleIndicator(indicator, startYear++, indicator.getYear7(), countrySaved.get()));
        if (startYear > endYear) return indicatorsRS;

        indicatorsRS.add(this.parseSingleIndicator(indicator, startYear++, indicator.getYear8(), countrySaved.get()));
        if (startYear > endYear) return indicatorsRS;

        indicatorsRS.add(this.parseSingleIndicator(indicator, startYear, indicator.getYear9(), countrySaved.get()));

        return indicatorsRS;

    }

    private IndicatorDetail parseSingleIndicator(IndicatorDetailData indicatorDetailData, int year, String value, Country country) {
        value = value == null || value.replace(YEAR_REPLACE,"").isEmpty() ? "0" : value.replace(YEAR_REPLACE,"");
        return new IndicatorDetail.IndicatorDetailBuilder()
                .setYear(year)
                .setValue(new BigDecimal(value))
                .setCountry(country)
                .setIndicator(new Indicator(indicatorDetailData.getIndicatorCode()))
                .createIndicatorDetail();
    }
}
