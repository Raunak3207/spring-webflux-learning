package com.reactive.wellnesswidgetservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CovidData {
    private long updated;
    private String country;
    private CountryInfo countryInfo;
    private long cases;
    private long todayCases;
    private long deaths;
    private long todayDeaths;
    private long recovered;
    private long todayRecovered;
    private long active;
    private long critical;
    private double casesPerOneMillion;
    private double deathsPerOneMillion;
    private long tests;
    private double testsPerOneMillion;
    private long population;
    private String continent;
    private long oneCasePerPeople;
    private long oneDeathPerPeople;
    private long oneTestPerPeople;
    private double activePerOneMillion;
    private double recoveredPerOneMillion;
    private double criticalPerOneMillion;


    @Data
    @ToString
    public static class CountryInfo {
        private int _id;
        private String iso2;
        private String iso3;
        private double lat;
        private double lon;
        private String flag;


    }
}

