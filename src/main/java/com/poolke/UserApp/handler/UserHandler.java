package com.poolke.UserApp.handler;

import com.poolke.UserApp.payload.UserRequest;
import com.poolke.UserApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {
    @Autowired
    UserService userService;

    public Mono<ServerResponse> registerUser(ServerRequest serverRequest) {
        Mono<UserRequest> newUser = serverRequest.bodyToMono(UserRequest.class);

        return newUser.flatMap(userRequest ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(userService.registerUser(userRequest), UserRequest.class)
        );
    }

    public Mono<ServerResponse> loginUser(ServerRequest serverRequest) {
        Mono<UserRequest> user = serverRequest.bodyToMono(UserRequest.class);

        return user.flatMap(userRequest ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(userService.loginUser(userRequest), UserRequest.class)
        );
    }

    public Mono<ServerResponse> updateUserDetails(ServerRequest serverRequest) {
        Mono<UserRequest> user = serverRequest.bodyToMono(UserRequest.class);

        return user.flatMap(userRequest ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(userService.updateUserDetails(userRequest), UserRequest.class)
        );
    }

    public Mono<ServerResponse> updatePassword(ServerRequest serverRequest) {
        Mono<UserRequest> user = serverRequest.bodyToMono(UserRequest.class);

        return user.flatMap(userRequest ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(userService.updatePassword(userRequest), UserRequest.class)
        );
    }

    public Mono<ServerResponse> getUserByEmail(ServerRequest serverRequest) {
        Mono<UserRequest> user = serverRequest.bodyToMono(UserRequest.class);

        return user.flatMap(userRequest ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(userService.getUserByEmail(userRequest), UserRequest.class)
        );
    }

    public Mono<ServerResponse> getUserById(ServerRequest serverRequest) {
        Mono<UserRequest> user = serverRequest.bodyToMono(UserRequest.class);

        return user.flatMap(userRequest ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(userService.getUserById(userRequest), UserRequest.class)
                        .switchIfEmpty(ServerResponse.notFound().build())
        );
    }

    public Mono<ServerResponse> getAllUsers(ServerRequest serverRequest) {
        Mono<UserRequest> user = serverRequest.bodyToMono(UserRequest.class);

        return user.flatMap(userRequest ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(userService.getAllUsers(), UserRequest.class)
                        .switchIfEmpty(ServerResponse.notFound().build())
        );
    }

}
