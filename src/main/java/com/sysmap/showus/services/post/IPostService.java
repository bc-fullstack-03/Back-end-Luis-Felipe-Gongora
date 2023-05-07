package com.sysmap.showus.services.post;

import com.sysmap.showus.domain.entities.Post;
import com.sysmap.showus.services.post.dto.CommentRequest;
import com.sysmap.showus.services.post.dto.PostRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface IPostService {
    Post createPost(PostRequest request);
    void uploadPhotoPost(MultipartFile photo, String postId);
    List<Post> findAllPosts();
    Post getPost(String postId);
    Post updatePost(String postId, PostRequest request);
    Post likePost(String postId);
    Post unlikePost(String postId);
    void deleteAllUserPostsAndLikes(UUID userId);
    void deletePost(String postId);
    Post newComment(String postId, CommentRequest comment);
    Post deleteComment(String postId, String commentId);
    void likeComment(String postId, String commentId);
    void unlikeComment(String postId, String commentId);
    Post updateComment(String postId, String commentId, CommentRequest updateC);
    List<Post> findAllByUserId(String email);
}
