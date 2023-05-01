package com.sysmap.showus.services.post;

import com.sysmap.showus.data.AuthorDTO;
import com.sysmap.showus.data.IPostRepository;
import com.sysmap.showus.data.IUserRepository;
import com.sysmap.showus.domain.Post;
import com.sysmap.showus.domain.User;
import com.sysmap.showus.services.exception.UserNotFoundException;
import com.sysmap.showus.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService implements IPostService {

    @Autowired
    private IPostRepository repo;

    @Autowired
    private IUserRepository userRepo;

    @Autowired
    private UserService userService;
//    public List<User> findAll(){
//        return repo.findAll();
//    }
//
//    public User findById(UUID id){
//        Optional<User> user = userRepo.findById(id);
//        return user.orElseThrow(() -> new UserNotFoundException("Usuario não encontrado"));
//    }
//
//    public void delete(UUID id){
//        findById(id);
//        repo.deleteById(id);
//    }

    public Post createPost(PostRequest request, UUID userId) {
        var author = userService.findById(userId);
        var post = new Post(request.getTitle(), request.getBody(), new AuthorDTO(author));
        author.getPosts().addAll(Arrays.asList(post));
        userRepo.save(author);
        return repo.save(post);
    }

//    public User updateUser(UUID id, PostRequest request) {
//        User newUser = findById(id);
//        newUser.setName(request.getName());
//        newUser.setEmail(request.getEmail());
//        newUser.setPassword(request.getPassword());
//        return repo.save(newUser);
//    }
}
