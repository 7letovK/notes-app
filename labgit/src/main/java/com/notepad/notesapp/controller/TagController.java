package com.notepad.notesapp.controller;

import com.notepad.notesapp.entity.Tag;
import com.notepad.notesapp.repository.TagRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {
    
    private final TagRepository tagRepository;
    
    public TagController(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }
    
    @GetMapping
    public List<Tag> getAll() {
        return tagRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Tag getById(@PathVariable Long id) {
        return tagRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag not found"));
    }
    
    @GetMapping("/user/{userId}")
    public List<Tag> getByUserId(@PathVariable Long userId) {
        return tagRepository.findByUserId(userId);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tag create(@RequestBody Tag tag) {
        return tagRepository.save(tag);
    }
    
    @PutMapping("/{id}")
    public Tag update(@PathVariable Long id, @RequestBody Tag tag) {
        if (!tagRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag not found");
        }
        tag.setId(id);
        return tagRepository.save(tag);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!tagRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag not found");
        }
        tagRepository.deleteById(id);
    }
}