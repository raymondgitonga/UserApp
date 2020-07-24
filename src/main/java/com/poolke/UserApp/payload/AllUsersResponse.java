package com.poolke.UserApp.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllUsersResponse {
    private boolean success;
    private String message;
    private List<UserDto> data;
}
