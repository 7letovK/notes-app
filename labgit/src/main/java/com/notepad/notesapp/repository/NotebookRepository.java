package com.notepad.notesapp.repository;

import com.notepad.notesapp.entity.Notebook;
import com.notepad.notesapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotebookRepository extends JpaRepository<Notebook, Long> {
    List<Notebook> findByUser(User user);
    List<Notebook> findByUserId(Long userId);
}
