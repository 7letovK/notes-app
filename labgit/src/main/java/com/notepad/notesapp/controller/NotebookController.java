package com.notepad.notesapp.controller;

import com.notepad.notesapp.entity.Notebook;
import com.notepad.notesapp.repository.NotebookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/notebooks")
public class NotebookController {
    
    private final NotebookRepository notebookRepository;
    
    public NotebookController(NotebookRepository notebookRepository) {
        this.notebookRepository = notebookRepository;
    }
    
    @GetMapping
    public List<Notebook> getAll() {
        return notebookRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Notebook getById(@PathVariable Long id) {
        return notebookRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Notebook not found"));
    }
    
    @GetMapping("/user/{userId}")
    public List<Notebook> getByUserId(@PathVariable Long userId) {
        return notebookRepository.findByUserId(userId);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Notebook create(@RequestBody Notebook notebook) {
        return notebookRepository.save(notebook);
    }
    
    @PutMapping("/{id}")
    public Notebook update(@PathVariable Long id, @RequestBody Notebook notebook) {
        if (!notebookRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Notebook not found");
        }
        notebook.setId(id);
        return notebookRepository.save(notebook);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!notebookRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Notebook not found");
        }
        notebookRepository.deleteById(id);
    }
}