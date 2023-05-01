package com.sysmap.showus.services.post;

import lombok.Data;

import java.util.UUID;

@Data
public class PostRequest {
    private UUID id;
    private String title;
    private String body;

    public String getName() {
        return title;
    }

    public void setName(String name) {
        this.title = name;
    }

    public String getEmail() {
        return body;
    }

    public void setEmail(String email) {
        this.body = email;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
