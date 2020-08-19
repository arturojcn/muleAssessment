package com.challenge.mule.service.batching.meta_country;

import com.challenge.mule.model.Country;
import com.challenge.mule.model.IncomeGroup;
import com.challenge.mule.model.Region;
import com.challenge.mule.model.csv.MetaCountry;
import com.challenge.mule.repository.CountryRepository;
import com.challenge.mule.repository.IncomeGroupRepository;
import com.challenge.mule.repository.RegionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MetaCountryItemWriter implements ItemWriter<MetaCountry> {

    private final static Logger logger = LoggerFactory.getLogger(MetaCountryItemWriter.class);

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private IncomeGroupRepository incomeGroupRepository;

    @Autowired
    private CountryRepository countryRepository;

    private Map<String, Region> regions = new HashMap<>();
    private Map<String, IncomeGroup> incomeGroups = new HashMap<>();

    @Override
    public void write(List<? extends MetaCountry> list) throws Exception {
        list.forEach(l -> {
            this.saveRegion(l.getRegion());
            this.saveIncomeGroup(l.getIncomeGroup());
            this.saveCountry(l);
        });

    }

    /**
     * This method save region if it isn't exist in DB but first it validate that the region isn't empty
     * and it isn't into regions flag variable to avoid call the DB multiple times
     * @param regionName to save
     */
    private void saveRegion(String regionName) {
        if (!regionName.isEmpty() && !this.regions.containsKey(regionName)) {
            Region regionSaved = this.regionRepository.findByName(regionName);
            if ( regionSaved == null) {
                Region regionToSave = new Region(regionName);
                this.regionRepository.saveAndFlush(regionToSave);
                this.regions.put(regionName, regionToSave);
            } else {
                this.regions.put(regionName, regionSaved);
            }
        }
    }

    /**
     * This method save the incomeGroup if it isn't exist in DB but first it validate that the group isn't empty
     * and it isn't into group flag variable to avoid call the DB multiple times
     * @param groupName to save
     */
    private void saveIncomeGroup(String groupName) {
        if (!groupName.isEmpty() && !this.incomeGroups.containsKey(groupName)) {
            IncomeGroup groupSaved = this.incomeGroupRepository.findByName(groupName);
            if ( groupSaved == null) {
                IncomeGroup groupToSave = new IncomeGroup(groupName);
                this.incomeGroupRepository.saveAndFlush(groupToSave);
                this.incomeGroups.put(groupName, groupToSave);
            } else {
                this.incomeGroups.put(groupName, groupSaved);
            }
        }
    }

    private void saveCountry(MetaCountry meta) {
        if (meta.getSpecialNote().length() > 255) {
            meta.setSpecialNote(meta.getSpecialNote().substring(0, 255));
        }

        Optional<Country> countrySaved = this.countryRepository.findById(meta.getCountryCode());

        if (countrySaved.isPresent()) {
            if (countrySaved.get().getIncomeGroup() != null
                && !countrySaved.get().getIncomeGroup().getName().equals(meta.getIncomeGroup())) {

                countrySaved.get().setIncomeGroup(this.incomeGroups.get(meta.getIncomeGroup()));
                countrySaved.get().setSpecialNote(meta.getSpecialNote());
                this.countryRepository.saveAndFlush(countrySaved.get());
            }
        } else {
            Country countryToSave = new Country.CountryBuilder()
                    .setCode(meta.getCountryCode())
                    .setName(meta.getCountryName())
                    .setSpecialNote(meta.getSpecialNote())
                    .setIncomeGroup(this.incomeGroups.get(meta.getIncomeGroup()))
                    .setRegion(this.regions.get(meta.getRegion()))
                    .createCountry();
            try {
                this.countryRepository.saveAndFlush(countryToSave);
            } catch (Exception e) {
                logger.error("Something was wrong with " + meta.getCountryCode(), e);
            }
        }
    }
}