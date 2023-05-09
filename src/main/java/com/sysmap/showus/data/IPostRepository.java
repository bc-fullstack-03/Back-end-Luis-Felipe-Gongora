package com.sysmap.showus.data;

import com.sysmap.showus.domain.entities.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IPostRepository extends MongoRepository<Post, UUID> {
    List<Post> findAllByAuthorId(UUID userId);
    List<Post> findAllByAuthorIdIn(List<UUID> followersId);
}
