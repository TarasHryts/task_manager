package com.djamware.mongodbsecurity.controller;

import com.djamware.mongodbsecurity.constants.Constants;
import com.djamware.mongodbsecurity.domain.User;
import com.djamware.mongodbsecurity.dto.UserCreateDto;
import com.djamware.mongodbsecurity.exception.UserAlreadyExistException;
import com.djamware.mongodbsecurity.exception.WrongPasswordException;
import com.djamware.mongodbsecurity.service.UserService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    private UserService userService;

    @Autowired
    public Controller(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public User createNewUser(@Valid @RequestBody UserCreateDto userCreateDto) {
        User userExists = userService.findUserByEmail(userCreateDto.getEmail());
        if (userExists != null) {
            throw new UserAlreadyExistException("There is already a user registered " +
                    "with the username provided");
        } else {
            User newUser = new User();
            newUser.setUsername(userCreateDto.getUsername());
            newUser.setEmail(userCreateDto.getEmail());
            if (!userCreateDto.getPassword().equals(userCreateDto.getPasswordConfirm())) {
                throw new WrongPasswordException("Confirm your password");
            }
            newUser.setPassword(userCreateDto.getPassword());
            return userService.saveUser(newUser);
        }
    }

    @GetMapping("/allUsers")
    public List<User> getAllUser(@RequestParam(value = "page", required = false,
            defaultValue = Constants.PAGE) Integer page,
                                 @RequestParam(value = "limit", required = false,
                                         defaultValue = Constants.LIMIT) Integer limit,
                                 @RequestParam(value = "sortBy", required = false,
                                         defaultValue = Constants.USERS_SORT_BY) String sortBy,
                                 @RequestParam(value = "sortOrder", required = false,
                                         defaultValue = Constants.SORT_ORDER) String sortOrder) {
        Sort.Direction orderingDirection = Sort.Direction.fromString(sortOrder);
        Sort sortByRequest = Sort.by(orderingDirection, sortBy);
        Pageable pageRequest = PageRequest.of(page, limit, sortByRequest);
        return userService.findAll(pageRequest);
    }
}
