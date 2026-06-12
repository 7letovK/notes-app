package com.notepad.notesapp.controller;

import com.notepad.notesapp.entity.SharedNote;
import com.notepad.notesapp.repository.SharedNoteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/shared-notes")
public class SharedNoteController {
    
    private final SharedNoteRepository sharedNoteRepository;
    
    public SharedNoteController(SharedNoteRepository sharedNoteRepository) {
        this.sharedNoteRepository = sharedNoteRepository;
    }
    
    @GetMapping
    public List<SharedNote> getAll() {
        return sharedNoteRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public SharedNote getById(@PathVariable Long id) {
        return sharedNoteRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "SharedNote not found"));
    }
    
    @GetMapping("/token/{token}")
    public SharedNote getByToken(@PathVariable String token) {
        return sharedNoteRepository.findByShareToken(token)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "SharedNote not found"));
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SharedNote create(@RequestBody SharedNote sharedNote) {
        if (sharedNote.getShareToken() == null) {
            sharedNote.setShareToken(UUID.randomUUID().toString());
        }
        return sharedNoteRepository.save(sharedNote);
    }
    
    @PutMapping("/{id}")
    public SharedNote update(@PathVariable Long id, @RequestBody SharedNote sharedNote) {
        if (!sharedNoteRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "SharedNote not found");
        }
        sharedNote.setId(id);
        return sharedNoteRepository.save(sharedNote);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!sharedNoteRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "SharedNote not found");
        }
        sharedNoteRepository.deleteById(id);
    }
}