package com.notepad.notesapp.controller;

import com.notepad.notesapp.entity.Attachment;
import com.notepad.notesapp.repository.AttachmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/attachments")
public class AttachmentController {
    
    private final AttachmentRepository attachmentRepository;
    
    // Конструктор
    public AttachmentController(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }
    
    @GetMapping
    public List<Attachment> getAll() {
        return attachmentRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Attachment getById(@PathVariable Long id) {
        return attachmentRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Attachment not found"));
    }
    
    @GetMapping("/note/{noteId}")
    public List<Attachment> getByNoteId(@PathVariable Long noteId) {
        return attachmentRepository.findByNoteId(noteId);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Attachment create(@RequestBody Attachment attachment) {
        return attachmentRepository.save(attachment);
    }
    
    @PutMapping("/{id}")
    public Attachment update(@PathVariable Long id, @RequestBody Attachment attachment) {
        if (!attachmentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attachment not found");
        }
        attachment.setId(id);
        return attachmentRepository.save(attachment);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!attachmentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attachment not found");
        }
        attachmentRepository.deleteById(id);
    }
}