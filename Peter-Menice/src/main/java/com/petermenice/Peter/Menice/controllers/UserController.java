package com.petermenice.Peter.Menice.controllers;

import com.petermenice.Peter.Menice.entities.User;
import com.petermenice.Peter.Menice.repos.UserRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping
    public List<User> userList() {
        return userRepo.findAllByOrderByUserNameAsc();
    }

/*
    Returns a list of ACTIVE users regardless of the pathvariable unless the pathvariable is specifically "false".
    If the pathvariable is missing, it will fail with a 400.
 */
    @GetMapping
    @RequestMapping("/active/{activeBoolean}")
    public List<User> activeUsers(@PathVariable String activeBoolean) {
        boolean a = !activeBoolean.equalsIgnoreCase("false");
        return userRepo.findAllByActive(a);
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody User user) throws Exception {
        List<User> allUsers = userRepo.findAll();
        for (User u : allUsers) {
            if (u.getUserName().equalsIgnoreCase(user.getUserName())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if (u.getId().equals(user.getId())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if (u.getEmail().equals(user.getEmail())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        user.setUserName(user.getUserName().toLowerCase());
        user.setEmail(user.getEmail().toLowerCase());
        user.setDateCreated(LocalDateTime.now());
        user.setActive(true);
        userRepo.saveAndFlush(user);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping
    @RequestMapping("{id}")
    public User userById(@PathVariable Long id) {
        return userRepo.getReferenceById(id);
    }

    @GetMapping
    @RequestMapping("/user/{userName}")
    public User userByName(@PathVariable String userName) {
        return userRepo.getReferenceByUserName(userName.toLowerCase());
    }

    @GetMapping
    @RequestMapping("/email/{email}")
    public User userByEmail(@PathVariable String email) {
        return userRepo.getReferenceByEmail(email.toLowerCase());
    }

    @GetMapping
    @RequestMapping("/birthMonth/{month}")
    public List<User> usersByBirthMonth(@PathVariable Integer month) {
        List<User> users = userRepo.findAllByBirthMonth(month);
        users.removeIf(u -> !u.getActive());
        return users;
    }

    @GetMapping
    @RequestMapping("/available/{userName}")
    public Boolean userNameAvailable(@PathVariable String userName) {
        boolean available = false;
        if (userRepo.getReferenceByUserName(userName) == null) {
            available = true;
        }
        return available;
    }

    @GetMapping
    @RequestMapping("/sub/{sub}")
    public List<User> usersBySubscription(@PathVariable Integer sub) {
        List<User> allActive = activeUsers("true");
        List<User> activeSubscribers = new ArrayList<>();
        for (User u : allActive) {
            Integer[] subArray = u.getSubscriptions();
            if (Arrays.asList(subArray).contains(sub)) {
                activeSubscribers.add(u);
            }
        }
        return activeSubscribers;
    }

    @RequestMapping(value = "/updateUser/{id}", method = RequestMethod.PUT)
    public User updateUserById(@PathVariable Long id, @RequestBody User user) {
        User existingUser = userRepo.getReferenceById(id);
        BeanUtils.copyProperties(user, existingUser, "id");
        return userRepo.saveAndFlush(existingUser);
    }

}
