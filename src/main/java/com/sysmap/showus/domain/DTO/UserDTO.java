package com.sysmap.showus.domain.DTO;

import com.sysmap.showus.domain.User;

import java.util.*;

public class UserDTO {
    private UUID id;
    private String name;
    private String email;
    private FollowersDTO followers;
    private Date createdAt;

    public UserDTO(){
    };

    public UserDTO(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.id = user.getId();
        this.createdAt = user.getCreatedAt();
        this.followers = user.getFollowers();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public FollowersDTO getFollowers() {
        return followers;
    }

    public void setFollowers(FollowersDTO followers) {
        this.followers = followers;
    }
}
