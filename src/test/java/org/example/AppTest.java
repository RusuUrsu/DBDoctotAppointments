package org.example;

import org.example.Controller.Controller;
import org.example.Repository.DBRepo;
import org.example.model.*;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.example.Service.Service;
import org.example.util.HibernateUtil.*;
import java.time.LocalDateTime;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    private static SessionFactory sessionFactory;

    private static DBRepo<Patient> patientRepo;
    private static DBRepo<Doctor> doctorRepo;
    private static DBRepo<Appointment> appointmentRepo;
    private static DBRepo<Clinic> clinicRepo;
    private static DBRepo<Specialization> specializationRepo;
    private static DBRepo<Medication> medicationRepo;

    private static Service service;
    private static Controller controller;

    @BeforeAll
    static void setUpSessionFactory() {
        sessionFactory = HibernateUtil.getSessionFactory();
        patientRepo = new DBRepo<>(sessionFactory, Patient.class);
        doctorRepo = new DBRepo<>(sessionFactory, Doctor.class);
        appointmentRepo = new DBRepo<>(sessionFactory, Appointment.class);
        clinicRepo = new DBRepo<>(sessionFactory, Clinic.class);
        specializationRepo = new DBRepo<>(sessionFactory, Specialization.class);
        medicationRepo = new DBRepo<>(sessionFactory, Medication.class);

        service = new Service(appointmentRepo, doctorRepo, patientRepo, clinicRepo, medicationRepo, specializationRepo);
        controller = new Controller(service);
    }

//    @BeforeEach
//    void setUpRepository() {
//        patientRepo = new DBRepo<>(sessionFactory, Patient.class);
//        doctorRepo = new DBRepo<>(sessionFactory, Doctor.class);
//        appointmentRepo = new DBRepo<>(sessionFactory, Appointment.class);
//        clinicRepo = new DBRepo<>(sessionFactory, Clinic.class);
//        specializationRepo = new DBRepo<>(sessionFactory, Specialization.class);
//        medicationRepo = new DBRepo<>(sessionFactory, Medication.class);
//
//        service = new Service(appointmentRepo, doctorRepo, patientRepo, clinicRepo, medicationRepo, specializationRepo);
//        controller = new Controller(service);
//    }

    @Test
    void testOperationsForDoctor() {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();

        // Create and save a new doctor
        Clinic clinic = new Clinic("Medica", "123 Main Street");
        clinicRepo.create(clinic);
        Doctor doctor = new Doctor(clinic, "Maria", "Popescu", "Ginecolog");
        doctorRepo.create(doctor);
//        transaction.commit();

        // Verify the doctor ID
        Integer doctorId = doctor.getId();
        assertNotNull(doctorId, "Doctor ID should not be null");

        // Retrieve and verify the saved doctor
        Doctor retrievedDoctor = doctorRepo.read(doctorId);
        assertNotNull(retrievedDoctor, "Retrieved Doctor should not be null");
        assertEquals("Maria", retrievedDoctor.getFirstName());
        assertEquals("Popescu", retrievedDoctor.getLastName());
        assertEquals("Ginecolog", retrievedDoctor.getSpecialization());

        // Update doctor details
//        transaction = session.beginTransaction();
        Doctor doctorToUpdate = doctorRepo.read(doctorId);
        doctorToUpdate.setFirstName("Diana");
        doctorToUpdate.setSpecialization("Oftalmolog");
        controller.updateDoctor(doctorToUpdate);
//        transaction.commit();

        // Verify updated doctor
        Doctor updatedDoctor = doctorRepo.read(doctorId);
        assertNotNull(updatedDoctor, "Updated Doctor should not be null");
        assertEquals("Diana", updatedDoctor.getFirstName());
        assertEquals("Oftalmolog", updatedDoctor.getSpecialization());

//        session.close();
    }

    @Test
    void testOperationsForPatient() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        // Create and save a new patient
        Patient patient = new Patient("Maria", "Popescu", new ContactInfo("0723456789", "maria.popescu@example.com", "123 Main Street"));
        patientRepo.create(patient);

        // Verify the patient ID
        Integer patientId = patient.getId();
        assertNotNull(patientId, "Patient ID should not be null");

        // Retrieve and verify the saved patient
        Patient retrievedPatient = patientRepo.read(patientId);
        assertNotNull(retrievedPatient, "Retrieved Patient should not be null");
        assertEquals("Maria", retrievedPatient.getFirstName());
        assertEquals("Popescu", retrievedPatient.getLastName());

        // Update patient details
        Patient patientToUpdate = patientRepo.read(patientId);
        patientToUpdate.setLastName("Rusu");
        controller.updatePatient(patientToUpdate);
        transaction.commit();

        // Verify updated patient
        Patient updatedPatient = patientRepo.read(patientId);
        assertNotNull(updatedPatient, "Updated Patient should not be null");
        assertEquals("Rusu", updatedPatient.getLastName());

        session.close();
    }

    @Test
    void testOperations() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        // Create clinic, patient, and doctor
        Clinic clinic = new Clinic("Medica", "123 Main Street");
        clinicRepo.create(clinic);
        Patient patient = new Patient("Maria", "Popescu", new ContactInfo("0723456789", "maria.popescu@example.com", "123 Main Street"));
        patientRepo.create(patient);
        Doctor doctor = new Doctor(clinic, "Maria", "Popescu", "Ginecolog");
        Doctor doctor1 = new Doctor(clinic, "Sanador", "Mihai", "Ginecolog");
        Doctor doctor2 = new Doctor(clinic, "Sanovil", "Miron", "Oftalmolog");


        doctorRepo.create(doctor);
        System.out.println(doctor);
        doctorRepo.create(doctor1);
        System.out.println(doctor1);
        doctorRepo.create(doctor2);


        List<Doctor> filteredDoctors = controller.getDoctorsBySpecialization("Ginecolog");
        //List<Appointment> filteredAppointmentsByDate =  controller.getSortedAppointmentsByDate();

        // Create an appointment
        Appointment appointment = new Appointment(LocalDateTime.now(), patient, doctor, "bla bla");
        appointmentRepo.create(appointment);

        // Verify entities are saved
        assertNotNull(doctor.getId(), "Doctor ID should not be null");
        assertNotNull(doctor1.getId(), "Doctor ID should not be null");
        assertNotNull(doctor2.getId(), "Doctor ID should not be null");

        assertNotNull(patient.getId(), "Patient ID should not be null");
        assertNotNull(appointment.getId(), "Appointment ID should not be null");

        assertNotNull(filteredDoctors, "Filtered doctors list should not be null");
        for (Doctor filteredDoctor : filteredDoctors)
        {
            assertEquals("Ginecolog", filteredDoctor.getSpecialization(), "Filtered doctor should have specialization Ginecolog");
        }

        for (Doctor filteredDoctor : filteredDoctors)
        {
            assertNotEquals("Oftalmolog", filteredDoctor.getSpecialization(), "Filtered doctor should only have specialization Ginecolog");
        }

        session.close();
    }
}
