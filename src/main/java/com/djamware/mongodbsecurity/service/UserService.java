package com.djamware.mongodbsecurity.service;

import com.djamware.mongodbsecurity.domain.User;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findUserByEmail(String email);

    User saveUser(User user);

    UserDetails loadUserByUsername(String email);

    List<User> findAll(Pageable pageRequest);
}
