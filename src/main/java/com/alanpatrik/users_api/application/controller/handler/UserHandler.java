package com.alanpatrik.users_api.application.controller.handler;

import com.alanpatrik.users_api.application.UserService;
import com.alanpatrik.users_api.domain.document.User;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@RequiredArgsConstructor
public class UserHandler {
    private final UserService service;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(service.findAll(), User.class);
    }

    public Mono<ServerResponse> find(ServerRequest request) {
        String id = request.pathVariable("id");

        return ServerResponse
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(service.find(id), User.class);
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<User> userMono = request.bodyToMono(User.class);

        return ServerResponse
                .status(CREATED)
                .contentType(APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(userMono.flatMap(service::save), User.class));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<User> userMono = request.bodyToMono(User.class);

        return ServerResponse
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(service.update(id, userMono), User.class);
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable("id");

        return ServerResponse
                .status(NO_CONTENT)
                .contentType(APPLICATION_JSON)
                .body(service.delete(id), User.class);
    }
}
