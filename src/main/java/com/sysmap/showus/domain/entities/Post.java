package com.sysmap.showus.domain.entities;

import com.sysmap.showus.domain.embedded.Author;
import com.sysmap.showus.domain.embedded.Comment;
import com.sysmap.showus.domain.embedded.Likes;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Document
public class Post {
    @Id
    private UUID id;
    private Date createdAt;
    private String title;
    private String photoUri;
    private String description;
    private Author author;
    private List<Likes> likes = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
    private Integer likesCount;

    public Post() {
    }

    public Post(String title, String description, Author author) {
        this.id = UUID.randomUUID();
        this.createdAt = new Date(System.currentTimeMillis());
        this.title = title;
        this.photoUri = "";
        this.description = description;
        this.author = author;
        this.likesCount = 0;
    }
}
