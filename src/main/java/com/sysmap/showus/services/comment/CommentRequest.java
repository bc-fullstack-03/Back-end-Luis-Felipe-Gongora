package com.sysmap.showus.services.comment;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class CommentRequest {
    private UUID userId;
    private String text;
    private Date createdAt;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
