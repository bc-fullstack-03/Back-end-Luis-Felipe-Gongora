package com.sysmap.showus.services.user.dto;

import com.sysmap.showus.domain.entities.User;
import lombok.Data;

import java.util.UUID;
@Data
public class FollowersResponse {
    private UUID id;
    private String name;
    private String photoUri;
    private String email;
    private Integer followersCount;
    private Integer followingCount;

    public FollowersResponse(){}

    public FollowersResponse(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.photoUri = user.getPhotoUri();
        this.email = user.getEmail();
        this.followersCount = user.getFollowers().getFollowersCount();
        this.followingCount = user.getFollowers().getFollowingCount();
    }
}
