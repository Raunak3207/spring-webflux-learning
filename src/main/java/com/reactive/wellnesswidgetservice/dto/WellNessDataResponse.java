package com.reactive.wellnesswidgetservice.dto;

import lombok.Data;

@Data
public class WellNessDataResponse {
    String userName;
    String city;
    String country;
    WeatherApiResponse weatherForecast;
    CovidData covidStats;

}
