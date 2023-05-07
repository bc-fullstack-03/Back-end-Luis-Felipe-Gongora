package com.sysmap.showus.services.authentication.dto;

import lombok.Data;

@Data
public class AuthenticateRequest {
    private String email;
    private String password;
}
