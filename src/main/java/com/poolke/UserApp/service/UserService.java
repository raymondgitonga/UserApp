package com.poolke.UserApp.service;

import com.poolke.UserApp.entity.User;
import com.poolke.UserApp.payload.UserDto;
import com.poolke.UserApp.payload.UserRequest;
import com.poolke.UserApp.payload.UserResponse;
import com.poolke.UserApp.repository.UserRepository;
import com.poolke.UserApp.shared.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    public Mono<UserResponse> registerUser(UserRequest userRequest) {
        User newUser = new User();
        BeanUtils.copyProperties(userRequest, newUser);
        if (userRequest.getPassword() != null) {
            newUser.setPassword(utils.encryptPassword(userRequest.getPassword()));
        }
        newUser.setUserId(utils.generateUserId());

        UserResponse userResponse = new UserResponse();

        return userRepository.findByEmail(userRequest.getEmail())
                .flatMap(user -> Mono.just(new UserResponse(false, "user exists", null))
                )
                .switchIfEmpty(
                        userRepository.save(newUser.setAsNew())
                                .map(savedUser -> {
                                    userResponse.setSuccess(true);
                                    userResponse.setMessage("success");
                                    userResponse.setUserData(new UserDto(savedUser.getUserId(), savedUser.getName(),
                                            savedUser.getEmail(), savedUser.getPhoneNumber()));
                                    return userResponse;
                                })
                );
    }

    public Mono<Object> loginUser(UserRequest userRequest) {
        UserResponse userResponse = new UserResponse();

        return userRepository.findByEmail(userRequest.getEmail())
                .map(user -> {
                    if (utils.checkPassword(userRequest.getPassword())) {
                        userResponse.setSuccess(true);
                        userResponse.setMessage("success");
                        userResponse.setUserData(new UserDto(user.getUserId(),
                                user.getName(), user.getEmail(), user.getPhoneNumber()));
                        return userResponse;
                    }
                    return Mono.just(new UserResponse(false, "wrong password", null));

                })
                .switchIfEmpty(Mono.just(new UserResponse(false, "wrong email", null)));
    }

    public Mono<UserResponse> updateUserDetails(UserRequest userRequest) {
        return userRepository.updateUserDetails(userRequest.getPhoneNumber(), userRequest.getEmail(), userRequest.getUserId())
                .flatMap(result -> Mono.just(new UserResponse(true, "success", null)));
    }

    public Mono<UserResponse> updatePassword(UserRequest userRequest) {
        String encryptedPassword = utils.encryptPassword(userRequest.getPassword());
        return userRepository.updatePassword(encryptedPassword, userRequest.getUserId())
                .flatMap(result -> Mono.just(new UserResponse(true, "success", null)));
    }

    public Mono<UserResponse> getUserById(UserRequest userRequest) {
        UserResponse userResponse = new UserResponse();
        return userRepository.findById(userRequest.getUserId())
                .map(userDetails ->{
                    userResponse.setSuccess(true);
                    userResponse.setMessage("success");
                    userResponse.setUserData(new UserDto(userDetails.getUserId(),
                            userDetails.getName(), userDetails.getEmail(), userDetails.getPhoneNumber()));
                    return userResponse;
                })
                .switchIfEmpty(Mono.just(new UserResponse(false, "fail", null)));
    }

    public Mono<UserResponse> getUserByEmail(UserRequest userRequest) {
        UserResponse userResponse = new UserResponse();
        return userRepository.findByEmail(userRequest.getEmail())
                .map(userDetails ->{
                    userResponse.setSuccess(true);
                    userResponse.setMessage("success");
                    userResponse.setUserData(new UserDto(userDetails.getUserId(),
                            userDetails.getName(), userDetails.getEmail(), userDetails.getPhoneNumber()));
                    return userResponse;
                })
                .switchIfEmpty(Mono.just(new UserResponse(false, "fail", null)));
    }
}
