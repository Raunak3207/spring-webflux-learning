package com.reactive.wellnesswidgetservice.service;

import com.reactive.wellnesswidgetservice.dto.CovidData;
import com.reactive.wellnesswidgetservice.dto.WeatherApiResponse;
import com.reactive.wellnesswidgetservice.dto.WellNessDataResponse;
import com.reactive.wellnesswidgetservice.dto.WellnessDataRequest;
import com.reactive.wellnesswidgetservice.exceptions.UserNotFoundException;
import com.reactive.wellnesswidgetservice.repository.UserRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@Slf4j
public class WeatherAndSiknessService {

    private final UserRepository userRepository;
    private final WebApiCallService apiCallService;

    public WeatherAndSiknessService(UserRepository userRepository, WebApiCallService apiCallService) {
        this.userRepository = userRepository;
        this.apiCallService = apiCallService;
    }

    @CircuitBreaker(name="mockService",fallbackMethod = "fallback")
    public Mono<WellNessDataResponse> getWellnessData(WellnessDataRequest dataRequest) {
    return userRepository.findByUserName(dataRequest.getUserName()).flatMap(user -> {
        String country  = Optional.ofNullable(dataRequest.getCountry()).orElse(user.getCountry());
        Mono<WeatherApiResponse> weatherApiResponseMono;
        Mono<CovidData> covidDataMono ;
       return Mono.zip(apiCallService.getWeatherForeCast(dataRequest.getLatitude(),dataRequest.getLongitude()),apiCallService.getCovidData(country),(weatherApiResponse,covidData)->{
            WellNessDataResponse wellNessDataResponse =  new WellNessDataResponse();
            wellNessDataResponse.setUserName(user.getUserName());
            wellNessDataResponse.setCity(user.getCity());
            wellNessDataResponse.setCountry(user.getCountry());
            wellNessDataResponse.setWeatherForecast(weatherApiResponse);
            wellNessDataResponse.setCovidStats(covidData);
            return wellNessDataResponse;
        });
    }).switchIfEmpty(Mono.error(new UserNotFoundException("User doesn't exists")));
    }

    public Mono<Void> fallback(Throwable ex) {
        log.info("fallback method executed");
        return Mono.error(new RuntimeException("Something went Wrong"+ex.getMessage()));
    }
}
