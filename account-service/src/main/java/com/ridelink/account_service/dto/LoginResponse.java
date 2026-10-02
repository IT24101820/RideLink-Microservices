package com.ridelink.account_service.dto;

import com.ridelink.account_service.model.AccountStatus;
import com.ridelink.account_service.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {

    private Long id;
    private String name;
    private String email;
    private Role role;
    private AccountStatus status;
    private String token;
}