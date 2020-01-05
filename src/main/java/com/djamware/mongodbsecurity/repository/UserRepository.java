package com.djamware.mongodbsecurity.repository;

import com.djamware.mongodbsecurity.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
}
