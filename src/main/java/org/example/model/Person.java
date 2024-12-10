package org.example.model;

import jakarta.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class Person extends Identifiable implements Serializable {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                "| lastName='" + lastName + '\'' +
                '}';
    }

    public Person(String firstName, String lastName) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person() {
        // Default constructor required by JPA
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
