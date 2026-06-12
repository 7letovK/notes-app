package com.notepad.notesapp.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "notebooks")
public class Notebook {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    @OneToMany(mappedBy = "notebook", cascade = CascadeType.ALL)
    private List<Note> notes = new ArrayList<>();
    
    // Constructors
    public Notebook() {}
    
    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public User getUser() { return user; }
    public List<Note> getNotes() { return notes; }
    
    // Setters
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setUser(User user) { this.user = user; }
    public void setNotes(List<Note> notes) { this.notes = notes; }
}