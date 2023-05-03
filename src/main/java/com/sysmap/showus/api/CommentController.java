package com.sysmap.showus.api;

import com.sysmap.showus.domain.DTO.CommentDTO;
import com.sysmap.showus.domain.Post;
import com.sysmap.showus.services.comment.CommentRequest;
import com.sysmap.showus.services.comment.CommentService;
import com.sysmap.showus.services.post.PostService;
import com.sysmap.showus.services.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "Comment")
public class CommentController {
    @Autowired
    private CommentService service;
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @PostMapping("/{userId}/post/{postId}/comment")
    @Operation(summary = "Create a new comment from post")
    public ResponseEntity<Void> createComment(@RequestBody CommentRequest request, @PathVariable UUID userId, @PathVariable UUID postId){
        Post post = service.createComment(request, userId, postId);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/post/{postId}/comment")
    @Operation(summary = "Find all comments from a post")
    public ResponseEntity<List<CommentDTO>> findAllCommentsFromPost(@PathVariable UUID postId){
        var comments = service.findAllCommentsFromPost(postId);
        return ResponseEntity.ok().body(comments);
    }

    @DeleteMapping("/post/{postId}/comment/{commentId}")
    @Operation(summary = "Delete a comment by commentId")
    public ResponseEntity<Void> deleteById(@PathVariable UUID postId, @PathVariable UUID commentId){
        service.delete(postId, commentId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/post/{postId}/comment/{commentId}")
    @Operation(summary = "Update a comment by commentId")
    public ResponseEntity<Void> updateComment(@RequestBody CommentRequest request, @PathVariable UUID postId, @PathVariable UUID commentId) {
        service.updateComment(postId, commentId, request);
        return ResponseEntity.noContent().build();
    }
}
