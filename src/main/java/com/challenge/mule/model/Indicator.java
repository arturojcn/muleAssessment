package com.challenge.mule.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "indicators")
public class Indicator {

    @Id
    private String id;
    private String name;
    @Column(name = "source_note")
    private String sourceNote;
    @Column(name = "source_organization")
    private String sourceOrganization;

    public Indicator() {}
    private Indicator(String id, String name, String sourceNote, String sourceOrganization) {
        this.id = id;
        this.name = name;
        this.sourceNote = sourceNote;
        this.sourceOrganization = sourceOrganization;
    }

    public static class IndicatorBuilder {
        private String id;
        private String name;
        private String sourceNote;
        private String sourceOrganization;

        public IndicatorBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public IndicatorBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public IndicatorBuilder setSourceNote(String sourceNote) {
            this.sourceNote = sourceNote;
            return this;
        }

        public IndicatorBuilder setSourceOrganization(String sourceOrganization) {
            this.sourceOrganization = sourceOrganization;
            return this;
        }

        public Indicator createIndicator() {
            return new Indicator(id, name, sourceNote, sourceOrganization);
        }
    }
}
