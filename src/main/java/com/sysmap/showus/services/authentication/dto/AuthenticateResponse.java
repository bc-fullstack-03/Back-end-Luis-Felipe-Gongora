package com.sysmap.showus.services.authentication.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class AuthenticateResponse {
    private UUID userId;
    private String token;
}
