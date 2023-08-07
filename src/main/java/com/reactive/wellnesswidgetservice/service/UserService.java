package com.reactive.wellnesswidgetservice.service;

import com.reactive.wellnesswidgetservice.dto.AddUserRequest;
import com.reactive.wellnesswidgetservice.dto.UpdateUserRequest;
import com.reactive.wellnesswidgetservice.entity.User;
import com.reactive.wellnesswidgetservice.exceptions.UserNotFoundException;
import com.reactive.wellnesswidgetservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    UserService(UserRepository userRepository){
        this.mapper = new ModelMapper();
        this.userRepository = userRepository;
    }

    public Mono<Void> createUser(AddUserRequest addUserRequest) {
        return userRepository.existsByUserName(addUserRequest.getUserName())
                .flatMap(userExists -> {
                    if (userExists) {
                        return Mono.error(new UserNotFoundException("User already exists with the given userName"));
                    } else {
                        User user = mapper.map(addUserRequest, User.class);
                        log.info("User created: {}", user.toString());
                        return userRepository.save(user).then().log();
                    }
                });

    }

    public Mono<Void> updateUser(UpdateUserRequest updateUserRequest) {
     return   userRepository.findByUserName(updateUserRequest.getUserName())
                .flatMap((user)->{
                  user.setCity(updateUserRequest.getCity());
                  user.setCountry(updateUserRequest.getCountry());
                  return userRepository.save(user);
                }).switchIfEmpty(Mono.error(new UserNotFoundException("User Not Found")))
             .then();
    }

    public Mono<User> getUserByUserName(String userName) {
        return userRepository.findByUserName(userName)
                .switchIfEmpty(Mono.error(new UserNotFoundException("user not found")));
    }
}
