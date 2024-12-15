package org.example.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment extends Identifiable implements Serializable {

    @Column(name = "date_time", nullable = true)
    private LocalDateTime dateTime;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = true)
    @JoinColumn(name = "patient_id", nullable = true)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = true)
    private Doctor doctor;

    @Column(name = "reason", length = 500, nullable = true)
    private String reason;

    public Appointment(LocalDateTime dateTime, Patient patient, Doctor doctor, String reason) {
        super();
        this.dateTime = dateTime;
        this.patient = patient;
        this.doctor = doctor;
        this.reason = reason;
    }

    public Appointment() {
        // Default constructor required by JPA
    }

    @Override
    public String toString() {
        return "Appointment: " +
                "date: " + dateTime +
                " | patient=" + patient.getFirstName() + " " + patient.getLastName() +
                " | doctor: " + doctor.getFirstName() + " " + doctor.getLastName() +
                " | reason='" + reason + '\'';
    }

    public String getDateTime() {
        return this.dateTime.toString();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getReason() {
        return this.reason;
    }

    public LocalDateTime getDate() {
        return dateTime;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getDescription() {
        return reason;
    }
}
