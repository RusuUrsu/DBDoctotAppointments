package org.example.Service;

import org.example.model.*;
import org.example.Repository.IRepository;
import org.example.Exceptions.*; // Import your custom exceptions

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Service {
    private final IRepository<Appointment> appointmentRepository;
    private final IRepository<Doctor> doctorRepository;
    private final IRepository<Patient> patientRepository;
    private final IRepository<Clinic> clinicRepository;
    private final IRepository<Medication> medicationRepository;
    private final IRepository<Specialization> specializationRepository;

    public Service(IRepository<Appointment> appointmentRepository, IRepository<Doctor> doctorRepository, IRepository<Patient> patientRepository, IRepository<Clinic> clinicRepository, IRepository<Medication> medicationRepository, IRepository<Specialization> specializationRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.clinicRepository = clinicRepository;
        this.medicationRepository = medicationRepository;
        this.specializationRepository = specializationRepository;
    }

    public void addAppointment(Appointment appointment) {
        try {
            appointmentRepository.create(appointment);
        } catch (Exception e) {
            throw new DatabaseException("Failed to add appointment: " + e.getMessage());
        }
    }

    public void removeAppointment(Appointment appointment) {
        if (appointment == null) {
            throw new EntityNotFoundException("Appointment not found.");
        }
        try {
            appointmentRepository.delete(appointment.getId());
        } catch (Exception e) {
            throw new DatabaseException("Failed to remove appointment: " + e.getMessage());
        }
    }

    public void addClinic(Clinic clinic) {
        if (clinic == null) {
            throw new ValidationException("Clinic cannot be null.");
        }
        try {
            clinicRepository.create(clinic);
        } catch (Exception e) {
            throw new DatabaseException("Failed to add clinic: " + e.getMessage());
        }
    }

    public void removeClinic(Clinic clinic) {
        if (clinic == null) {
            throw new EntityNotFoundException("Clinic not found.");
        }
        try {
            clinicRepository.delete(clinic.getId());
        } catch (Exception e) {
            throw new DatabaseException("Failed to remove clinic: " + e.getMessage());
        }
    }

    public void addMedication(Medication medication) {
        if (medication == null) {
            throw new ValidationException("Medication cannot be null.");
        }
        try {
            medicationRepository.create(medication);
        } catch (Exception e) {
            throw new DatabaseException("Failed to add medication: " + e.getMessage());
        }
    }

    public void removeMedication(Medication medication) {
        if (medication == null) {
            throw new EntityNotFoundException("Medication not found.");
        }
        try {
            medicationRepository.delete(medication.getId());
        } catch (Exception e) {
            throw new DatabaseException("Failed to remove medication: " + e.getMessage());
        }
    }

    public void updateAppointment(Appointment appointment) {
        if (appointment == null) {
            throw new ValidationException("Appointment cannot be null.");
        }
        try {
            appointmentRepository.update(appointment);
        } catch (Exception e) {
            throw new DatabaseException("Failed to update appointment: " + e.getMessage());
        }
    }

    public Clinic getClinicById(int id) {
        try {
            Clinic clinic = clinicRepository.getById(id);
            if (clinic == null) {
                throw new EntityNotFoundException("Clinic with ID " + id + " not found.");
            }
            return clinic;
        } catch (Exception e) {
            throw new DatabaseException("Failed to retrieve clinic: " + e.getMessage());
        }
    }

    public Doctor getDoctorById(int id) {
        try {
            Doctor doctor = doctorRepository.getById(id);
            if (doctor == null) {
                throw new EntityNotFoundException("Doctor with ID " + id + " not found.");
            }
            return doctor;
        } catch (Exception e) {
            throw new DatabaseException("Failed to retrieve doctor: " + e.getMessage());
        }
    }

    public boolean createAppointment(Patient patient, Doctor doctor, String dateTime, String reason) {
        if (patient == null || doctor == null || dateTime == null || reason == null) {
            throw new ValidationException("Invalid input for creating an appointment.");
        }
        try {
            LocalDateTime appointmentTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            Appointment appointment = new Appointment(appointmentTime, patient, doctor, reason);
            addAppointment(appointment);
            return true;
        } catch (DateTimeParseException e) {
            throw new ValidationException("Invalid date format: " + dateTime);
        } catch (Exception e) {
            throw new BusinessLogicException("Error creating appointment: " + e.getMessage());
        }
    }

    public List<Appointment> filterAppointmentsByDate(LocalDate date) {
        if (date == null) {
            throw new ValidationException("Date cannot be null.");
        }
        try {
            return appointmentRepository.getAll().values().stream()
                    .filter(appointment -> LocalDateTime.parse(appointment.getDateTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME).toLocalDate().equals(date))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessLogicException("Error filtering appointments: " + e.getMessage());
        }
    }

    public List<Appointment> sortAppointmentsByDate(boolean ascending) {
        try {
            return appointmentRepository.getAll().values().stream()
                    .sorted(ascending
                            ? Comparator.comparing(Appointment::getDateTime)
                            : Comparator.comparing(Appointment::getDateTime).reversed())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessLogicException("Error sorting appointments: " + e.getMessage());
        }
    }

    public void updateClinic(Clinic clinic) {
        if (clinic == null) {
            throw new ValidationException("Clinic cannot be null.");
        }
        try {
            clinicRepository.update(clinic);
        } catch (Exception e) {
            throw new DatabaseException("Failed to update clinic: " + e.getMessage());
        }
    }

    public void updateMedication(Medication medication) {
        if (medication == null) {
            throw new ValidationException("Medication cannot be null.");
        }
        try {
            medicationRepository.update(medication);
        } catch (Exception e) {
            throw new DatabaseException("Failed to update medication: " + e.getMessage());
        }
    }

    public void addDoctor(Doctor doctor) {
        if (doctor == null) {
            throw new ValidationException("Doctor cannot be null.");
        }
        try {
            doctorRepository.create(doctor);
        } catch (Exception e) {
            throw new DatabaseException("Failed to add doctor: " + e.getMessage());
        }
    }

    public void removeDoctor(Doctor doctor) {
        if (doctor == null) {
            throw new EntityNotFoundException("Doctor not found.");
        }
        try {
            doctorRepository.delete(doctor.getId());
        } catch (Exception e) {
            throw new DatabaseException("Failed to remove doctor: " + e.getMessage());
        }
    }

    public void addPatient(Patient patient) {
        if (patient == null) {
            throw new ValidationException("Patient cannot be null.");
        }
        try {
            patientRepository.create(patient);
        } catch (Exception e) {
            throw new DatabaseException("Failed to add patient: " + e.getMessage());
        }
    }

    public void removePatient(Patient patient) {
        if (patient == null) {
            throw new EntityNotFoundException("Patient not found.");
        }
        try {
            patientRepository.delete(patient.getId());
        } catch (Exception e) {
            throw new DatabaseException("Failed to remove patient: " + e.getMessage());
        }
    }

    public void updateDoctor(Doctor doctor) {
        if (doctor == null) {
            throw new ValidationException("Doctor cannot be null.");
        }
        try {
            doctorRepository.update(doctor);
        } catch (Exception e) {
            throw new DatabaseException("Failed to update doctor: " + e.getMessage());
        }
    }

    public void updatePatient(Patient patient) {
        if (patient == null) {
            throw new ValidationException("Patient cannot be null.");
        }
        try {
            patientRepository.update(patient);
        } catch (Exception e) {
            throw new DatabaseException("Failed to update patient: " + e.getMessage());
        }
    }

    public Map<Integer, Appointment> getAppointments() {
        try {
            return appointmentRepository.getAll();
        } catch (Exception e) {
            throw new DatabaseException("Failed to retrieve appointments: " + e.getMessage());
        }
    }

    public Map<Integer, Clinic> getClinics() {
        try {
            return clinicRepository.getAll();
        } catch (Exception e) {
            throw new DatabaseException("Failed to retrieve clinics: " + e.getMessage());
        }
    }

    public Map<Integer, Doctor> getDoctors() {
        try {
            return doctorRepository.getAll();
        } catch (Exception e) {
            throw new DatabaseException("Failed to retrieve doctors: " + e.getMessage());
        }
    }

    public Map<Integer, Patient> getPatients() {
        try {
            return patientRepository.getAll();
        } catch (Exception e) {
            throw new DatabaseException("Failed to retrieve patients: " + e.getMessage());
        }
    }

    public Map<Integer, Specialization> getSpecializations() {
        try {
            return specializationRepository.getAll();
        } catch (Exception e) {
            throw new DatabaseException("Failed to retrieve specializations: " + e.getMessage());
        }
    }

    public Map<Integer, Medication> getMedications() {
        try {
            return medicationRepository.getAll();
        } catch (Exception e) {
            throw new DatabaseException("Failed to retrieve medications: " + e.getMessage());
        }
    }

    public List<Clinic> findNearbyClinics(String address) {
        if (address == null || address.isEmpty()) {
            throw new ValidationException("Address cannot be null or empty.");
        }
        try {
            return new ArrayList<>(clinicRepository.getAll().values());
        } catch (Exception e) {
            throw new BusinessLogicException("Failed to find nearby clinics: " + e.getMessage());
        }
    }

//    public List<Doctor> findDoctorsBySpecialization(String specializationName) {
//        if (specializationName == null || specializationName.isEmpty()) {
//            throw new ValidationException("Specialization name cannot be null or empty.");
//        }
//        try {
//            return doctorRepository.getAll().values().stream()
//                    .filter(doctor -> doctor.getSpecializations().stream()
//                            .anyMatch(spec -> spec.getName().equalsIgnoreCase(specializationName)))
//                    .collect(Collectors.toList());
//        } catch (Exception e) {
//            throw new BusinessLogicException("Failed to find doctors by specialization: " + e.getMessage());
//        }
//    }

    public List<Appointment> getDoctorAppointments(int doctorId) {
        try {
            return appointmentRepository.getAll().values().stream()
                    .filter(appointment -> appointment.getDoctor().getId() == doctorId)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DatabaseException("Failed to retrieve appointments for doctor with ID " + doctorId + ": " + e.getMessage());
        }
    }

    public List<Appointment> getPatientAppointments(int patientId) {
        try {
            return appointmentRepository.getAll().values().stream()
                    .filter(appointment -> appointment.getPatient().getId() == patientId)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DatabaseException("Failed to retrieve appointments for patient with ID " + patientId + ": " + e.getMessage());
        }
    }

    public Patient getPatientById(int id) {
        try {
            Patient patient = patientRepository.getById(id);
            if (patient == null) {
                throw new EntityNotFoundException("Patient with ID " + id + " not found.");
            }
            return patient;
        } catch (Exception e) {
            throw new DatabaseException("Failed to retrieve patient: " + e.getMessage());
        }
    }

    public List<Doctor> findDoctorsBySpecialization(String specializationName) {
        if (specializationName == null || specializationName.isEmpty()) {
            throw new ValidationException("Specialization name cannot be null or empty.");
        }
        try {
            List<Doctor> filteredDoctors = doctorRepository.getAll().values().stream()
                    .filter(doctor -> doctor.getSpecialization().equalsIgnoreCase(specializationName)) // Compare specialization name
                    .collect(Collectors.toList());

            if (filteredDoctors.isEmpty()) {
                throw new EntityNotFoundException("No doctors found with specialization: " + specializationName);
            }

            return filteredDoctors;
        } catch (Exception e) {
            throw new BusinessLogicException("Error filtering doctors by specialization: " + e.getMessage());
        }
    }

    public List<Appointment> sortAppointmentsByDoctorAndDate() {
        try {
            List<Appointment> sortedAppointments = appointmentRepository.getAll().values().stream()
                    .sorted(Comparator
                            .comparing((Appointment a) -> a.getDoctor().getLastName())
                            .thenComparing(Appointment::getDateTime)
                    )
                    .collect(Collectors.toList());

            if (sortedAppointments.isEmpty()) {
                throw new EntityNotFoundException("No appointments found to sort.");
            }

            return sortedAppointments;
        } catch (Exception e) {
            throw new BusinessLogicException("Error sorting appointments by doctor and date: " + e.getMessage());
        }
    }

    public List<Appointment> filterFutureAppointments() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        try {
            List<Appointment> futureAppointments = appointmentRepository.getAll().values().stream()
                    .filter(appointment -> {
                        try {
                            LocalDateTime appointmentDateTime = LocalDateTime.parse(appointment.getDateTime(), formatter);
                            return appointmentDateTime.isAfter(now);
                        } catch (DateTimeParseException e) {
                            throw new ValidationException("Invalid date format for appointment: " + appointment.getDateTime());
                        }
                    })
                    .sorted(Comparator.comparing(appointment -> LocalDateTime.parse(appointment.getDateTime(), formatter)))
                    .collect(Collectors.toList());

            if (futureAppointments.isEmpty()) {
                throw new EntityNotFoundException("No future appointments found.");
            }

            return futureAppointments;
        } catch (ValidationException | EntityNotFoundException e) {
            throw e; // Re-throw known exceptions to provide meaningful error messages.
        } catch (Exception e) {
            throw new BusinessLogicException("Error filtering future appointments: " + e.getMessage());
        }
    }

    public boolean validateDateTime(String dateTime) {
        try {
            LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            return true;
        } catch (DateTimeParseException e) {
            throw new ValidationException("Date Time is incorrect formatted");
//            return false;
        }
    }






}
