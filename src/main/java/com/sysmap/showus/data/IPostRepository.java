package com.sysmap.showus.data;

import com.sysmap.showus.domain.entities.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IPostRepository extends MongoRepository<Post, UUID> {
    List<Post> findByAuthorId(UUID userId);
    List<Post> findAllByAuthorId(UUID authorId);
}
