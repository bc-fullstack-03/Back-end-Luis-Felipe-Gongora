package com.sysmap.showus.services.user;

import com.sysmap.showus.domain.entities.User;
import com.sysmap.showus.services.user.dto.FollowersResponse;
import com.sysmap.showus.services.user.dto.UserRequest;
import com.sysmap.showus.services.user.dto.UserResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    UserResponse getUserByEmail(String userEmail);
    User getUserById(UUID userId);
    void deleteUser();
    UUID createUser(UserRequest request);
    void uploadPhotoProfile(MultipartFile photo);
    User updateUser(UserRequest request);
    UserResponse addFollower(String followerEmail);
    UserResponse unfollow(String followerEmail);
    List<FollowersResponse> getUsersToFollow();
}
