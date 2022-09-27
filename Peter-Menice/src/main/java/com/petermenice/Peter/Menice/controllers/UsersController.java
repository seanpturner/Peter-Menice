package com.petermenice.Peter.Menice.controllers;

import com.petermenice.Peter.Menice.entities.Users;
import com.petermenice.Peter.Menice.repos.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersRepo usersRepo;

    @GetMapping
    public List<Users> userList() {
        return usersRepo.findAll();
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody Users user) throws Exception {
        List<Users> allUsers = usersRepo.findAll();
        for (Users u : allUsers) {
            if (u.getUserName().equalsIgnoreCase(user.getUserName())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if (Objects.equals(u.getId(), user.getId())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        usersRepo.saveAndFlush(user);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
