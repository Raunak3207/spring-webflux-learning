package com.reactive.wellnesswidgetservice.controller;

import com.reactive.wellnesswidgetservice.dto.ErrorResponse;
import com.reactive.wellnesswidgetservice.dto.WellNessDataResponse;
import com.reactive.wellnesswidgetservice.dto.WellnessDataRequest;
import com.reactive.wellnesswidgetservice.entity.User;
import com.reactive.wellnesswidgetservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//@WebFluxTest(controllers = UserController.class)
@SpringBootTest
@AutoConfigureWebTestClient
@Slf4j
class WeatherAndSiknessDataControllerTest {

    @Autowired
    WebTestClient webTestClient;

    private  final String baseUrl = "/wellness-api/v1";

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setupData(){
        User user  =  new User();
        user.setUserName("user@gmail.com");
        user.setCountry("india");
        user.setCity("surat");
        userRepository.save(user);
        log.info("before each Executed");
    }

    @AfterEach
    void deletetestData(){
        userRepository.deleteByUserName("user@gmail.com");
        log.info("After each Executed");
    }


    @Test
    void getWellnessData() {
        WellnessDataRequest wellnessDataRequest  =  new WellnessDataRequest();
        wellnessDataRequest.setUserName("Raunak@gmail.com");
        wellnessDataRequest.setCountry("india");
        wellnessDataRequest.setLatitude(26.81);
        wellnessDataRequest.setLongitude(81.00);
        webTestClient.post()
                .uri(baseUrl+"/wellnessData")
                .bodyValue(wellnessDataRequest)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(WellNessDataResponse.class)
                .consumeWith(wellNessDataResponseEntityExchangeResult ->{
                    WellNessDataResponse response = wellNessDataResponseEntityExchangeResult.getResponseBody();
                    assert response != null;
                    assertTrue(response.getCovidStats()!=null && response.getWeatherForecast()!=null);
                });
    }

    @Test
    void getWellnessDataInvalidCountry() {
        WellnessDataRequest wellnessDataRequest  =  new WellnessDataRequest();
        wellnessDataRequest.setUserName("user@gmail.com");
        wellnessDataRequest.setCountry("asdf");
        wellnessDataRequest.setLatitude(26.81);
        wellnessDataRequest.setLongitude(81.00);
        webTestClient.post()
                .uri(baseUrl+"/wellnessData")
                .bodyValue(wellnessDataRequest)
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectBody(ErrorResponse.class)
                .consumeWith(errorResponseEntityExchangeResult -> log.info(errorResponseEntityExchangeResult.getResponseBody().getMessage()));
    }

    @Test
    void getWellnessDataNonExistingUser() {
        WellnessDataRequest wellnessDataRequest  =  new WellnessDataRequest();
        wellnessDataRequest.setUserName("test@gmail.com");
        wellnessDataRequest.setCountry("asdf");
        wellnessDataRequest.setLatitude(26.81);
        wellnessDataRequest.setLongitude(81.00);
        webTestClient.post()
                .uri(baseUrl+"/wellnessData")
                .bodyValue(wellnessDataRequest)
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectBody(ErrorResponse.class)
                .consumeWith(errorResponseEntityExchangeResult -> log.info(errorResponseEntityExchangeResult.getResponseBody().getMessage()));
    }


}