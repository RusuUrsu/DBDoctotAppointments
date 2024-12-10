package org.example.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "doctors")
public class Doctor extends Person implements Serializable {

    @Column(name = "specialization", nullable = true)
    private String specialization;

    @ManyToOne
    @JoinColumn(name = "clinic_id", nullable = true)
    private Clinic clinic;

    public Doctor() {
        // Default constructor required by JPA
    }

    public Doctor(Clinic clinic, String firstName, String lastName, String specialization) {
        super(firstName, lastName);
        this.specialization = specialization;
        this.clinic = clinic;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    @Override
    public String toString() {
        return "Doctor: " +
                "First Name: " + getFirstName() +
                " | Last Name: " + getLastName() +
                " | Specialization: " + specialization +
                " | ID: " + getId() +
                " | Clinic: " + clinic.getName();

    }
}
