package com.petermenice.Peter.Menice.controllers;

import com.petermenice.Peter.Menice.entities.Tracking;
import com.petermenice.Peter.Menice.repos.TrackingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tracking")
public class TrackingController {

    @Autowired
    TrackingRepo trackingRepo;

    @GetMapping
    public List<Tracking> allTracking() {
        return trackingRepo.findAll();
    }

    @GetMapping
    @RequestMapping("/{id}")
    public Tracking getTrackingById(@PathVariable Long id) {
        return trackingRepo.getReferenceById(id);
    }

    @GetMapping
    @RequestMapping("/between/{startMonth}/{startDay}/{startYear}/and/{endMonth}/{endDay}/{endYear}")
    public List<Tracking> trackingBetweenDates(@PathVariable int startMonth,
                                               @PathVariable int startDay,
                                               @PathVariable int startYear,
                                               @PathVariable int endMonth,
                                               @PathVariable int endDay,
                                               @PathVariable int endYear) {
        List<Tracking> dateTracking = trackingRepo.findAll();
        LocalDateTime startDate = LocalDateTime.of(startYear, startMonth, startDay, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(endYear, endMonth, endDay, 23, 59, 59);
        dateTracking.removeIf(d -> d.getTimestamp().isBefore(startDate) || d.getTimestamp().isAfter(endDate));
        return dateTracking;
    }

    @GetMapping
    @RequestMapping("/byUser/{userId}/between/{startMonth}/{startDay}/{startYear}/and/{endMonth}/{endDay}/{endYear}")
    public List<Tracking> trackingByUserBetweenDates(
            @PathVariable Long userId,
            @PathVariable int startMonth,
            @PathVariable int startDay,
            @PathVariable int startYear,
            @PathVariable int endMonth,
            @PathVariable int endDay,
            @PathVariable int endYear) {
        List<Tracking> dateTracking = trackingRepo.findAll();
        LocalDateTime startDate = LocalDateTime.of(startYear, startMonth, startDay, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(endYear, endMonth, endDay, 23, 59, 59);
        dateTracking.removeIf(d -> !d.getUserId().equals(userId));
        dateTracking.removeIf(d -> d.getTimestamp().isBefore(startDate) || d.getTimestamp().isAfter(endDate));
        return dateTracking;
    }

    @GetMapping
    @RequestMapping("/byUser/{userId}")
    public List<Tracking> trackingByUser(@PathVariable Long userId) {
        List<Tracking> userTracking = trackingRepo.findAll();
        userTracking.removeIf(u -> !u.getUserId().equals(userId));
        return userTracking;
    }

    @PostMapping
    public ResponseEntity<?> addTracking(@RequestBody Tracking tracking) {
        tracking.setTimestamp(LocalDateTime.now());
        trackingRepo.saveAndFlush(tracking);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
