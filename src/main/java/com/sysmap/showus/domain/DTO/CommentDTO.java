package com.sysmap.showus.domain.DTO;

import java.util.Date;
import java.util.UUID;

public class CommentDTO {
    private String text;
    private Date date;
    private AuthorDTO author;
    private Integer like;

    private UUID id;

    public CommentDTO(){};

    public CommentDTO(String text, AuthorDTO author) {
        this.id = UUID.randomUUID();
        this.text = text;
        this.date = new Date();
        this.author = author;
        this.like = 0;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public void setLike() {
        this.like = this.like + 1;
    }

    public UUID getId() {
        return id;
    }
}
