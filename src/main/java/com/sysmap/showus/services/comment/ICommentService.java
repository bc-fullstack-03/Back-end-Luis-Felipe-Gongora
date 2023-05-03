package com.sysmap.showus.services.comment;

import com.sysmap.showus.domain.DTO.CommentDTO;
import com.sysmap.showus.domain.Post;

import java.util.List;
import java.util.UUID;

public interface ICommentService {
    List<CommentDTO> findAllCommentsFromPost(UUID postId);
    void delete(UUID postId, UUID commentId);
    Post createComment(CommentRequest request, UUID userId, UUID postId);
    Post updateComment(UUID postId, UUID commentId, CommentRequest request);

}
