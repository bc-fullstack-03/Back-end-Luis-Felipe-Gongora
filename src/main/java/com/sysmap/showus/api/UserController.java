package com.sysmap.showus.api;

import com.sysmap.showus.services.security.IJwtService;
import com.sysmap.showus.services.user.IUserService;
import com.sysmap.showus.services.user.dto.FollowersResponse;
import com.sysmap.showus.services.user.dto.UserRequest;
import com.sysmap.showus.services.user.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User")
public class UserController {
    @Autowired
    private IUserService _userService;
    @Autowired
    private IJwtService _jwtService;
    @GetMapping()
    @Operation(summary = "Get a User")
    public ResponseEntity<UserResponse> getUserByEmail(String email){
        return ResponseEntity.ok().body(_userService.getUserByEmail(email));
    }

    @GetMapping("/follow")
    @Operation(summary = "Get a list of users to follow")
    public ResponseEntity<List<FollowersResponse>> getUsersToFollow(){
        return ResponseEntity.ok().body(_userService.getUsersToFollow());
    }

    @PostMapping("/create")
    @Operation(summary = "Create a new user")
    public ResponseEntity<UUID> createUser(@RequestBody UserRequest request){
        UUID user = _userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/photo/upload")
    @Operation(summary = "Upload photo user profile")
    public ResponseEntity<Void> uploadPhotoProfile(@RequestParam("photo") MultipartFile photo){
        try {
            _userService.uploadPhotoProfile(photo);
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/add")
    @Operation(summary = "Add follower")
    public ResponseEntity<UserResponse> addFollower(String email){
        return ResponseEntity.ok().body(_userService.addFollower(email));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete a user")
    public ResponseEntity<Void> deleteById(){
        _userService.deleteUser();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/follower/unfollow")
    @Operation(summary = "Unfollow a follower")
    public ResponseEntity<UserResponse> unfollow(String email){
        return ResponseEntity.ok().body(_userService.unfollow(email));
    }

    @PutMapping("/update")
    @Operation(summary = "Update user")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest request) {
        var user = new UserResponse(_userService.getUserById(_userService.updateUser(request).getId()));
        return ResponseEntity.ok().body(user);
    }
}
