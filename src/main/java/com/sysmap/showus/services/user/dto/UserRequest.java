package com.sysmap.showus.services.user.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserRequest {
    private String name;
    private String email;
    private String password;
}
