package com.sysmap.showus.services.post.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PostRequest {
    private UUID id;
    private String title;
    private String description;
    private String photoUri;

    public PostRequest(){}

    public PostRequest(String title, String description){
        this.title = title;
        this.description = description;
        this.photoUri = "";
    }
}
