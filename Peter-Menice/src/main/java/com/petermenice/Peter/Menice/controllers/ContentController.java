package com.petermenice.Peter.Menice.controllers;

import com.petermenice.Peter.Menice.entities.Content;
import com.petermenice.Peter.Menice.repos.ContentRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content")
public class ContentController {
    @Autowired
    ContentRepo contentRepo;

    @GetMapping
    public List<Content> allContent() {
        return contentRepo.findAll();
    }

    @GetMapping
    @RequestMapping("/byPageName/{page}")
    public Content getContentByPageName(@PathVariable String page) {
        return contentRepo.getReferenceByPageName(page);
    }

    @PostMapping
    public ResponseEntity<?> addContent(@RequestBody Content content) {
        content.setPageName(content.getPageName().toLowerCase());
        if (getContentByPageName(content.getPageName()) == null) {
            contentRepo.saveAndFlush(content);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    @RequestMapping("/{page}")
    public ResponseEntity<?> updateContent(@PathVariable String page, @RequestBody Content content) {
        Content existingContent = getContentByPageName(page);
        if (page.equalsIgnoreCase(content.getPageName())) {
            BeanUtils.copyProperties(content, existingContent);
            contentRepo.saveAndFlush(existingContent);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
