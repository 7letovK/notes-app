package com.notepad.notesapp.repository;

import com.notepad.notesapp.entity.SharedNote;
import com.notepad.notesapp.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SharedNoteRepository extends JpaRepository<SharedNote, Long> {
    Optional<SharedNote> findByShareToken(String shareToken);
    Optional<SharedNote> findByNote(Note note);
}