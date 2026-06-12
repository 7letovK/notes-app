package com.notepad.notesapp.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    private String role = "ROLE_USER";
    
    private boolean enabled = true;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Note> notes = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Notebook> notebooks = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Tag> tags = new ArrayList<>();
    
    @OneToMany(mappedBy = "sharedBy", cascade = CascadeType.ALL)
    private List<SharedNote> sharedNotes = new ArrayList<>();
    
    // Constructors
    public User() {}
    
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    
    // Getters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public boolean isEnabled() { return enabled; }
    public List<Note> getNotes() { return notes; }
    public List<Notebook> getNotebooks() { return notebooks; }
    public List<Tag> getTags() { return tags; }
    public List<SharedNote> getSharedNotes() { return sharedNotes; }
    
    // Setters
    public void setId(Long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public void setNotes(List<Note> notes) { this.notes = notes; }
    public void setNotebooks(List<Notebook> notebooks) { this.notebooks = notebooks; }
    public void setTags(List<Tag> tags) { this.tags = tags; }
    public void setSharedNotes(List<SharedNote> sharedNotes) { this.sharedNotes = sharedNotes; }
}