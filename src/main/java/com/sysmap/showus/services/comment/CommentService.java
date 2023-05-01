package com.sysmap.showus.services.comment;

import com.sysmap.showus.data.AuthorDTO;
import com.sysmap.showus.data.CommentDTO;
import com.sysmap.showus.data.IPostRepository;
import com.sysmap.showus.data.IUserRepository;
import com.sysmap.showus.domain.Post;
import com.sysmap.showus.services.exception.ObjNotFoundException;
import com.sysmap.showus.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;

@Service
public class CommentService implements ICommentService {

    @Autowired
    private IPostRepository repo;

    @Autowired
    private IUserRepository userRepo;

    @Autowired
    private UserService userService;
//    public List<Post> findAll(){
//        return repo.findAll();
//    }
//
//    public Post findById(UUID id){
//        Optional<Post> post = repo.findById(id);
//        return post.orElseThrow(() -> new UserNotFoundException("Post não encontrado"));
//    }
//
//    public void delete(UUID id){
//        findById(id);
//        repo.deleteById(id);
//    }

    public Post createComment(CommentRequest request, UUID userId, UUID postId) {
        var author = userService.findById(userId);
        var post = repo.findById(postId).orElseThrow(() -> new ObjNotFoundException("Post não encontrado!"));
        var comment = new CommentDTO(request.getText(), new AuthorDTO(author));
        post.getComments().addAll(Arrays.asList(comment));
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
