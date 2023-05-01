package com.sysmap.showus.domain;

import com.sysmap.showus.data.AuthorDTO;
import com.sysmap.showus.data.CommentDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Document
public class Post {
    @Id
    private UUID id;
    private Date createdAt;
    private String title;
    private String body;
    private AuthorDTO author;
    private Integer like;
    private List<CommentDTO> comments = new ArrayList<>();

    public Post() {
    }

    public Post(String title, String body, AuthorDTO author) {
        this.id = UUID.randomUUID();
        this.createdAt = new Date();
        this.title = title;
        this.body = body;
        this.author = author;
        this.like = 0;
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

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like + 1;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }
}
