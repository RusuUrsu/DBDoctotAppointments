package org.example.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "doctors")
public class Doctor extends Person implements Serializable {

    @ManyToOne
    @JoinColumn(name = "specialization_id", nullable = true)
    private Specialization specialization;

    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    public Doctor() {
        // Default constructor required by JPA
    }

    public Doctor(String firstName, String lastName, Specialization specialization) {
        super(firstName, lastName);
        this.specialization = specialization;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
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
                " | Specialization: " + specialization.getName() + " - Description: " + specialization.getDescription() +
                " | ID: " + getId();
    }
}
