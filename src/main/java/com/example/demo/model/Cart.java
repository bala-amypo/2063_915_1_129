package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    [cite_start]private Long id; [cite: 82]
    [cite_start]private Long userId; [cite: 83]
    [cite_start]private Boolean active = true; [cite: 86]

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}