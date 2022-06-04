package com.alanpatrik.users_api.modules.user;

import com.alanpatrik.users_api.modules.user.User;
import com.alanpatrik.users_api.modules.user.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public Flux<User> getAll() {
        return repository.findAll();
    }

    public Mono<User> getById(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Product with id %s not found", id)))
                ).cast(User.class);
    }

    private Mono<User> getByName(String username) {
        return repository.findByUsername(username)
                .switchIfEmpty(Mono.error(new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Product with username %s not found", username)))
                ).cast(User.class);
    }

    public Mono<User> create(User user) {
        return repository.findByUsername(user.getUsername())
                .flatMap(p -> Mono.error(new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        String.format("User with username already registered in the database", p.getUsername())))
                ).switchIfEmpty(Mono.defer(() -> repository.save(user))).cast(User.class);
    }

    public Mono<User> update(String id, Mono<User> data) {
        return getById(id)
                .flatMap(user -> data.doOnNext(u -> u.setId(id)))
                .flatMap(repository::save);
    }

    public Mono<Void> delete(String id) {
        return getById(id).flatMap(repository::delete);
    }
}
