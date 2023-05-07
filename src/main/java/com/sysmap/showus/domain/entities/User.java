package com.sysmap.showus.domain.entities;

import com.sysmap.showus.domain.embedded.Followers;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Data
@Document
public class User {
    @Id
    private UUID id;
    private String name;
    private String email;
    private String password;
    private Date createdAt;
    private Followers followers;
    private String photoUri;

    public User(){}

    public User(String name, String email) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.createdAt = new Date(System.currentTimeMillis());
        this.followers = new Followers();
        this.photoUri = "";
    }
}
