package com.sysmap.showus.services.post;

import com.sysmap.showus.data.AuthorDTO;
import com.sysmap.showus.data.IPostRepository;
import com.sysmap.showus.data.IUserRepository;
import com.sysmap.showus.domain.Post;
import com.sysmap.showus.services.exception.ObjNotFoundException;
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

    public List<Post> findAll(){
        return repo.findAll();
    }
//
    public Post findById(UUID id){
        Optional<Post> post = repo.findById(id);
        return post.orElseThrow(() -> new ObjNotFoundException("Post não encontrado"));
    }
//
    public void delete(UUID userId, UUID postId){
        var post = findById(postId);
        if(post.getAuthor().getId().equals(userId)) {
            repo.deleteById(postId);
        }else{
           throw  new ObjNotFoundException("Voce não tem autorização para excluir esse post");
        }
    }

    public Post createPost(PostRequest request, UUID userId) {
        var author = userService.findById(userId);
        var post = new Post(request.getTitle(), request.getBody(), new AuthorDTO(author));
        author.getPosts().addAll(Arrays.asList(post));
        userRepo.save(author);
        return repo.save(post);
    }

    public Post updatePost(UUID userId, UUID postId, PostRequest request) {
        var post = findById(postId);
        if(post.getAuthor().getId().equals(userId)) {
            post.setTitle(request.getTitle());
            post.setBody(request.getBody());
            return repo.save(post);
        }else{
           throw new ObjNotFoundException("Voce não tem autorização para editar esse post");
        }
    }
}
