package com.sysmap.showus.services.user.dto;

import com.sysmap.showus.domain.embedded.Followers;
import com.sysmap.showus.domain.entities.User;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class UserResponse {
    private UUID id;
    private String name;
    private String email;
    private Followers followers;
    private Date createdAt;
    private String photoUri;

    public UserResponse(){
    }

    public UserResponse(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.id = user.getId();
        this.createdAt = user.getCreatedAt();
        this.followers = user.getFollowers();
        this.photoUri = user.getPhotoUri();
    }
}
