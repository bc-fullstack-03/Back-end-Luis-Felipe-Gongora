package com.sysmap.showus.api;

import com.sysmap.showus.domain.Post;
import com.sysmap.showus.services.post.PostRequest;
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
@Tag(name = "Post")
public class PostController {

    @Autowired
    private PostService service;

    @Autowired
    private UserService userService;

    @GetMapping("/post")
    @Operation(summary = "Find all posts. Ex: Post feed")
    public ResponseEntity<List<Post>> findAll(){
        List<Post> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/post/{postId}")
    @Operation(summary = "Find post by postId")
    public ResponseEntity<Post> findById(@PathVariable UUID postId){
        Post post = service.findById(postId);
        return ResponseEntity.ok().body(post);
    }

    @PostMapping("/{userId}/post")
    @Operation(summary = "Create a new post from user")
    public ResponseEntity<Void> createPost(@RequestBody PostRequest request, @PathVariable UUID userId){
        Post post = service.createPost(request, userId);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{userId}/post")
    @Operation(summary = "Find all posts from an user. Ex: User Profile")
    public ResponseEntity<List<Post>> findAllPostsFromUser(@PathVariable UUID userId){
        return ResponseEntity.ok().body(service.findByAuthorId(userId));
    }

    @DeleteMapping("/{userId}/post/{postId}")
    @Operation(summary = "delete post by postId")
    public ResponseEntity<Void> deleteById(@PathVariable UUID userId, @PathVariable UUID postId){
        service.delete(userId, postId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}/post/{postId}")
    @Operation(summary = "Update post by postId")
    public ResponseEntity<Void> updateUser(@PathVariable UUID userId, @PathVariable UUID postId, @RequestBody PostRequest request) {
        service.updatePost(userId, postId, request);
        return ResponseEntity.noContent().build();
    }
}
