package com.challenge.mule.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "control_tmp")
public class IndicatorDetailControlTmp {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int start;
    private int end;

    public IndicatorDetailControlTmp() {}
    public IndicatorDetailControlTmp(int start, int end) {
        this.start = start;
        this.end = end;
    }
}
