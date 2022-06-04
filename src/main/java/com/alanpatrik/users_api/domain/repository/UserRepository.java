package com.alanpatrik.users_api.domain.repository;

import com.alanpatrik.users_api.domain.document.User;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, String> {
    Mono<User> findByUsername(String username);
}
