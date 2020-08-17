package com.challenge.mule.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "countries")
public class Country {

    @Id
    @Column(name = "id")
    private String code;
    @Column(unique = true, nullable = false)
    private String name;
    private String specialNote;

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn(name = "region_id")
    private Region region;

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn(name = "income_group_id")
    private IncomeGroup incomeGroup;

    public Country() {}
    private Country(String code, String name, String specialNote, Region region, IncomeGroup incomeGroup) {
        this.code = code;
        this.name = name;
        this.specialNote = specialNote;
        this.region = region;
        this.incomeGroup = incomeGroup;
    }

    public static class CountryBuilder {
        private String code;
        private String name;
        private String specialNote;
        private Region region;
        private IncomeGroup incomeGroup;

        public CountryBuilder setCode(String code) {
            this.code = code;
            return this;
        }

        public CountryBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public CountryBuilder setSpecialNote(String specialNote) {
            this.specialNote = specialNote;
            return this;
        }

        public CountryBuilder setRegion(Region region) {
            this.region = region;
            return this;
        }

        public CountryBuilder setIncomeGroup(IncomeGroup incomeGroup) {
            this.incomeGroup = incomeGroup;
            return this;
        }

        public Country createCountry() {
            return new Country(code, name, specialNote, region, incomeGroup);
        }
    }

}
