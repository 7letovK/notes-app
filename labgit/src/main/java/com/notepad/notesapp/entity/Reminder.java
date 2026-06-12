package com.notepad.notesapp.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reminders")
public class Reminder {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    private LocalDateTime remindAt;
    private boolean notified = false;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_id")
    private Note note;
    
    // Constructors
    public Reminder() {}
    
    // Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public LocalDateTime getRemindAt() { return remindAt; }
    public boolean isNotified() { return notified; }
    public User getUser() { return user; }
    public Note getNote() { return note; }
    
    // Setters
    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setRemindAt(LocalDateTime remindAt) { this.remindAt = remindAt; }
    public void setNotified(boolean notified) { this.notified = notified; }
    public void setUser(User user) { this.user = user; }
    public void setNote(Note note) { this.note = note; }
}