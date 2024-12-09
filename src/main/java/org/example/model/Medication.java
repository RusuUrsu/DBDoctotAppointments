package org.example.model;


import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "medications")
public class Medication extends Identifiable implements Serializable {

    @Column(name = "medication_name", nullable = true)
    private String medicationName;

    @Column(name = "dosage", nullable = true)
    private String dosage;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = true)
    private Patient patient;

    public Medication() {
        // Default constructor required by JPA
    }

    public Medication(String name, String dosage, Patient patient) {
        super();
        this.medicationName = name;
        this.dosage = dosage;
        this.patient = patient;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }



    @Override
    public String toString() {
        return "Medication:   Name: " + medicationName +
                " | Dosage: " + dosage + " | Patient: " + patient.getFirstName() + " " + patient.getLastName();
    }
}

