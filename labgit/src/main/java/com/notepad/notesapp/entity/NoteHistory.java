package com.notepad.notesapp.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "note_histories")
public class NoteHistory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String oldTitle;
    
    @Column(columnDefinition = "TEXT")
    private String oldContent;
    
    private LocalDateTime changedAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_id")
    private Note note;
    
    // Constructors
    public NoteHistory() {}
    
    // Getters
    public Long getId() { return id; }
    public String getOldTitle() { return oldTitle; }
    public String getOldContent() { return oldContent; }
    public LocalDateTime getChangedAt() { return changedAt; }
    public Note getNote() { return note; }
    
    // Setters
    public void setId(Long id) { this.id = id; }
    public void setOldTitle(String oldTitle) { this.oldTitle = oldTitle; }
    public void setOldContent(String oldContent) { this.oldContent = oldContent; }
    public void setChangedAt(LocalDateTime changedAt) { this.changedAt = changedAt; }
    public void setNote(Note note) { this.note = note; }
    
    @PrePersist
    protected void onCreate() {
        changedAt = LocalDateTime.now();
    }
}