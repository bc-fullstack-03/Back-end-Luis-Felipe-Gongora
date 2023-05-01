package com.sysmap.showus.domain;

import com.sysmap.showus.data.AuthorDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Document
public class Post {
    @Id
    private UUID id;
    private Date createdAt;
    private String title;
    private String body;
    private AuthorDTO author;

    public Post() {
    }

    public Post(String title, String body, AuthorDTO author) {
        this.id = UUID.randomUUID();
        this.createdAt = new Date();
        this.title = title;
        this.body = body;
        this.author = author;
    }

    public UUID getId() {
        return id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public AuthorDTO getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDTO author) {
        this.author = author;
    }
}
