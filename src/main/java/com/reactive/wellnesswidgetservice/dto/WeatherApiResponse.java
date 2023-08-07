package com.reactive.wellnesswidgetservice.dto;

import lombok.Data;

@Data
public class WeatherApiResponse {
        private double latitude;
        private double longitude;
        private double generationTimeMs;
        private int utcOffsetSeconds;
        private String timezone;
        private String timezoneAbbreviation;
        private double elevation;

}
