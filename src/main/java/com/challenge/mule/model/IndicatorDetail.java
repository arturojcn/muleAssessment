package com.challenge.mule.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "indicator_details", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"year", "country_id", "indicator_id"})
})
public class IndicatorDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer year;
    private BigDecimal value;

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn(name = "indicator_id", nullable = false)
    private Indicator indicator;

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    public IndicatorDetail() {}
    private IndicatorDetail(Integer year, BigDecimal value, Indicator indicator, Country country) {
        this.year = year;
        this.value = value;
        this.indicator = indicator;
        this.country = country;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        IndicatorDetail guest = (IndicatorDetail) obj;
        return (country.getCode().equals(guest.getCountry().getCode())
                && indicator.getId().equals(guest.getIndicator().getId()));
    }

    public static class IndicatorDetailBuilder {
        private Integer year;
        private BigDecimal value;
        private Indicator indicator;
        private Country country;

        public IndicatorDetailBuilder setYear(Integer year) {
            this.year = year;
            return this;
        }

        public IndicatorDetailBuilder setValue(BigDecimal value) {
            this.value = value;
            return this;
        }

        public IndicatorDetailBuilder setIndicator(Indicator indicator) {
            this.indicator = indicator;
            return this;
        }

        public IndicatorDetailBuilder setCountry(Country country) {
            this.country = country;
            return this;
        }

        public IndicatorDetail createIndicatorDetail() {
            return new IndicatorDetail(year, value, indicator, country);
        }
    }
}
