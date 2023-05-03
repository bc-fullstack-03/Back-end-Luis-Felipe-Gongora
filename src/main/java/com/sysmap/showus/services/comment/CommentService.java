package com.sysmap.showus.services.comment;

import com.sysmap.showus.data.AuthorDTO;
import com.sysmap.showus.data.CommentDTO;
import com.sysmap.showus.data.IPostRepository;
import com.sysmap.showus.data.IUserRepository;
import com.sysmap.showus.domain.Post;
import com.sysmap.showus.services.exception.ObjNotFoundException;
import com.sysmap.showus.services.post.PostService;
import com.sysmap.showus.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class CommentService implements ICommentService {

    @Autowired
    private IPostRepository repo;

    @Autowired
    private IUserRepository userRepo;

    @Autowired
    private UserService userService;
    @Autowired
    PostService postService;

    public void delete(UUID postId, UUID commentId){
        var post = postService.findById(postId);
        var comment = post.getComments().stream().filter(c -> c.getId().equals(commentId)).findFirst().orElseThrow(() -> new ObjNotFoundException("Comentário não encontrado"));
        post.getComments().remove(comment);
        repo.save(post);
    }

    public Post createComment(CommentRequest request, UUID userId, UUID postId) {
        var author = userService.findById(userId);
        var post = repo.findById(postId).orElseThrow(() -> new ObjNotFoundException("Post not found!"));
        var comment = new CommentDTO(request.getText(), new AuthorDTO(author));
        post.getComments().addAll(Arrays.asList(comment));
        return repo.save(post);
    }

    public List<CommentDTO> findAllCommentsFromPost(UUID postId){
        var post = repo.findById(postId).orElseThrow(() -> new ObjNotFoundException("Post found!"));
        return post.getComments();
    }

    public Post updateComment(UUID postId, UUID commentId, CommentRequest request) {
        var post = postService.findById(postId);
        var comment = post.getComments().stream().filter(c -> c.getId().equals(commentId)).findFirst().orElseThrow(() -> new ObjNotFoundException("comment not found"));
        comment.setText(request.getText());
        return repo.save(post);
    }
}
