package com.challenge.mule.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsultImpl implements ConsultInterface {


    @Override
    public String getWorldPopulation() {
        return "The world population";
    }

    @Override
    public String getGdpPpp() {
        return "the gross domestic product and Purchasing power parity";
    }
}
