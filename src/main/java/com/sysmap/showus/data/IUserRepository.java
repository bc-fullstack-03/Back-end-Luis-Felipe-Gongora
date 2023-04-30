package com.sysmap.showus.data;

import com.sysmap.showus.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IUserRepository extends MongoRepository<User, UUID> {
}
