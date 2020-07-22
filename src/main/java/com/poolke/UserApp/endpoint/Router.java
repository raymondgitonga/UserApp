package com.poolke.UserApp.endpoint;

import com.poolke.UserApp.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.poolke.UserApp.endpoint.Endpoints.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class Router {
    @Bean
    public RouterFunction<ServerResponse> usersRoute(UserHandler userHandler) {
        return RouterFunctions
                .route(POST(REGISTER_USER_V1).and(accept(MediaType.APPLICATION_JSON)),
                        userHandler::registerUser)
                .andRoute(POST(LOGIN_USER_V1).and(accept(MediaType.APPLICATION_JSON)),
                        userHandler::loginUser)
                .andRoute(POST(UPDATE_USER_DETAILS_V1).and(accept(MediaType.APPLICATION_JSON)),
                        userHandler::updateUserDetails)
                .andRoute(POST(UPDATE_USER_PASSWORD_V1).and(accept(MediaType.APPLICATION_JSON)),
                        userHandler::updatePassword)
                .andRoute(GET(GET_USER_BY_EMAIL_V1).and(accept(MediaType.APPLICATION_JSON)),
                        userHandler::getUserByEmail)
                .andRoute(GET(GET_USER_DETAILS).and(accept(MediaType.APPLICATION_JSON)),
                        userHandler::getUserById);
    }
}
