package com.sysmap.showus.data;

import com.sysmap.showus.domain.User;

import java.util.UUID;

public class AuthorDTO {
    private UUID id;
    private String name;

    public AuthorDTO(){}

    public AuthorDTO(User user){
        id = user.getId();
        name = user.getName();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
