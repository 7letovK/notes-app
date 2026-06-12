package com.notepad.notesapp.controller;

import com.notepad.notesapp.entity.Note;
import com.notepad.notesapp.repository.NoteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {
    
    private final NoteRepository noteRepository;
    
    public NoteController(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }
    
    @GetMapping
    public List<Note> getAll() {
        return noteRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Note getById(@PathVariable Long id) {
        return noteRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));
    }
    
    @GetMapping("/user/{userId}")
    public List<Note> getByUserId(@PathVariable Long userId) {
        return noteRepository.findByUserId(userId);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Note create(@RequestBody Note note) {
        return noteRepository.save(note);
    }
    
    @PutMapping("/{id}")
    public Note update(@PathVariable Long id, @RequestBody Note note) {
        if (!noteRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found");
        }
        note.setId(id);
        return noteRepository.save(note);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!noteRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found");
        }
        noteRepository.deleteById(id);
    }
    
    @PatchMapping("/{id}/archive")
    public Note archive(@PathVariable Long id) {
        Note note = noteRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));
        note.setArchived(true);
        return noteRepository.save(note);
    }
    
    @PatchMapping("/{id}/unarchive")
    public Note unarchive(@PathVariable Long id) {
        Note note = noteRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));
        note.setArchived(false);
        return noteRepository.save(note);
    }
}