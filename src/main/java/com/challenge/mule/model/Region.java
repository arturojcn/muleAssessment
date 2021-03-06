package com.challenge.mule.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "regions")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    public Region() {}

    public Region(String name) {
        this.name = name;
    }
}
