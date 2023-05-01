package com.sysmap.showus.services.comment;

import com.sysmap.showus.domain.Post;

import java.util.List;
import java.util.UUID;

public interface ICommentService {
//    List<Post> findAll();
//    Post findById(UUID id);
//    void delete(UUID id);
    Post createComment(CommentRequest request, UUID userId, UUID postId);
//    User updateUser(UUID id, PostRequest request);

}
