package com.djamware.mongodbsecurity.repository;

import com.djamware.mongodbsecurity.domain.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {
    Role findByRole(String role);
}
