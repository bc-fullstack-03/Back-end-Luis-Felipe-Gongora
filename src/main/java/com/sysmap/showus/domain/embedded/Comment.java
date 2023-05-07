package com.sysmap.showus.domain.embedded;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class Comment {
    private String comment;
    private Date date;
    private Author author;
    private List<Likes> like;
    private Integer likesCount;
    private UUID id;

    public Comment(){};

    public Comment(String comment, Author author) {
        this.id = UUID.randomUUID();
        this.comment = comment;
        this.date = new Date(System.currentTimeMillis());
        this.author = author;
        this.likesCount = 0;
        this.like = new ArrayList<>();
    }
}
