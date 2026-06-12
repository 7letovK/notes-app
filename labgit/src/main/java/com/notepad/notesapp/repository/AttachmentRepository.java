package com.notepad.notesapp.repository;

import com.notepad.notesapp.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    List<Attachment> findByNoteId(Long noteId);
}