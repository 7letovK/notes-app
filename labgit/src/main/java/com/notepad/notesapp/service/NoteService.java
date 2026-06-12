package com.notepad.notesapp.service;

import com.notepad.notesapp.entity.Note;
import com.notepad.notesapp.entity.User;
import com.notepad.notesapp.repository.NoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    public List<Note> findByUser(User user) {
        return noteRepository.findByUserId(user.getId());
    }

    public Note findById(Long id) {
        return noteRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));
    }

    public Note create(Note note, User user) {
        note.setUser(user);
        note.setArchived(false);
        return noteRepository.save(note);
    }

    public Note update(Long id, Note noteDetails, User user) {
        Note note = findById(id);
        
        if (!note.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only update your own notes");
        }
        
        note.setTitle(noteDetails.getTitle());
        note.setContent(noteDetails.getContent());
        return noteRepository.save(note);
    }

    public void delete(Long id, User user) {
        Note note = findById(id);
        
        if (!note.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only delete your own notes");
        }
        
        noteRepository.deleteById(id);
    }

    public Note archive(Long id, User user) {
        Note note = findById(id);
        
        if (!note.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only archive your own notes");
        }
        
        note.setArchived(true);
        return noteRepository.save(note);
    }

    public Note unarchive(Long id, User user) {
        Note note = findById(id);
        
        if (!note.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only unarchive your own notes");
        }
        
        note.setArchived(false);
        return noteRepository.save(note);
    }
}