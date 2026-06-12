package com.notepad.notesapp.controller;

import com.notepad.notesapp.entity.Reminder;
import com.notepad.notesapp.repository.ReminderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/reminders")
public class ReminderController {
    
    private final ReminderRepository reminderRepository;
    
    public ReminderController(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }
    
    @GetMapping
    public List<Reminder> getAll() {
        return reminderRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Reminder getById(@PathVariable Long id) {
        return reminderRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reminder not found"));
    }
    
    @GetMapping("/user/{userId}")
    public List<Reminder> getByUserId(@PathVariable Long userId) {
        return reminderRepository.findByUserId(userId);
    }
    
    @GetMapping("/note/{noteId}")
    public List<Reminder> getByNoteId(@PathVariable Long noteId) {
        return reminderRepository.findByNoteId(noteId);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reminder create(@RequestBody Reminder reminder) {
        return reminderRepository.save(reminder);
    }
    
    @PutMapping("/{id}")
    public Reminder update(@PathVariable Long id, @RequestBody Reminder reminder) {
        if (!reminderRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reminder not found");
        }
        reminder.setId(id);
        return reminderRepository.save(reminder);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!reminderRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reminder not found");
        }
        reminderRepository.deleteById(id);
    }
    
    @PatchMapping("/{id}/notify")
    public Reminder markNotified(@PathVariable Long id) {
        Reminder reminder = reminderRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reminder not found"));
        reminder.setNotified(true);
        return reminderRepository.save(reminder);
    }
}