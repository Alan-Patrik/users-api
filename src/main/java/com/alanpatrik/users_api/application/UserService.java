package com.alanpatrik.users_api.application;

import com.alanpatrik.users_api.domain.document.User;
import com.alanpatrik.users_api.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public Mono<User> save(User data) {
        return repository
                .findByUsername(data.getUsername())
                .doOnNext(isUserRegistered -> {
                    if (isUserRegistered != null)
                        throw new IllegalArgumentException("User with username already registered in the database " + data.getUsername());
                })
                .switchIfEmpty(Mono.defer(() -> repository.save(data)))
                .cast(User.class);
    }

    public Flux<User> findAll() {
        return repository.findAll();
    }

    public Mono<User> find(String id) {
        return repository
                .findById(id)
                .flatMap(
                        isUserNotPresent -> Mono.error(new IllegalArgumentException("No user was found with id " + id))
                );
    }

    public Mono<Void> delete(String id) {
        return find(id).flatMap(repository::delete);
    }

    public Mono<User> update(String id, Mono<User> data) {
        return find(id)
                .flatMap(user -> data.doOnNext(u -> u.setId(id)))
                .flatMap(repository::save);
    }
}
