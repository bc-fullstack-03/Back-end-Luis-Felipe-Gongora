package com.sysmap.showus.api;

import com.sysmap.showus.domain.Post;
import com.sysmap.showus.services.comment.CommentRequest;
import com.sysmap.showus.services.comment.CommentService;
import com.sysmap.showus.services.post.PostService;
import com.sysmap.showus.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
public class CommentController {
    @Autowired
    private CommentService service;
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

//    @GetMapping("/post")
//    public ResponseEntity<List<Post>> findAll(){
//        List<Post> list = service.findAll();
//        return ResponseEntity.ok().body(list);
//    }
//
//    @GetMapping("/post/{id}")
//    public ResponseEntity<Post> findById(@PathVariable UUID id){
//        Post post = service.findById(id);
//        return ResponseEntity.ok().body(post);
//    }

    @PostMapping("/{userId}/post/{postId}")
    public ResponseEntity<Void> createComment(@RequestBody CommentRequest request, @PathVariable UUID userId, @PathVariable UUID postId){
        Post post = service.createComment(request, userId, postId);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

//    @GetMapping("/{userId}/post")
//    public ResponseEntity<List<Post>> findAllPostsFromUser(@PathVariable UUID userId){
//        User user = userService.findById(userId);
//        return ResponseEntity.ok().body(user.getPosts());
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteById(@PathVariable UUID id){
//        service.delete(id);
//        return ResponseEntity.noContent().build();
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Void> updateUser(@RequestBody UserRequest request, @PathVariable UUID id) {
//        User user = service.updateUser(id, request);
//        return ResponseEntity.noContent().build();
//    }
}
