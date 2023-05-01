package com.sysmap.showus.api;

import com.sysmap.showus.data.UserDTO;
import com.sysmap.showus.domain.Post;
import com.sysmap.showus.domain.User;
import com.sysmap.showus.services.post.PostRequest;
import com.sysmap.showus.services.post.PostService;
import com.sysmap.showus.services.user.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user/{userId}")
public class PostController {

    @Autowired
    private PostService service;

//    @GetMapping
//    public ResponseEntity<List<UserDTO>> findAll(){
//        List<User> list = service.findAll();
//        List<UserDTO> listDTO = list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
//        return ResponseEntity.ok().body(listDTO);
//    }

    @PostMapping("/post")
    public ResponseEntity<Void> createPost(@RequestBody PostRequest request, @PathVariable UUID userId){
        Post post = service.createPost(request, userId);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<UserDTO> findById(@PathVariable UUID id){
//        User user = service.findById(id);
//        return ResponseEntity.ok().body(new UserDTO(user));
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
