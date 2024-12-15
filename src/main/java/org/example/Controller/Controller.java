package org.example.Controller;

import org.example.Exceptions.ValidationException;
import org.example.model.*;
import org.example.Service.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Controller {
    private final Service service;
    public Controller(Service service) {
        this.service = service;
    }

    public Service getService() {
        return service;
    }

    public List<Appointment> searchAppointment(int patientID, int doctorID) {
        Patient patient = service.getPatientById(patientID);
        Doctor doctor = service.getDoctorById(doctorID);
        List<Appointment> appointments = new ArrayList<>(service.getAppointments().values().stream().toList());
        appointments.removeIf(appointment -> patient.equals(appointment.getPatient()) && doctor.equals(appointment.getDoctor()));
        return appointments;
    }


    public void addClinic(Clinic clinic) {
        service.addClinic(clinic);
    }

    public void addDoctor(Doctor doctor) {
        service.addDoctor(doctor);
    }

    public Clinic searchClinicById(int id) {
        return service.getClinicById(id);
    }

    public Doctor getDoctorById(int id) {
        return service.getDoctorById(id);
    }


    public List<Appointment> getAppointmentsFromDoctor(Doctor doctor) {
        List<Appointment> appointments = new java.util.ArrayList<>(service.getAppointments().values().stream().toList());
        appointments.removeIf(appointment -> appointment.getDoctor().getId() != doctor.getId());
        return appointments;
    }

    public List<Doctor> getAllDoctors(){
        return service.getDoctors().values().stream().toList();
    }

    public void createAppointment() {}

    public void updateClinic(Clinic clinic) {
        service.updateClinic(clinic);
    }

    // Update doctor
    public void updateDoctor(Doctor doctor) {
        service.updateDoctor(doctor);
    }

    // Update patient
    public void updatePatient(Patient patient) {
        service.updatePatient(patient);
    }

    // Update appointment
    public void updateAppointment(Appointment appointment) {
        service.updateAppointment(appointment);
    }

    // Add medication
    public void addMedication(Medication medication) {
        service.addMedication(medication);
    }

    public boolean updateAppointmentDateTime(Appointment appointment, String newDateTime) {
        if (newDateTime.isBlank()) {
            throw new ValidationException("The new date and time cannot be blank.");
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime parsedDateTime = LocalDateTime.parse(newDateTime, formatter);
            appointment.setDateTime(parsedDateTime);
            service.updateAppointment(appointment);
            return true;
        } catch (DateTimeParseException e) {
            throw new ValidationException("Invalid date and time format. Use 'yyyy-MM-dd HH:mm'.");
        }
    }

    // Retrieve a patient by ID
    public Patient getPatientById(int id) {
        return service.getPatientById(id);
    }

    // Retrieve nearby clinics based on the patient's address
    public List<Clinic> getNearbyClinics(String address) {
        return service.findNearbyClinics(address);
    }

    // Retrieve doctors by specialization
    public List<Doctor> getDoctorsBySpecialization(String specializationName) {
        return service.findDoctorsBySpecialization(specializationName);
    }

    // Retrieve prescribed medications for a patient
    public List<Medication> getPrescribedMedications(Patient patient) {
        List<Medication> medications = new java.util.ArrayList<>(service.getMedications().values().stream().toList());
        medications.removeIf(appointment -> !Objects.equals(appointment.getPatient().getId(), patient.getId()));
        return medications;
    }

    // Validate date and time format
    public boolean validateDateTime(String dateTime) {
        return service.validateDateTime(dateTime);
    }

    // Create an appointment
    public boolean createAppointment(Patient patient, Doctor doctor, String dateTime, String reason) {
        return service.createAppointment(patient, doctor, dateTime, reason);
    }

    public List<Clinic> getAllClinics() {
        return service.getClinics().values().stream().toList();
    }

    public List<Doctor> getDoctorsSortedBySpecialization(String specializationName) {
        return service.filterDoctorsBySpecialization(specializationName);
    }



    public List<Appointment> getAppointmentsByDate(LocalDate date) {
        return service.filterAppointmentsByDate(date);
    }



    public List<Appointment> getSortedAppointmentsByDate(boolean ascending) {
        return service.sortAppointmentsByDate(ascending);
    }

    public List<Appointment> getSortedAppointmentsByDoctorAndDate() {
        return service.sortAppointmentsByDoctorAndDate();
    }

    public List<Appointment> getFutureAppointments() {
        return service.filterFutureAppointments();
    }
}
