package com.sysmap.showus.services.user;

import com.sysmap.showus.data.IUserRepository;
import com.sysmap.showus.domain.embedded.Author;
import com.sysmap.showus.domain.entities.User;
import com.sysmap.showus.services.fileUpload.IFileUploadService;
import com.sysmap.showus.services.post.IPostService;
import com.sysmap.showus.services.user.dto.FollowersResponse;
import com.sysmap.showus.services.user.dto.UserRequest;
import com.sysmap.showus.services.user.dto.UserResponse;
import com.sysmap.showus.services.utils.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository _userRepo;
    @Autowired
    public PasswordEncoder _passwordEncoder;
    @Autowired
    private IFileUploadService _fileUploadService;
    @Autowired
    private IPostService _postService;

    public UserResponse getUserByEmail(String userEmail) {
        User getUser = _userRepo.findUserByEmail(userEmail);
        if (getUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not registered or invalid e-mail!");
        } else {
            UserResponse userResponse = new UserResponse(getUser);
            return userResponse;
        }
    }

    public User getUserById(UUID userId){
        User getUser = _userRepo.findById(userId).get();
        if (getUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found!");
        } else {
            return getUser;
        }
    }

    public void deleteUser(){
        var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        _postService.deleteAllUserPostsAndLikes(user.getId());

        List<Author> followers = user.getFollowers().getFollowingList();
        for (Author f : followers) {
            User followerUser = _userRepo.findById(f.getId()).orElse(null);
            if (followerUser != null) {
                followerUser.getFollowers().getFollowersList().removeIf(u -> u.getId().equals(user.getId()));
                followerUser.getFollowers().setFollowersCount(followerUser.getFollowers().getFollowersList().size());
                _userRepo.save(followerUser);
            }
        }
        List<Author> following = user.getFollowers().getFollowersList();
        for (Author f : following) {
            User followingUser = _userRepo.findById(f.getId()).orElse(null);
            if (followingUser != null) {
                followingUser.getFollowers().getFollowingList().removeIf(u -> u.getId().equals(user.getId()));
                followingUser.getFollowers().setFollowingCount(followingUser.getFollowers().getFollowingList().size());
                _userRepo.save(followingUser);
            }
        }
        _userRepo.deleteById(user.getId());
    }

    public UUID createUser(UserRequest request) {
        if(Validator.isValidName(request.getName())) {
            if (Validator.isValidPassword(request.getPassword())) {
                if(Validator.isValidEmail(request.getEmail())) {
                    if (_userRepo.findUserByEmail(request.getEmail()) == null) {
                        var user = new User(request.getName(), request.getEmail());
                        user.setPassword(_passwordEncoder.encode(request.getPassword()));
                        _userRepo.save(user);
                        return user.getId();
                    }
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail is already registered");
                }
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Enter a valid e-mail address!");
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password cannot contain spaces and must have at least 3 characters!");
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is empty or less than 3 characters!");
    }

    public void uploadPhotoProfile(MultipartFile photo){
        var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if (!photo.isEmpty()) {
            try {
                var fileName = user.getId() + "." + photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".") + 1);
                user.setPhotoUri(_fileUploadService.upload(photo, fileName));
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            }
            _userRepo.save(user);
        }else{
            user.setPhotoUri("");
            _userRepo.save(user);
        }
    }

    public User updateUser(UserRequest request) {
        var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if(Validator.isValidName(request.getName())){
            user.setName(request.getName());
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is empty or less than 3 characters!");
        }
        if(Validator.isValidEmail(request.getEmail())){
            if(user.getEmail().equals(request.getEmail()) || _userRepo.findUserByEmail(request.getEmail()) == null) {
                user.setEmail(request.getEmail());
            }
            else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This email was already registered!");
            }
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Enter a valid e-mail address!");
        }
        if(Validator.isValidPassword(request.getPassword())){
            user.setPassword(_passwordEncoder.encode(request.getPassword()));
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password cannot contain spaces and must have at least 3 characters!");
        }
        return _userRepo.save(user);
    }

    public UserResponse addFollower(String followerEmail) {
        var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        User follower = getUserById(getUserByEmail(followerEmail).getId());
        if(!user.getId().equals(follower.getId())){
                if (user.getFollowers().getFollowingList().stream().anyMatch(f -> f.getId().equals(follower.getId()))) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "You are already following this user!");
                }
            Author followUser = new Author(follower);
            user.getFollowers().getFollowingList().add(followUser);
            user.getFollowers().setFollowingCount(user.getFollowers().getFollowingList().size());

            Author followerUser = new Author(user);
            var userF = getUserById(follower.getId());
            userF.getFollowers().getFollowersList().add(followerUser);
            userF.getFollowers().setFollowersCount(userF.getFollowers().getFollowersList().size());
            _userRepo.save(user);
            _userRepo.save(userF);
            return getUserByEmail(user.getEmail());
        }else{
            throw new ResponseStatusException(HttpStatus.CONFLICT, "You cannot follow yourself!");
        }
    }

    public void unfollow(String followerEmail) {
        var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        User follower = getUserById(getUserByEmail(followerEmail).getId());
        if (user.getFollowers().getFollowingList().removeIf(f -> f.getId().equals(follower.getId())) && follower.getFollowers().getFollowersList().removeIf(f -> f.getId().equals(user.getId()))){
            user.getFollowers().setFollowingCount(user.getFollowers().getFollowingList().size());
            follower.getFollowers().setFollowersCount(follower.getFollowers().getFollowersList().size());
            _userRepo.save(user);
            _userRepo.save(follower);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Follower not found!");
        }
    }

    public List<FollowersResponse> getUsersToFollow() {
        var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        var exceptionIds = user.getFollowers().getFollowingList().stream()
                .map(f -> f.getId())
                .collect(Collectors.toList());
        exceptionIds.add(user.getId());

        var usersListToFollow = _userRepo.findAllByIdNotIn(exceptionIds);

        return usersListToFollow.stream()
                .map(u -> new FollowersResponse(u))
                .collect(Collectors.toList());
    }

    public List<UUID> getAllFollowedId(){
        var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if(user.getFollowers().getFollowingList().size() == 0) {
            List<UUID> followerId = new ArrayList<>(List.of(user.getId()));
            return followerId;
        }
            var followerId = user.getFollowers().getFollowingList().stream()
                    .map(f -> f.getId())
                    .collect(Collectors.toList());
            followerId.add(user.getId());
            return followerId;
    }
}
