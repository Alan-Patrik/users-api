package com.alanpatrik.users_api.application.controller.router;

import com.alanpatrik.users_api.application.controller.handler.UserHandler;

import com.alanpatrik.users_api.domain.document.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@CrossOrigin("*")
@Configuration
public class UserRouter {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/users/",
                    produces = {APPLICATION_JSON_VALUE},
                    method = GET,
                    beanMethod = "findAll",
                    operation = @Operation(
                            operationId = "findAll",
                            description = "API to list all registered users",
                            summary = "API to list all registered users",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "SUCCESSFUL OPERATION",
                                            content = {
                                                    @Content(schema = @Schema(
                                                            implementation = User.class
                                                    ))
                                            }
                                    )
                            }
                    )
            ),

            @RouterOperation(
                    path = "/api/v1/users/{id}",
                    produces = {APPLICATION_JSON_VALUE},
                    method = GET,
                    beanMethod = "find",
                    operation = @Operation(
                            operationId = "find",
                            description = "API to return a specific user",
                            summary = "API to return a specific user",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "SUCCESSFUL OPERATION",
                                            content = {
                                                    @Content(schema = @Schema(
                                                            implementation = User.class
                                                    ))
                                            }
                                    ),

                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "NOT FOUND"
                                    )
                            },
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "User id to retrieve")
                            }
                    )
            ),

            @RouterOperation(
                    path = "/api/v1/users/",
                    produces = {APPLICATION_JSON_VALUE},
                    method = POST,
                    beanMethod = "save",
                    operation = @Operation(
                            operationId = "save",
                            description = "API to create a user in the database",
                            summary = "API to create a user in the database",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "SUCCESSFUL OPERATION",
                                            content = {
                                                    @Content(schema = @Schema(
                                                            implementation = User.class
                                                    ))
                                            }
                                    ),

                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "BAD REQUEST"
                                    )
                            },
                            requestBody = @RequestBody(
                                    content = {
                                            @Content(schema = @Schema(
                                                    implementation = User.class
                                            ))
                                    }
                            )
                    )
            ),

            @RouterOperation(
                    path = "/api/v1/users/{id}",
                    produces = {APPLICATION_JSON_VALUE},
                    method = PUT,
                    beanMethod = "update",
                    operation = @Operation(
                            operationId = "update",
                            description = "API to update a user's details",
                            summary = "API to update a user's details",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "SUCCESSFUL OPERATION",
                                            content = {
                                                    @Content(schema = @Schema(
                                                            implementation = User.class
                                                    ))
                                            }
                                    ),

                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "BAD REQUEST"
                                    ),

                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "BAD REQUEST"
                                    )
                            },

                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "User id to retrieve")
                            },

                            requestBody = @RequestBody(
                                    content = {
                                            @Content(schema = @Schema(
                                                    implementation = User.class
                                            ))
                                    }
                            )
                    )
            ),

            @RouterOperation(
                    path = "/api/v1/users/{id}",
                    produces = {APPLICATION_JSON_VALUE},
                    method = DELETE,
                    beanMethod = "delete",
                    operation = @Operation(
                            operationId = "delete",
                            description = "API to delete a user from the database",
                            summary = "API to delete a user from the database",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "204",
                                            description = "NO CONTENT"
                                    ),

                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "BAD REQUEST"
                                    ),

                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "BAD REQUEST"
                                    )
                            },

                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "User id to retrieve")
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> router(UserHandler handler) {
        return route()
                .path(
                        "/api/v1/users",
                        builder -> builder.nest(
                                accept(APPLICATION_JSON),
                                httpMethods -> httpMethods
                                        .GET("", handler::findAll)
                                        .GET("/{id}", handler::find)
                                        .POST("", handler::save)
                                        .PUT("/{id}", handler::update)
                                        .DELETE("/{id}", handler::delete)
                        )
                )
                .build();
    }
}
