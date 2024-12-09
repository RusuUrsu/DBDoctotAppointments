package org.example.model;


import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "specializations")
public class Specialization extends Identifiable implements Serializable {

    @Column(name = "name", nullable = true)
    private String name;

    @Column(name = "description", nullable = true)
    private String description;

    public Specialization() {
        // Default constructor required by JPA
    }

    public Specialization(String name, String description) {
        super();
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Specialization: ID: " + this.getId() + " | Name: " + name +
                " | Description: " + description;
    }
}

