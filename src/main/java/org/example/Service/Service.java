package org.example.Service;

import org.example.model.*;
import org.example.Repository.IRepository;
import org.example.Repository.IRepository;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Comparator;
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
        //addSomeValues();
    }

    public void addSomeValues() {
        Clinic clinic1 = new Clinic("Nova Clinic", "Str. T. Vladimirescu 19");
        Clinic clinic2 = new Clinic("Medica", "Str. Mircea Eliade 33");
        Clinic clinic3 = new Clinic("Regina Maria", "Str. Eugen Ionescu 32-37");
        clinicRepository.create(clinic1);
        clinicRepository.create(clinic2);
        clinicRepository.create(clinic3);

        Specialization specialization1 = new Specialization("ginecolog", "opens legs");
        Specialization specialization2 = new Specialization("oftalmolog", "opens eyes");
        Specialization specialization3 = new Specialization("dentist", "opens mouths");
        specializationRepository.create(specialization1);
        specializationRepository.create(specialization2);
        specializationRepository.create(specialization3);

//        Doctor doctor1 = new Doctor("Victor", "Rusu", specialization1);
//        Doctor doctor2 = new Doctor("Tudor", "Ivancea", specialization2);
//        Doctor doctor3 = new Doctor("Alex", "Luca", specialization3);
//        addDoctor(doctor1);
//        addDoctor(doctor2);
//        addDoctor(doctor3);

        Patient patient0 = new Patient("Alexandra", "Bercu", new ContactInfo("0725896874","bercualexandra@gmail.com" ,"Strada Narciselor 4"));
        Patient patient1 = new Patient("Ion", "Popescu", new ContactInfo("0723000001", "ion.popescu@gmail.com", "Strada Unirii 10"));
        Patient patient2 = new Patient("Maria", "Ionescu", new ContactInfo("0732000002", "maria.ionescu@gmail.com", "Strada Libertății 5"));
        Patient patient3 = new Patient("George", "Vasilescu", new ContactInfo("0743000003", "george.vasilescu@gmail.com", "Strada Păcii 20"));
        Patient patient4 = new Patient("Ana", "Dumitrescu", new ContactInfo("0753000004", "ana.dumitrescu@gmail.com", "Strada Primăverii 12"));
        Patient patient5 = new Patient("Alex", "Mateescu", new ContactInfo("0763000005", "alex.mateescu@gmail.com", "Bulevardul Florilor 15"));
        addPatient(patient0);
        addPatient(patient1);
        addPatient(patient2);
        addPatient(patient3);
        addPatient(patient4);
        addPatient(patient5);
    }

    public void addAppointment(Appointment appointment) {
        appointmentRepository.create(appointment);
    }

    public void removeAppointment(Appointment appointment) {
        appointmentRepository.delete(appointment.getId());
    }

    public void addClinic(Clinic clinic) {
        clinicRepository.create(clinic);
    }

    public void removeClinic(Clinic clinic) {
        clinicRepository.delete(clinic.getId());
    }

    public void addMedication(Medication medication) {
        medicationRepository.create(medication);
    }
    public void removeMedication(Medication medication) {
        medicationRepository.delete(medication.getId());
    }

    public void addSpecialization(Specialization specialization) {
        specializationRepository.create(specialization);
    }
    public void removeSpecialization(Specialization specialization) {
        specializationRepository.delete(specialization.getId());
    }

    public void updateAppointment(Appointment appointment) {
        appointmentRepository.update(appointment);
    }

    public void updateClinic(Clinic clinic) {
        clinicRepository.update(clinic);
    }

    public void updateMedication(Medication medication) {
        medicationRepository.update(medication);
    }
    public void updateSpecialization(Specialization specialization) {
        specializationRepository.update(specialization);
    }

    public void addDoctor(Doctor doctor) {
        doctorRepository.create(doctor);
    }

    public void removeDoctor(Doctor doctor) {
        doctorRepository.delete(doctor.getId());
    }

    public void addPatient(Patient patient) {
        patientRepository.create(patient);
    }
    public void removePatient(Patient patient) {
        patientRepository.delete(patient.getId());
    }

    public void updateDoctor(Doctor doctor) {
        doctorRepository.update(doctor);
    }
    public void updatePatient(Patient patient) {
        patientRepository.update(patient);
    }

    public Map<Integer, Appointment> getAppointments() {
        return appointmentRepository.getAll();
    }
    public Map<Integer, Clinic> getClinics() {
        return clinicRepository.getAll();
    }

    public Map<Integer, Doctor> getDoctors(){
        return doctorRepository.getAll();
    }

    public Map<Integer, Patient> getPatients() {
        return patientRepository.getAll();
    }
    public Map<Integer, Specialization> getSpecializations() {
        return specializationRepository.getAll();
    }
    public Map<Integer, Medication> getMedications() {
        return medicationRepository.getAll();
    }

    public Clinic getClinicById(int id){
        return clinicRepository.getById(id);
    }

    public Doctor getDoctorById(int id){
        return doctorRepository.getById(id);
    }

    public Patient getPatientById(int id) {
        return patientRepository.getById(id);
    }


    public List<Clinic> findNearbyClinics(String address) {
        // Implement logic to find clinics near the address
        // Placeholder for now:
        return new ArrayList<>(clinicRepository.getAll().values());


    }

    public List<Doctor> findDoctorsBySpecialization(String specializationName) {
        return doctorRepository.getAll().values().stream()
                .filter(doctor -> doctor.getSpecialization().equalsIgnoreCase(specializationName))
                .toList();
    }

    public List<Medication> getMedicationsForPatient(Patient patient) {
        return medicationRepository.getAll().values().stream().toList();
    }

    public boolean validateDateTime(String dateTime) {
        try {
            LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }



    public boolean createAppointment(Patient patient, Doctor doctor, String dateTime, String reason) {
        try {
            LocalDateTime appointmentTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            Appointment appointment = new Appointment(appointmentTime, patient, doctor,  reason);
            addAppointment(appointment); // Add to the repository
            return true;
        } catch (Exception e) {
            System.out.println("Error creating appointment: " + e.getMessage());
            return false;
        }
    }

    public List<Doctor> filterDoctorsBySpecialization(String specializationName) {
        return doctorRepository.getAll().values().stream()
                .filter(doctor -> doctor.getSpecialization().equalsIgnoreCase(specializationName)) // Comparăm numele specializării
                .collect(Collectors.toList());
    }



    public List<Appointment> filterAppointmentsByDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        return appointmentRepository.getAll().values().stream()
                .filter(appointment -> {
                    try {
                        LocalDateTime dateTime = LocalDateTime.parse(appointment.getDateTime(), formatter);
                        return dateTime.toLocalDate().equals(date);
                    } catch (Exception e) {
                        System.out.println("Error parsing date for appointment: " + appointment);
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }




    public List<Appointment> sortAppointmentsByDoctorAndDate() {
        return appointmentRepository.getAll().values().stream()
                .sorted(Comparator
                        .comparing((Appointment a) -> a.getDoctor().getLastName())
                        .thenComparing(Appointment::getDateTime)
                )
                .collect(Collectors.toList());
    }

    public List<Appointment> sortAppointmentsByDate(boolean ascending) {
        return appointmentRepository.getAll().values().stream()
                .sorted(ascending
                        ? Comparator.comparing(Appointment::getDateTime)
                        : Comparator.comparing(Appointment::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    public List<Appointment> filterFutureAppointments() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        return appointmentRepository.getAll().values().stream()
                .filter(appointment -> {
                    LocalDateTime appointmentDateTime = LocalDateTime.parse(appointment.getDateTime(), formatter);
                    return appointmentDateTime.isAfter(now);
                })
                .sorted(Comparator.comparing(appointment -> LocalDateTime.parse(appointment.getDateTime(), formatter)))
                .collect(Collectors.toList());
    }


}

