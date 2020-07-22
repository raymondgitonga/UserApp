package com.poolke.UserApp.repository;

import com.poolke.UserApp.entity.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, String> {

    @Query("SELECT * FROM user WHERE email =:email")
    Mono<User> findByEmail(String email);

    @Query("UPDATE user SET phone_number =:phone, email =:email, phone_verified = 1 WHERE user_id =:userId")
    Mono<User> updateUserDetails(String phone, String email, String userId);

    @Query("UPDATE user SET password =:password WHERE user_id =:userId")
    Mono<User> updatePassword(String password, String userId);
}
