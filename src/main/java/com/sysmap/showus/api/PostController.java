package com.sysmap.showus.api;

import com.sysmap.showus.domain.entities.Post;
import com.sysmap.showus.services.post.IPostService;
import com.sysmap.showus.services.post.dto.CommentRequest;
import com.sysmap.showus.services.post.dto.PostRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/post")
@Tag(name = "Post")
public class PostController {
    @Autowired
    IPostService _postService;

    @PostMapping("/create")
    @Operation(summary = "Create a new post from a user", security = @SecurityRequirement(name = "token"))
    @Parameter(name = "RequestedBy", description = "User Id Authorization", required = true, schema = @Schema(type = "string"))
    public ResponseEntity<Post> createPost(@RequestBody PostRequest request, @RequestHeader("RequestedBy") String CurrentUserId){
        var post = _postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @GetMapping("/user")
    @Operation(summary = "Get all posts by user", security = @SecurityRequirement(name = "token"))
    @Parameter(name = "RequestedBy", description = "User Id Authorization", required = true, schema = @Schema(type = "string"))
    public ResponseEntity<List<Post>> findAllByUserId(String userId, @RequestHeader("RequestedBy") String CurrentUserId){
        return ResponseEntity.ok().body(_postService.findAllByUserId(userId));
    }

    @PostMapping(value = "/uploadPhoto", name = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload photo post", security = @SecurityRequirement(name = "token"))
    @Parameter(name = "RequestedBy", description = "User Id Authorization", required = true, schema = @Schema(type = "string"))
    public ResponseEntity<Void> uploadPhoto(@RequestParam(name = "photo", required = false)MultipartFile photo, String postId, @RequestHeader("RequestedBy") String CurrentUserId){
            _postService.uploadPhotoPost(photo, postId);
            return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/update")
    @Operation(summary = "update a post", security = @SecurityRequirement(name = "token"))
    @Parameter(name = "RequestedBy", description = "User Id Authorization", required = true, schema = @Schema(type = "string"))
    public ResponseEntity<Post> updatePost(String postId, @RequestBody PostRequest request, @RequestHeader("RequestedBy") String CurrentUserId){
        return ResponseEntity.status(HttpStatus.OK).body(_postService.updatePost(postId, request));
    }

    @PostMapping("/like")
    @Operation(summary = "Like a post", security = @SecurityRequirement(name = "token"))
    @Parameter(name = "RequestedBy", description = "User Id Authorization", required = true, schema = @Schema(type = "string"))
    public ResponseEntity<Post> likePost(String postId, @RequestHeader("RequestedBy") String CurrentUserId){
        return ResponseEntity.status(HttpStatus.OK).body(_postService.likePost(postId));
    }

    @PostMapping("/unlike")
    @Operation(summary = "Unlike a post", security = @SecurityRequirement(name = "token"))
    @Parameter(name = "RequestedBy", description = "User Id Authorization", required = true, schema = @Schema(type = "string"))
    public ResponseEntity<Post> UnlikePost(String postId, @RequestHeader("RequestedBy") String CurrentUserId){
        return ResponseEntity.status(HttpStatus.OK).body(_postService.unlikePost(postId));
    }

    @PostMapping("/comment")
    @Operation(summary = "New comment", security = @SecurityRequirement(name = "token"))
    @Parameter(name = "RequestedBy", description = "User Id Authorization", required = true, schema = @Schema(type = "string"))
    public ResponseEntity<Post> newComment(String postId, @RequestBody CommentRequest comment, @RequestHeader("RequestedBy") String CurrentUserId){
        return ResponseEntity.status(HttpStatus.OK).body(_postService.newComment(postId, comment));
    }

    @PostMapping("/comment/update")
    @Operation(summary = "Update a comment", security = @SecurityRequirement(name = "token"))
    @Parameter(name = "RequestedBy", description = "User Id Authorization", required = true, schema = @Schema(type = "string"))
    public ResponseEntity<Post> updateComment(String postId, String commentId, @RequestBody CommentRequest updateC, @RequestHeader("RequestedBy") String CurrentUserId){
        return ResponseEntity.status(HttpStatus.OK).body(_postService.updateComment(postId, commentId, updateC));
    }

    @GetMapping
    @Operation(summary = "Get all posts", security = @SecurityRequirement(name = "token"))
    @Parameter(name = "RequestedBy", description = "User Id Authorization", required = true, schema = @Schema(type = "string"))
    public ResponseEntity<List<Post>> findAllPosts(@RequestHeader("RequestedBy") String CurrentUserId){
        return ResponseEntity.ok().body(_postService.findAllPosts());
    }

    @DeleteMapping
    @Operation(summary = "Delete a post", security = @SecurityRequirement(name = "token"))
    @Parameter(name = "RequestedBy", description = "User Id Authorization", required = true, schema = @Schema(type = "string"))
    public ResponseEntity<Void> deletePost(String postId, @RequestHeader("RequestedBy") String CurrentUserId){
        _postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/comment/delete")
    @Operation(summary = "Delete a comment", security = @SecurityRequirement(name = "token"))
    @Parameter(name = "RequestedBy", description = "User Id Authorization", required = true, schema = @Schema(type = "string"))
    public ResponseEntity<Post> deleteComment(String postId, String commentId, @RequestHeader("RequestedBy") String CurrentUserId){
        return ResponseEntity.ok().body(_postService.deleteComment(postId, commentId));
    }

    @PostMapping("/comment/like")
    @Operation(summary = "Like a Comment", security = @SecurityRequirement(name = "token"))
    @Parameter(name = "RequestedBy", description = "User Id Authorization", required = true, schema = @Schema(type = "string"))
    public ResponseEntity<Post> likeComment(String postId, String commentId, @RequestHeader("RequestedBy") String CurrentUserId){
        return ResponseEntity.ok().body(_postService.likeComment(postId, commentId));
    }

    @PostMapping("/comment/unlike")
    @Operation(summary = "Unlike a comment", security = @SecurityRequirement(name = "token"))
    @Parameter(name = "RequestedBy", description = "User Id Authorization", required = true, schema = @Schema(type = "string"))
    public ResponseEntity<Post> UnlikeComment(String postId, String commentId, @RequestHeader("RequestedBy") String CurrentUserId){
        return ResponseEntity.ok().body(_postService.unlikeComment(postId, commentId));
    }
}
