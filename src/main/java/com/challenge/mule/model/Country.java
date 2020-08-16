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

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn(name = "region_id")
    private Region region;

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn(name = "income_group_id")
    private IncomeGroup incomeGroup;

    public Country() {}
    public Country(String code, String name, Region region, IncomeGroup incomeGroup) {
        this.code = code;
        this.name = name;
        this.region = region;
        this.incomeGroup = incomeGroup;
    }
}
