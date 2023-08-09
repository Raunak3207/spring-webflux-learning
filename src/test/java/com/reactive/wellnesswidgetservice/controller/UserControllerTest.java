package com.reactive.wellnesswidgetservice.controller;

import com.reactive.wellnesswidgetservice.dto.AddUserRequest;
import com.reactive.wellnesswidgetservice.dto.UpdateUserRequest;
import com.reactive.wellnesswidgetservice.entity.User;
import com.reactive.wellnesswidgetservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
//@WebFluxTest(controllers = UserController.class)
@SpringBootTest
@AutoConfigureWebTestClient
@Slf4j
class UserControllerTest {

    @Autowired
    WebTestClient webTestClient;
    private  final String baseUrl = "/wellness-api/v1";

    @Autowired
    UserRepository userRepository;



    @Test
    void createUserwithValidData() {
        AddUserRequest requestBody  =  new AddUserRequest();
        requestBody.setUserName("test@gmail.com");
        requestBody.setCountry("india");
        requestBody.setCity("surat");
        webTestClient.post()
                .uri(baseUrl+"/user/add")
                .bodyValue(requestBody)
                .exchange()
                .expectStatus()
                .isCreated();
    }
    @Test
    void createUserWithInValidData() {
        AddUserRequest requestBody  =  new AddUserRequest();
        requestBody.setUserName(null);
        requestBody.setCountry("india");
        requestBody.setCity("surat");
        webTestClient.post()
                .uri(baseUrl+"/user/add")
                .bodyValue(requestBody)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void createAlreadyExistingUser() {
        AddUserRequest requestBody  =  new AddUserRequest();
        requestBody.setUserName("Raunak@gmail.com");
        requestBody.setCountry("india");
        requestBody.setCity("surat");
        webTestClient.post()
                .uri(baseUrl+"/user/add")
                .bodyValue(requestBody)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void updateUserWithValidData() {
        UpdateUserRequest updateUserRequest  =  new UpdateUserRequest();
        updateUserRequest.setUserName("Raunak@gmail.com");
        updateUserRequest.setCity("NY");
        updateUserRequest.setCountry("canada");
        webTestClient.put()
                .uri(baseUrl+"/user/update")
                .bodyValue(updateUserRequest)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    void updateUserWithInValidData() {
        UpdateUserRequest updateUserRequest  =  new UpdateUserRequest();
        updateUserRequest.setUserName("Raunak@gmail.com");
        updateUserRequest.setCity(null);
        updateUserRequest.setCountry(null);
        webTestClient.put()
                .uri(baseUrl+"/user/update")
                .bodyValue(updateUserRequest)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void updateNonExistingUserWithValidData() {
        UpdateUserRequest updateUserRequest  =  new UpdateUserRequest();
        updateUserRequest.setUserName("test1@gmail.com");
        updateUserRequest.setCity("NY");
        updateUserRequest.setCountry("canada");
        webTestClient.put()
                .uri(baseUrl+"/user/update")
                .bodyValue(updateUserRequest)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void getUserByUserName() {
        String username = "Raunak@gmail.com";
        webTestClient.get()
                .uri(baseUrl+"/user/"+username)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(User.class)
                .consumeWith(userEntityExchangeResult -> assertEquals(username, Objects.requireNonNull(userEntityExchangeResult.getResponseBody()).getUserName()));
    }

    @Test
    void getUserByUserNameNonExistingUser() {
        String username = "test2@gmail.com";
        webTestClient.get()
                .uri(baseUrl+"/user/"+username)
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }

    @Test
    void getUserByUserInvalidData() {
        String username = null;
        webTestClient.get()
                .uri(baseUrl+"/user/")
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }
}