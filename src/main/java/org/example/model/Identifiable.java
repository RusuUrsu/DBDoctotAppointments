package org.example.model;


import jakarta.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class Identifiable implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Use database auto-increment
    @Column(name = "id", nullable = false, updatable = false)
    protected Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(int andIncrement) {
        this.id = andIncrement;
    }
}
