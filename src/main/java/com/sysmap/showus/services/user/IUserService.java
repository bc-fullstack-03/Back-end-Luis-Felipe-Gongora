package com.sysmap.showus.services.user;

import com.sysmap.showus.data.FollowersDTO;
import com.sysmap.showus.domain.User;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    List<User> findAll();
    User findById(UUID id);
    void delete(UUID id);
    User createUser(UserRequest request);
    User updateUser(UUID id, UserRequest request);
    User addFollower(UUID userId, UUID friendId);
    User removeFollower(UUID userId, UUID friendId);
    FollowersDTO findAllFollowersFromUser(UUID userId);
}
