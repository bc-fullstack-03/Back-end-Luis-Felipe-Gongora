package com.sysmap.showus.api;

import com.sysmap.showus.data.UserDTO;
import com.sysmap.showus.domain.User;
import com.sysmap.showus.services.user.UserRequest;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    @Operation(summary = "Find All users")
    public ResponseEntity<List<UserDTO>> findAll(){
        List<User> list = service.findAll();
        List<UserDTO> listDTO = list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @PostMapping
    @Operation(summary = "Create a new user")
    public ResponseEntity<Void> createUser(@RequestBody UserRequest request){
        User user = service.createUser(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find user by id")
    public ResponseEntity<UserDTO> findById(@PathVariable UUID id){
        User user = service.findById(id);
        return ResponseEntity.ok().body(new UserDTO(user));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user by id")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user by id")
    public ResponseEntity<Void> updateUser(@RequestBody UserRequest request, @PathVariable UUID id) {
        User user = service.updateUser(id, request);
        return ResponseEntity.noContent().build();
    }
}
