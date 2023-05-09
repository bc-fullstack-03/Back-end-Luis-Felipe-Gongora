package com.sysmap.showus.services.post;

import com.sysmap.showus.data.IPostRepository;
import com.sysmap.showus.domain.embedded.Author;
import com.sysmap.showus.domain.embedded.Comment;
import com.sysmap.showus.domain.embedded.Likes;
import com.sysmap.showus.domain.entities.Post;
import com.sysmap.showus.domain.entities.User;
import com.sysmap.showus.services.fileUpload.IFileUploadService;
import com.sysmap.showus.services.post.dto.CommentRequest;
import com.sysmap.showus.services.post.dto.PostRequest;
import com.sysmap.showus.services.utils.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class PostService implements IPostService{
    @Autowired
    private IPostRepository _postRepo;
    @Autowired
    IFileUploadService _fileUploadService;

    public Post createPost(PostRequest request) {
        var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if(Validator.isValidTitleDescription(request.getTitle(), request.getDescription())) {
            Post post = new Post(request.getTitle(), request.getDescription(), new Author(user));
            return _postRepo.save(post);
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Make sure the title and the description have at least 3 letters!");
        }
    }

    public void uploadPhotoPost(MultipartFile photo, String postId){
        var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Post post = getPost(postId);
        if(post.getAuthor().getId().equals(user.getId())) {
            if (post != null) {
                if (!photo.isEmpty()) {
                    try {
                        var fileName = post.getId() + "." + photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".") + 1);
                        post.setPhotoUri(_fileUploadService.upload(photo, fileName));
                    } catch (Exception e) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
                    }
                    _postRepo.save(post);
                } else {
                    post.setPhotoUri("");
                    _postRepo.save(post);
                }
            }
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have authorization to edit this post");
        }
    }
    public List<Post> findAllPosts(){
        return _postRepo.findAll();
    }

    public List<Post> getAllPostsByFollowed(List<UUID> followersId){
        return _postRepo.findAllByAuthorIdIn(followersId);
    }

    public Post getPost(String postId){
        try {
            _postRepo.findById(UUID.fromString(postId)).get();
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
        Post post = _postRepo.findById(UUID.fromString(postId)).get();
        if(post != null){
            return post;
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found!");
        }
    }

    public List<Post> findAllByUserId(String userId){
        try {
            return _postRepo.findAllByAuthorId(UUID.fromString(userId));
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    public Post updatePost(String postId, PostRequest request){
        var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Post post = getPost(postId);
        if(post.getAuthor().getId().equals(user.getId())){
            if(Validator.isValidTitleDescription(request.getTitle(), request.getDescription())) {
                post.setTitle(request.getTitle());
                post.setDescription(request.getDescription());
                return _postRepo.save(post);
            }else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Make sure the title and the description have at least 3 letters!");
            }
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have authorization to edit this post");
        }
    }

    public Post likePost(String postId){
        var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Post post = getPost(postId);
        if (!post.getLikes().stream().anyMatch(userLike -> userLike.getId().equals(user.getId()))) {
            post.getLikes().add(new Likes(user));
            post.setLikesCount(post.getLikes().size());
            return _postRepo.save(post);
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User is already liked!");
        }
    }

    public Post unlikePost(String postId){
        var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Post post = getPost(postId);
        List<Likes> likes = post.getLikes();
        if (!likes.removeIf(userLike -> userLike.getId().equals(user.getId()))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User is already unliked!");
        }
        post.setLikes(likes);
        post.setLikesCount(post.getLikes().size());
        return _postRepo.save(post);
    }

    public void deleteAllUserPostsAndLikes(UUID userId){
        List<Post> allPosts = findAllPosts();
        for(Post post : allPosts) {
            List<Likes> likes = post.getLikes();
            List<Comment> comments = post.getComments();

            for(Comment comment : comments) {
                List<Likes> commentLikes = comment.getLikes();
                commentLikes.removeIf(like -> like.getId().equals(userId));
                comment.setLikes(commentLikes);
                comment.setLikesCount(commentLikes.size());
            }

            likes.removeIf(like -> like.getId().equals(userId));
            comments.removeIf(c -> c.getAuthor().getId().equals(userId));

            post.setLikes(likes);
            post.setLikesCount(post.getLikes().size());
            post.setComments(comments);
            _postRepo.save(post);

            if(post.getAuthor().getId().equals(userId)) {
                _postRepo.delete(post);
            }
        }
    }

    public void deletePost(String postId){
        var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Post post = getPost(postId);
        if(user.getId().equals(post.getAuthor().getId())){
            _postRepo.delete(post);
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have authorization to delete this post");
        }
    }

    public Post newComment(String postId, CommentRequest comment){
        var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Post post = getPost(postId);
        if(Validator.isValidComment(comment.getComment())) {
            post.getComments().add(new Comment(comment.getComment(), new Author(user)));
            return _postRepo.save(post);
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Make sure the comment have at least 3 letters!");
        }
    }

    public Post updateComment(String postId, String commentId, CommentRequest updateC){
        var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Post post = getPost(postId);
        try {
            Comment comment = post.getComments().stream().filter(c -> c.getId().equals(UUID.fromString(commentId))).findFirst().get();
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found");
        }
        Comment comment = post.getComments().stream().filter(c -> c.getId().equals(UUID.fromString(commentId))).findFirst().get();
        if (!user.getId().equals(comment.getAuthor().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not authorized to delete this comment");
        }
        if(!Validator.isValidComment(updateC.getComment())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Make sure the comment have at least 3 letters!");
        }
        comment.setComment(updateC.getComment());
        return _postRepo.save(post);
    }

    public Post deleteComment(String postId, String commentId){
        var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Post post = getPost(postId);
        Comment comment = post.getComments().stream().filter(c -> c.getId().equals(UUID.fromString(commentId)))
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));

        if (!user.getId().equals(post.getAuthor().getId()) && !user.getId().equals(comment.getAuthor().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not authorized to delete this comment");
        }

        post.getComments().remove(comment);
        post.setComments(post.getComments());

        return _postRepo.save(post);
    }

    public Post likeComment(String postId, String commentId){
        var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Post post = getPost(postId);
        Comment comment = post.getComments().stream().filter(c -> c.getId().equals(UUID.fromString(commentId)))
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));
        if (!comment.getLikes().stream().anyMatch(userLike -> userLike.getId().equals(user.getId()))){
            comment.getLikes().add(new Likes(user));
            comment.setLikesCount(comment.getLikes().size());

            return _postRepo.save(post);
        }else{
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User is already liked!");
        }
    }

    public Post unlikeComment(String postId, String commentId){
        var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Post post = getPost(postId);
        Comment comment = post.getComments().stream().filter(c -> c.getId().equals(UUID.fromString(commentId)))
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));
        List<Likes> likes = comment.getLikes();
        if (!likes.removeIf(userLike -> userLike.getId().equals(user.getId()))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User is already unliked!");
        }
        comment.setLikes(likes);
        comment.setLikesCount(comment.getLikes().size());
        return _postRepo.save(post);
    }
}
