package com.petermenice.Peter.Menice.controllers;

import com.petermenice.Peter.Menice.entities.Subscription;
import com.petermenice.Peter.Menice.entities.User;
import com.petermenice.Peter.Menice.repos.SubscriptionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/subs")
public class SubscriptionController {

    @Autowired
    private SubscriptionRepo subscriptionRepo;

    @GetMapping
    public List<Subscription> subscriptionList() {
        return subscriptionRepo.findAll();
    }

    @GetMapping
    @RequestMapping("/{id}")
    public Subscription subscription(@PathVariable Long id) {
        return subscriptionRepo.getReferenceById(id);
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody Subscription subscription) throws Exception {
        List<Subscription> allSubscriptions = subscriptionRepo.findAll();
        for (Subscription s : allSubscriptions) {
            if (s.getName().equalsIgnoreCase(subscription.getName())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if (s.getId().equals(subscription.getId())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        subscription.setName(subscription.getName().toLowerCase());
        subscription.setActive(true);
        subscriptionRepo.saveAndFlush(subscription);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
