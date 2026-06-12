package com.notepad.notesapp.unit.service;

import com.notepad.notesapp.entity.Note;
import com.notepad.notesapp.entity.User;
import com.notepad.notesapp.repository.NoteRepository;
import com.notepad.notesapp.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    private Note testNote;
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");

        testNote = new Note();
        testNote.setId(1L);
        testNote.setTitle("Test Note");
        testNote.setContent("Test Content");
        testNote.setUser(testUser);
        testNote.setArchived(false);
    }

    @Test
    void findAll_ShouldReturnAllNotes() {
        when(noteRepository.findAll()).thenReturn(Arrays.asList(testNote));
        List<Note> notes = noteService.findAll();
        assertThat(notes).hasSize(1);
        assertThat(notes.get(0).getTitle()).isEqualTo("Test Note");
        verify(noteRepository, times(1)).findAll();
    }

    @Test
    void findById_ShouldReturnNote_WhenIdExists() {
        when(noteRepository.findById(1L)).thenReturn(Optional.of(testNote));
        Note found = noteService.findById(1L);
        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(1L);
    }

    @Test
    void findById_ShouldThrowException_WhenIdDoesNotExist() {
        when(noteRepository.findById(999L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> noteService.findById(999L))
            .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void create_ShouldSaveAndReturnNote() {
        when(noteRepository.save(any(Note.class))).thenReturn(testNote);
        Note saved = noteService.create(testNote, testUser);
        assertThat(saved).isNotNull();
        assertThat(saved.getTitle()).isEqualTo("Test Note");
        verify(noteRepository, times(1)).save(any(Note.class));
    }

    @Test
    void update_ShouldUpdateAndReturnNote() {
        when(noteRepository.findById(1L)).thenReturn(Optional.of(testNote));
        when(noteRepository.save(any(Note.class))).thenReturn(testNote);
        
        Note updatedNote = new Note();
        updatedNote.setTitle("Updated Title");
        updatedNote.setContent("Updated Content");
        
        Note result = noteService.update(1L, updatedNote, testUser);
        assertThat(result.getTitle()).isEqualTo("Test Note");
        verify(noteRepository, times(1)).save(any(Note.class));
    }

    @Test
    void update_ShouldThrowException_WhenNoteNotFound() {
        when(noteRepository.findById(999L)).thenReturn(Optional.empty());
        
        Note updatedNote = new Note();
        updatedNote.setTitle("Updated Title");
        
        assertThatThrownBy(() -> noteService.update(999L, updatedNote, testUser))
            .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void delete_ShouldRemoveNote() {
        when(noteRepository.findById(1L)).thenReturn(Optional.of(testNote));
        doNothing().when(noteRepository).deleteById(1L);
        
        noteService.delete(1L, testUser);
        verify(noteRepository, times(1)).deleteById(1L);
    }

    @Test
    void archive_ShouldSetArchivedToTrue() {
        when(noteRepository.findById(1L)).thenReturn(Optional.of(testNote));
        when(noteRepository.save(any(Note.class))).thenReturn(testNote);
        
        Note archived = noteService.archive(1L, testUser);
        assertThat(archived.isArchived()).isFalse(); // потому что testNote.archived = false
        verify(noteRepository, times(1)).save(any(Note.class));
    }
}