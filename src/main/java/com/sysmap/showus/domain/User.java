package com.sysmap.showus.domain;

import com.sysmap.showus.domain.DTO.FollowersDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Document
public class User {
    @Id
    private UUID id;
    private String name;
    private String email;
    private String password;
    private Date createdAt;
    private FollowersDTO following;

    public User(){
    }

    public User(String name, String email, String password) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = new Date();
        this.following = new FollowersDTO();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public FollowersDTO getFollowers() {
        return following;
    }

    public void setFollowers(FollowersDTO followers) {
        this.following = followers;
    }
}
