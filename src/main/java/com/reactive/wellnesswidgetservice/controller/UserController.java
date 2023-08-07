package com.reactive.wellnesswidgetservice.controller;

import com.reactive.wellnesswidgetservice.dto.AddUserRequest;
import com.reactive.wellnesswidgetservice.dto.UpdateUserRequest;
import com.reactive.wellnesswidgetservice.entity.User;
import com.reactive.wellnesswidgetservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/wellness-api/v1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping(value = "/user/add")
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Void> createUser(@Valid @RequestBody AddUserRequest addUserRequest){
        return userService.createUser(addUserRequest);
    }

    @PutMapping("/user/update")
    Mono<Void> updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest){
       return userService.updateUser(updateUserRequest);

    }
    @GetMapping("/user/{userName}")
    Mono<User> getUserByUserName( @Valid  @PathVariable String userName){
        return userService.getUserByUserName(userName);
    }

}
