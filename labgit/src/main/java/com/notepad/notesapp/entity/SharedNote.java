package com.notepad.notesapp.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "shared_notes")
public class SharedNote {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String shareToken;
    private boolean isPublic = false;
    private LocalDateTime sharedAt;
    private LocalDateTime expiresAt;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_id", unique = true)
    private Note note;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shared_by_id")
    private User sharedBy;
    
    // Constructors
    public SharedNote() {}
    
    // Getters
    public Long getId() { return id; }
    public String getShareToken() { return shareToken; }
    public boolean isPublic() { return isPublic; }
    public LocalDateTime getSharedAt() { return sharedAt; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public Note getNote() { return note; }
    public User getSharedBy() { return sharedBy; }
    
    // Setters
    public void setId(Long id) { this.id = id; }
    public void setShareToken(String shareToken) { this.shareToken = shareToken; }
    public void setPublic(boolean isPublic) { this.isPublic = isPublic; }
    public void setSharedAt(LocalDateTime sharedAt) { this.sharedAt = sharedAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }
    public void setNote(Note note) { this.note = note; }
    public void setSharedBy(User sharedBy) { this.sharedBy = sharedBy; }
    
    @PrePersist
    protected void onCreate() {
        sharedAt = LocalDateTime.now();
    }
}