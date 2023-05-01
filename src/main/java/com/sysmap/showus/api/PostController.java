package com.sysmap.showus.api;

import com.sysmap.showus.domain.Post;
import com.sysmap.showus.domain.User;
import com.sysmap.showus.services.post.PostRequest;
import com.sysmap.showus.services.post.PostService;
import com.sysmap.showus.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
public class PostController {

    @Autowired
    private PostService service;

    @Autowired
    private UserService userService;

    @GetMapping("/post")
    public ResponseEntity<List<Post>> findAll(){
        List<Post> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<Post> findById(@PathVariable UUID id){
        Post post = service.findById(id);
        return ResponseEntity.ok().body(post);
    }

    @PostMapping("/{userId}/post")
    public ResponseEntity<Void> createPost(@RequestBody PostRequest request, @PathVariable UUID userId){
        Post post = service.createPost(request, userId);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{userId}/post")
    public ResponseEntity<List<Post>> findAllPostsFromUser(@PathVariable UUID userId){
        User user = userService.findById(userId);
        return ResponseEntity.ok().body(user.getPosts());
    }

    @DeleteMapping("/{userId}/post/{postId}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID userId, @PathVariable UUID postId){
        service.delete(userId, postId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}/post/{postId}")
    public ResponseEntity<Void> updateUser(@PathVariable UUID userId, @PathVariable UUID postId, @RequestBody PostRequest request) {
        service.updatePost(userId, postId, request);
        return ResponseEntity.noContent().build();
    }
}
