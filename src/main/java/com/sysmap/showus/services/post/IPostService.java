package com.sysmap.showus.services.post;

import com.sysmap.showus.domain.Post;
import com.sysmap.showus.domain.User;

import java.util.List;
import java.util.UUID;

public interface IPostService {
//    List<User> findAll();
//    User findById(UUID id);
//    void delete(UUID id);
    Post createPost(PostRequest request, UUID userId);
//    User updateUser(UUID id, PostRequest request);

}
