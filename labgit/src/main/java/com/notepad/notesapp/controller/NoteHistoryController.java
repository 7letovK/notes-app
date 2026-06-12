package com.notepad.notesapp.controller;

import com.notepad.notesapp.entity.NoteHistory;
import com.notepad.notesapp.repository.NoteHistoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/note-histories")
public class NoteHistoryController {
    
    private final NoteHistoryRepository noteHistoryRepository;
    
    public NoteHistoryController(NoteHistoryRepository noteHistoryRepository) {
        this.noteHistoryRepository = noteHistoryRepository;
    }
    
    @GetMapping
    public List<NoteHistory> getAll() {
        return noteHistoryRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public NoteHistory getById(@PathVariable Long id) {
        return noteHistoryRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "NoteHistory not found"));
    }
    
    @GetMapping("/note/{noteId}")
    public List<NoteHistory> getByNoteId(@PathVariable Long noteId) {
        return noteHistoryRepository.findByNoteId(noteId);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NoteHistory create(@RequestBody NoteHistory noteHistory) {
        return noteHistoryRepository.save(noteHistory);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!noteHistoryRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "NoteHistory not found");
        }
        noteHistoryRepository.deleteById(id);
    }
}