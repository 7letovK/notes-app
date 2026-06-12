package com.notepad.notesapp.repository;

import com.notepad.notesapp.entity.Note;
import com.notepad.notesapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByUser(User user);
    List<Note> findByUserAndArchived(User user, boolean archived);
    List<Note> findByUserId(Long userId);
}