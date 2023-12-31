package com.reactive.wellnesswidgetservice.repository;

import com.reactive.wellnesswidgetservice.entity.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User,String> {
    Mono<User> findByUserName(String userName);
    Mono<Boolean> existsByUserName(String userName);

    Mono<Void> deleteByUserName(String userName);
}
