package com.notepad.notesapp.repository;

import com.notepad.notesapp.entity.NoteHistory;
import com.notepad.notesapp.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NoteHistoryRepository extends JpaRepository<NoteHistory, Long> {
    List<NoteHistory> findByNote(Note note);
    List<NoteHistory> findByNoteId(Long noteId);
}
