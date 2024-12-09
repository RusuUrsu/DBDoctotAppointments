package org.example.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "patients")
public class Patient extends Person implements Serializable {

    @Embedded
    private ContactInfo contactInfo;

    public Patient() {
        // Default constructor required by JPA
    }

    public Patient(String firstName, String lastName, ContactInfo contactInfo) {
        super(firstName, lastName);
        this.contactInfo = contactInfo;
    }

    public ContactInfo getContactInfo() {
        return this.contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    @Override
    public String toString() {
        return "Patient: " +
                " First name: " + this.getFirstName() +
                " | Last name: " + getLastName() +
                contactInfo +
                " | id: " + id;
    }
}
