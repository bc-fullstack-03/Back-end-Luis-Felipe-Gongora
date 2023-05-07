package com.sysmap.showus.api;

import com.sysmap.showus.domain.entities.Post;
import com.sysmap.showus.services.post.IPostService;
import com.sysmap.showus.services.post.dto.CommentRequest;
import com.sysmap.showus.services.post.dto.PostRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/v1/post")
@Tag(name = "Post")
public class PostController {
    @Autowired
    IPostService _postService;

    @PostMapping("/create")
    @Operation(summary = "Create a new post from a user")
    public ResponseEntity<Post> createPost(@RequestBody PostRequest request){
        var post = _postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @PostMapping("uploadPhoto")
    @Operation(summary = "Upload photo post")
    public ResponseEntity<Void> uploadPhoto(@RequestParam(name = "photo", required = false)MultipartFile photo, String postId){
            _postService.uploadPhotoPost(photo, postId);
            return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("update")
    @Operation(summary = "update a post")
    public ResponseEntity<Post> updatePost(String postId, @RequestBody PostRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(_postService.updatePost(postId, request));
    }

    @PostMapping("like")
    @Operation(summary = "Like a post")
    public ResponseEntity<Post> likePost(String postId){
        return ResponseEntity.status(HttpStatus.OK).body(_postService.likePost(postId));
    }

    @PostMapping("unlike")
    @Operation(summary = "Unlike a post")
    public ResponseEntity<Post> UnlikePost(String postId){
        return ResponseEntity.status(HttpStatus.OK).body(_postService.unlikePost(postId));
    }

    @PostMapping("comment")
    @Operation(summary = "New comment")
    public ResponseEntity<Post> newComment(String postId, @RequestBody CommentRequest comment){
        return ResponseEntity.status(HttpStatus.OK).body(_postService.newComment(postId, comment));
    }

    @GetMapping
    @Operation(summary = "Get all posts")
    public List<Post> findAllPosts(){
        return _postService.findAllPosts();
    }

    @DeleteMapping
    @Operation(summary = "Delete a post")
    public ResponseEntity<Void> deletePost(String postId){
        _postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}
