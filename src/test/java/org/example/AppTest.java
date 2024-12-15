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


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doAnswer;

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
//        patientRepo = new DBRepo<>(sessionFactory, Patient.class);
//        doctorRepo = new DBRepo<>(sessionFactory, Doctor.class);
//        appointmentRepo = new DBRepo<>(sessionFactory, Appointment.class);
//        clinicRepo = new DBRepo<>(sessionFactory, Clinic.class);
//        specializationRepo = new DBRepo<>(sessionFactory, Specialization.class);
//        medicationRepo = new DBRepo<>(sessionFactory, Medication.class);
//
//        service = new Service(appointmentRepo, doctorRepo, patientRepo, clinicRepo, medicationRepo, specializationRepo);
//        controller = new Controller(service);
    }


    @BeforeEach
    void setUpRepository() {
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

//    @Test
//    void testOperationsForDoctor() {
////        Session session = sessionFactory.openSession();
////        Transaction transaction = session.beginTransaction();
//
//        // Create and save a new doctor
//        Clinic clinic = new Clinic("Medica", "123 Main Street");
//        clinicRepo.create(clinic);
//        Doctor doctor = new Doctor(clinic, "Maria", "Popescu", "Ginecolog");
//        System.out.println(doctor);
//        doctorRepo.create(doctor);
//        System.out.println(doctor);
////        transaction.commit();
//
//        // Verify the doctor ID
//        Integer doctorId = doctor.getId();
//        assertNotNull(doctorId, "Doctor ID should not be null");
//
//        // Retrieve and verify the saved doctor
//        System.out.println(doctorRepo.getAll());
//        System.out.println(doctor);
//        Doctor retrievedDoctor = doctorRepo.read(doctorId);
//        assertNotNull(retrievedDoctor, "Retrieved Doctor should not be null");
//        assertEquals("Maria", retrievedDoctor.getFirstName());
//        assertEquals("Popescu", retrievedDoctor.getLastName());
//        assertEquals("Ginecolog", retrievedDoctor.getSpecialization());
//
//        // Update doctor details
////        transaction = session.beginTransaction();
//        Doctor doctorToUpdate = doctorRepo.read(doctorId);
//        doctorToUpdate.setFirstName("Diana");
//        doctorToUpdate.setSpecialization("Oftalmolog");
//        controller.updateDoctor(doctorToUpdate);
////        transaction.commit();
//
//        // Verify updated doctor
//        Doctor updatedDoctor = doctorRepo.read(doctorId);
//        assertNotNull(updatedDoctor, "Updated Doctor should not be null");
//        assertEquals("Diana", updatedDoctor.getFirstName());
//        assertEquals("Oftalmolog", updatedDoctor.getSpecialization());
//
////        session.close();
//    }

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

    void testGetAppointmentsFromDoctor() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Clinic clinic = new Clinic("Medica", "123 Main Street");
        clinicRepo.create(clinic);
        Doctor doctor = new Doctor(clinic, "Maria", "Popescu", "Ginecolog");
        Doctor doctor1 = new Doctor(clinic, "Sanador", "Mihai", "Ginecolog");
        Doctor doctor2 = new Doctor(clinic, "Sanovil", "Miron", "Oftalmolog");
        doctorRepo.create(doctor);
        doctorRepo.create(doctor1);
        doctorRepo.create(doctor2);
        Patient patient = new Patient("Maria", "Popescu", new ContactInfo("0723456789", "maria.popescu@example.com", "123 Main Street"));
        Patient patient1 = new Patient("Darius", "Pop", new ContactInfo("0744322611","darius.pop03@gmail.com", "Primaverii nr 23"));
        patientRepo.create(patient);
        patientRepo.create(patient1);
        Appointment appointment = new Appointment(LocalDateTime.now(), patient, doctor, "belly ache");
        Appointment appointment1 = new Appointment(LocalDateTime.now(), patient, doctor1, "follow up");
        Appointment appointment2 = new Appointment(LocalDateTime.now(),patient1, doctor2, "problems");
        appointmentRepo.create(appointment);
        appointmentRepo.create(appointment1);
        appointmentRepo.create(appointment2);
        List<Appointment> filteredAppointments = controller.getAppointmentsFromDoctor(doctor);

        assertNotNull(filteredAppointments, "Filtered appointments list should not be null");
        for (Appointment filteredAppointment : filteredAppointments) {
            assertEquals(doctor.getId(), filteredAppointment.getDoctor().getId(),
                    "Each appointment should belong to the specified doctor");

            session.close();
        }
    }

    @Test
    void testGetPrescribedMedications() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Patient patient1 = new Patient("Maria", "Popescu", new ContactInfo("0723456789", "maria.popescu@example.com", "123 Main Street"));
        Patient patient2 = new Patient("Ion", "Pop", new ContactInfo("0744322611", "ion.pop@gmail.com", "Primaverii nr 23"));
        patientRepo.create(patient1);
        patientRepo.create(patient2);
        Medication medication1 = new Medication("Paracetamol", "For pain", patient1);
        Medication medication2 = new Medication("Ibuprofen", "For inflammation", patient1);
        Medication medication3 = new Medication("Aspirin", "For heart health", patient2);
        medicationRepo.create(medication1);
        medicationRepo.create(medication2);
        medicationRepo.create(medication3);
        List<Medication> filteredMedications = controller.getPrescribedMedications(patient1);
        assertNotNull(filteredMedications, "Filtered medications list should not be null");
        assertEquals(2, filteredMedications.size(), "Patient1 should have exactly 2 medications");
        for (Medication medication : filteredMedications) {
            assertEquals(patient1.getId(), medication.getPatient().getId(),
                    "Medication should be prescribed to the specified patient");
        }
        for (Medication medication : filteredMedications) {
            assertNotEquals(patient2.getId(), medication.getPatient().getId(),
                    "Filtered medications should not include medications for other patients");
        }
        transaction.commit();
        session.close();


    }


    @Test
    void testGetSortedAppointmentsByDate_Ascending() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            Appointment appointment1 = new Appointment(LocalDateTime.of(2024, 12, 10, 10, 0), null, null, "Checkup");
            Appointment appointment2 = new Appointment(LocalDateTime.of(2024, 12, 12, 12, 0), null, null, "Consultation");
            Appointment appointment3 = new Appointment(LocalDateTime.of(2024, 12, 11, 14, 0), null, null, "Follow-up");

            session.save(appointment1);
            session.save(appointment2);
            session.save(appointment3);

            transaction.commit();
            Transaction queryTransaction = session.beginTransaction();
            List<Appointment> sortedAppointments = controller.getSortedAppointmentsByDate(true);
            assertNotNull(sortedAppointments, "The returned list of appointments should not be null");
            assertEquals(3, sortedAppointments.size(), "The number of appointments should match");
            assertTrue(sortedAppointments.get(0).getDate().isBefore(sortedAppointments.get(1).getDate()),
                    "Appointments should be sorted in ascending order");
            assertTrue(sortedAppointments.get(1).getDate().isBefore(sortedAppointments.get(2).getDate()),
                    "Appointments should be sorted in ascending order");
            queryTransaction.commit();
        } finally {
            session.close();
        }

    }


    @Test
    void testUpdateAppointmentDateTime_Success() {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Appointment appointment = new Appointment(LocalDateTime.of(2024, 12, 15, 10, 0), null, null, "Checkup");
        appointmentRepo.create(appointment);

        String newDateTime = "2024-12-16 14:30";

        boolean result = controller.updateAppointmentDateTime(appointment, newDateTime);

        transaction.commit();

        String expectedDateTime = "2024-12-16T14:30";
        String actualDateTime = appointment.getDateTime(); // Returnează String

        assertTrue(result, "The method should return true for a successful update");
        assertEquals(expectedDateTime, actualDateTime,
                "The appointment's date and time should be updated correctly");

        session.close();
    }




    @Test
    void testGetFutureAppointments() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Appointment appointment1 = new Appointment(
                LocalDateTime.of(2024, 12, 22, 10, 0), null, null, "Checkup");
        Appointment appointment2 = new Appointment(
                LocalDateTime.of(2025, 12, 15, 10, 0), null, null, "Consultation");

        session.save(appointment1);
        session.save(appointment2);

        transaction.commit();

        List<Appointment> futureAppointments = controller.getFutureAppointments();

        assertNotNull(futureAppointments, "The returned list of future appointments should not be null");
        assertEquals(2, futureAppointments.size(), "The list should contain all future appointments");

         boolean hasAppointment1 = futureAppointments.stream()
                .anyMatch(a -> a.getDateTime().equals(appointment1.getDateTime())
                        && "Checkup".equals(a.getDescription()));

        boolean hasAppointment2 = futureAppointments.stream()
                .anyMatch(a -> a.getDateTime().equals(appointment2.getDateTime())
                        && "Consultation".equals(a.getDescription()));

        assertTrue(hasAppointment1, "The list should include appointment1");
        assertTrue(hasAppointment2, "The list should include appointment2");

        session.close();
    }

}
