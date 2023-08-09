package com.reactive.wellnesswidgetservice.controller;

import com.reactive.wellnesswidgetservice.dto.WellNessDataResponse;
import com.reactive.wellnesswidgetservice.dto.WellnessDataRequest;
import com.reactive.wellnesswidgetservice.service.WeatherAndSiknessService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/wellness-api/v1")
public class WeatherAndSiknessDataController {

    private final  WeatherAndSiknessService weatherAndSiknessService;

    public WeatherAndSiknessDataController(WeatherAndSiknessService weatherAndSiknessService) {
        this.weatherAndSiknessService = weatherAndSiknessService;
    }

    @PostMapping("/wellnessData")
    Mono<WellNessDataResponse> getWellnessData(@Valid  @RequestBody WellnessDataRequest dataRequest){
        return weatherAndSiknessService.getWellnessData(dataRequest);
    }
}
