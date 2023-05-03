package com.sysmap.showus.services.user;

import com.sysmap.showus.data.AuthorDTO;
import com.sysmap.showus.data.FollowersDTO;
import com.sysmap.showus.data.IUserRepository;
import com.sysmap.showus.domain.User;
import com.sysmap.showus.services.exception.ObjNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository repo;

    public List<User> findAll(){
        return repo.findAll();
    }

    public User findById(UUID id){
        Optional<User> user = repo.findById(id);
        return user.orElseThrow(() -> new ObjNotFoundException("User not found"));
    }

    public void delete(UUID id){
        findById(id);
        repo.deleteById(id);
    }

    public User createUser(UserRequest request) {
        var user = new User(request.getName(), request.getEmail(), request.getPassword());
        return repo.save(user);
    }

    public User updateUser(UUID id, UserRequest request) {
        User newUser = findById(id);
        newUser.setName(request.getName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(request.getPassword());
        return repo.save(newUser);
    }

    public User addFollower(UUID userId, UUID friendId){
        User user = findById(userId);
        AuthorDTO followerUser = new AuthorDTO(findById(friendId));
        FollowersDTO followers = user.getFollowers();
        if (followers.getFollowersList().stream().anyMatch(f -> f.getId().equals(friendId))) {
            throw new ObjNotFoundException("You are already following this user");
        } else {
            followers.getFollowersList().add(followerUser);
            followers.setCountFollowers(followers.getFollowersList().size());
            return repo.save(user);
        }
    }

    public User removeFollower(UUID userId, UUID friendId) {
        User user = findById(userId);
        FollowersDTO followersDTO = user.getFollowers();
        List<AuthorDTO> followersList = followersDTO.getFollowersList();
        if (followersList.removeIf(follower -> follower.getId().equals(friendId))) {
            followersDTO.setCountFollowers(followersDTO.getCountFollowers() - 1);
            return repo.save(user);
        } else {
            throw new ObjNotFoundException("Follower not found");
        }
    }

    public FollowersDTO findAllFollowersFromUser(UUID userId){
        User user = findById(userId);
        return user.getFollowers();
    }
}
