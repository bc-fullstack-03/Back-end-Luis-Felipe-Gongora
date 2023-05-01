package com.sysmap.showus.services.post;

import com.sysmap.showus.domain.Post;
import com.sysmap.showus.domain.User;

import java.util.List;
import java.util.UUID;

public interface IPostService {
    List<Post> findAll();
    Post findById(UUID id);
    void delete(UUID userId, UUID postId);
    Post createPost(PostRequest request, UUID userId);
    Post updatePost(UUID userId, UUID postId, PostRequest request);
}
