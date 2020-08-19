package com.challenge.mule.model.csv;

import lombok.Data;

@Data
public class MetaCountry {
    private String countryCode;
    private String region;
    private String incomeGroup;
    private String specialNote;
    private String countryName;
    private String none;

    public MetaCountry() {}
    private MetaCountry(String countryCode, String region, String incomeGroup, String specialNote, String countryName) {
        this.countryCode = countryCode;
        this.region = region;
        this.incomeGroup = incomeGroup;
        this.specialNote = specialNote;
        this.countryName = countryName;
    }

    public static class MetaCountryBuilder {
        private String countryCode;
        private String region;
        private String incomeGroup;
        private String specialNote;
        private String countryName;

        public MetaCountryBuilder setCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public MetaCountryBuilder setRegion(String region) {
            this.region = region;
            return this;
        }

        public MetaCountryBuilder setIncomeGroup(String incomeGroup) {
            this.incomeGroup = incomeGroup;
            return this;
        }

        public MetaCountryBuilder setSpecialNote(String specialNote) {
            this.specialNote = specialNote;
            return this;
        }

        public MetaCountryBuilder setCountryName(String countryName) {
            this.countryName = countryName;
            return this;
        }

        public MetaCountry createMetaCountry() {
            return new MetaCountry(countryCode, region, incomeGroup, specialNote, countryName);
        }
    }
}
