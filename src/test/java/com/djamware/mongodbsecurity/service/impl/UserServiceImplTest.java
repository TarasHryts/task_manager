package com.djamware.mongodbsecurity.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.djamware.mongodbsecurity.domain.Role;
import com.djamware.mongodbsecurity.domain.User;
import com.djamware.mongodbsecurity.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserServiceImplTest {
    private UserRepository userRepository;
    private UserServiceImpl userService;
    private User user;
    private List<User> expectedData = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl();
        userService.setUserRepository(userRepository);
        Role role = new Role();
        role.setRole("USER");
        user = new User("someId", "example@gmail.com", "password", "someName", Set.of(role));
        expectedData.add(user);
    }

    @Test
    public void findUserByEmail() {
        when(userRepository.findByEmail(any())).thenReturn(user);
        Assert.assertEquals(user.getEmail(), userService.findUserByEmail(any()).getEmail());
        Assert.assertEquals(user.getId(), userService.findUserByEmail(any()).getId());
        Assert.assertEquals(user, userService.findUserByEmail(any()));
    }
}
