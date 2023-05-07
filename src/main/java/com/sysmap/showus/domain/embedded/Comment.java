package com.sysmap.showus.domain.embedded;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class Comment {
    private String text;
    private Date date;
    private Author author;
    private List<Author> like;
    private UUID id;

    public Comment(){};

    public Comment(String text, Author author) {
        this.id = UUID.randomUUID();
        this.text = text;
        this.date = new Date(System.currentTimeMillis());
        this.author = author;
    }
}
