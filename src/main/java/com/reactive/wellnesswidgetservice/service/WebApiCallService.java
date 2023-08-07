package com.reactive.wellnesswidgetservice.service;

import com.reactive.wellnesswidgetservice.dto.CovidData;
import com.reactive.wellnesswidgetservice.dto.WeatherApiResponse;
import com.reactive.wellnesswidgetservice.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class WebApiCallService {
    private final WebClient webClient;


    public WebApiCallService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<WeatherApiResponse> getWeatherForeCast(double lat ,double lon ){
        var baseUrl = "https://api.open-meteo.com/v1";
        var requestUrl = UriComponentsBuilder
                .fromHttpUrl(baseUrl)
                .path("/forecast")
                .queryParam("latitude", lat)
                .queryParam("longitude", lon)
                .queryParam("currentweather", true)
                .toUriString();
        return webClient.get()
                .uri(requestUrl)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(WeatherApiResponse.class).log();
    }

    public Mono<CovidData> getCovidData(String country){
        String baseUrl = "https://corona.lmao.ninja/v2/countries/"+country;
        String requestUrl = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .toUriString();
        return webClient.get()
                .uri(requestUrl)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,clientResponse -> {
                    if(clientResponse.statusCode() == HttpStatus.NOT_FOUND)
                      return   Mono.error(new ResourceNotFoundException("Provided country invalid"));
                  return  Mono.error(new ResourceNotFoundException("Provided country invalid"));
                })
                .bodyToMono(CovidData.class)
                .log();

    }
}
