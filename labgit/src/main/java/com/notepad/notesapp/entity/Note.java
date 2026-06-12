package com.notepad.notesapp.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "notes")
public class Note {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String content;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    private boolean archived = false;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notebook_id")
    private Notebook notebook;
    
    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NoteHistory> histories = new ArrayList<>();
    
    @OneToOne(mappedBy = "note", cascade = CascadeType.ALL)
    private SharedNote sharedNote;
    
    @ManyToMany
    @JoinTable(
        name = "note_tags",
        joinColumns = @JoinColumn(name = "note_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();
    
    // Constructors
    public Note() {}
    
    // Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public boolean isArchived() { return archived; }
    public User getUser() { return user; }
    public Notebook getNotebook() { return notebook; }
    public List<NoteHistory> getHistories() { return histories; }
    public SharedNote getSharedNote() { return sharedNote; }
    public List<Tag> getTags() { return tags; }  // НОВЫЙ GETTER
    
    // Setters
    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public void setArchived(boolean archived) { this.archived = archived; }
    public void setUser(User user) { this.user = user; }
    public void setNotebook(Notebook notebook) { this.notebook = notebook; }
    public void setHistories(List<NoteHistory> histories) { this.histories = histories; }
    public void setSharedNote(SharedNote sharedNote) { this.sharedNote = sharedNote; }
    public void setTags(List<Tag> tags) { this.tags = tags; }  // НОВЫЙ SETTER
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}