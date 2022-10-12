package com.petermenice.Peter.Menice.controllers;

import com.petermenice.Peter.Menice.entities.Subscription;
import com.petermenice.Peter.Menice.entities.User;
import com.petermenice.Peter.Menice.repos.SubscriptionRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/subs")
public class SubscriptionController {

    @Autowired
    private SubscriptionRepo subscriptionRepo;

    @GetMapping
    public List<Subscription> subscriptionList() {
        return subscriptionRepo.findAllByOrderByNameAsc();
    }

    @GetMapping
    @RequestMapping("/{id}")
    public Subscription subscription(@PathVariable Long id) {
        return subscriptionRepo.getReferenceById(id);
    }

    /*
    Returns a list of ACTIVE subscriptions regardless of the pathvariable unless the pathvariable is specifically "false".
    If the pathvariable is missing, it will fail with a 400.
    */
    @GetMapping
    @RequestMapping("/active/{activeBoolean}")
    public List<Subscription> activeSubscriptions(@PathVariable String activeBoolean) {
        boolean a = !activeBoolean.equalsIgnoreCase("false");
        return subscriptionRepo.findAllByActive(a);
    }

    @PostMapping
    public ResponseEntity<?> addSub(@RequestBody Subscription subscription) {
        List<Subscription> allSubscriptions = subscriptionRepo.findAll();
        for (Subscription s : allSubscriptions) {
            if (s.getName().equalsIgnoreCase(subscription.getName())
                || s.getDescription().equalsIgnoreCase(subscription.getDescription())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        subscription.setActive(true);
        subscriptionRepo.saveAndFlush(subscription);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateSubscriptionById(@PathVariable Long id, @RequestBody Subscription subscription) {
        Subscription existingSub = subscriptionRepo.getReferenceById(subscription.getId());
        if (id.equals(subscription.getId())) {
            List<Subscription> allOtherSubId = subscriptionRepo.findAll();
            allOtherSubId.removeIf(s -> Objects.equals(s.getId(), subscription.getId()));
            List<Subscription> allOtherSubName = new ArrayList<>(allOtherSubId);
            List<Subscription> allOtherSubDescription = new ArrayList<>(allOtherSubId);
            allOtherSubName.removeIf(s -> !Objects.equals(s.getName().toLowerCase(), subscription.getName().toLowerCase()));
            allOtherSubDescription.removeIf(s -> !Objects.equals(s.getDescription().toLowerCase(), subscription.getDescription().toLowerCase()));
            if (allOtherSubName.size() == 0 && allOtherSubDescription.size() == 0) {
                BeanUtils.copyProperties(subscription, existingSub, "id");
                subscriptionRepo.saveAndFlush(existingSub);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
