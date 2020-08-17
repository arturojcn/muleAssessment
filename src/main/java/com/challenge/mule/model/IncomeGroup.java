package com.challenge.mule.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "income_groups")
public class IncomeGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    public IncomeGroup() {}
    public IncomeGroup(String name) {
        this.name = name;
    }
}
