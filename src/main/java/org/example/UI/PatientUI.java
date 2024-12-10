package org.example.UI;

import org.example.Controller.Controller;
import org.example.model.*;

import java.util.List;
import java.util.Scanner;

public class PatientUI {
    private final Controller controller;

    public PatientUI(Controller controller) {
        this.controller = controller;
    }

    public void start() {
        System.out.println("Welcome to Patient UI");
        Patient patient = login();
        if (patient == null) {
            System.out.println("Invalid login. Exiting.");
            return;
        }

        boolean active = true;
        Scanner scanner = new Scanner(System.in);

        while (active) {
            System.out.println("\nPlease select an option:");
            System.out.println("1. See all clinics");
            System.out.println("2. See all doctors");
            System.out.println("3. Make an appointment");
            System.out.println("4. See nearby clinics");
            System.out.println("5. Search doctors by specialization");
            System.out.println("6. See prescribed medications");
            System.out.println("7. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> showAllClinics();
                case 2 -> showAllDoctors();
                case 3 -> makeAppointment(patient);
                case 4 -> findNearbyClinics();
                case 5 -> searchDoctorsBySpecialization();
                case 6 -> showPrescribedMedications(patient);
                case 7 -> active = false;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private Patient login() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your Patient ID: ");
        int id = sc.nextInt();
        sc.nextLine(); // Consume newline

        Patient patient = controller.getPatientById(id);
        if (patient == null) {
            System.out.println("Invalid Patient ID.");
        }
        else {
            System.out.println("Login successful. Welcome " + patient.getFirstName() + " " + patient.getLastName() + "!");
        }
        return patient;
    }

    private void showAllClinics() {
        System.out.println("List of all clinics:");
        List<Clinic> clinics = controller.getAllClinics();
        for (Clinic clinic : clinics) {
            System.out.println(clinic);
        }
    }

    private void showAllDoctors() {
        System.out.println("List of all doctors:");
        List<Doctor> doctors = controller.getAllDoctors();
        for (Doctor doctor : doctors) {
            System.out.println(doctor);
        }
    }

    private void makeAppointment(Patient patient) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the Doctor ID for the appointment:");
        int doctorId = sc.nextInt();
        sc.nextLine(); // Consume newline

        Doctor doctor = controller.getDoctorById(doctorId);
        if (doctor == null) {
            System.out.println("Invalid Doctor ID. Appointment not created.");
            return;
        }

        System.out.println("Enter appointment date and time (e.g., 2024-11-20T15:00):");
        String dateTimeInput = sc.nextLine();

        if (!controller.validateDateTime(dateTimeInput)) {
            System.out.println("Invalid date and time format. Please try again.");
            return;
        }

        System.out.println("Enter the reason for the appointment:");
        String reason = sc.nextLine();

        if (controller.createAppointment(patient, doctor, dateTimeInput, reason)) {
            System.out.println("Appointment successfully created.");
        } else {
            System.out.println("Failed to create appointment. Please check the details and try again.");
        }
    }

    private void findNearbyClinics() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your address:");
        String patientAddress = sc.nextLine();

        List<Clinic> nearbyClinics = controller.getNearbyClinics(patientAddress);
        System.out.println("Nearby Clinics:");
        for (Clinic clinic : nearbyClinics) {
            System.out.println(clinic);
        }
    }

    private void searchDoctorsBySpecialization() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter specialization name:");
        String specializationName = sc.nextLine();

        List<Doctor> doctors = controller.getDoctorsBySpecialization(specializationName);
        if (doctors.isEmpty()) {
            System.out.println("No doctors found with the specialization: " + specializationName);
        } else {
            System.out.println("Doctors with specialization '" + specializationName + "':");
            for (Doctor doctor : doctors) {
                System.out.println(doctor);
            }
        }
    }

    private void showPrescribedMedications(Patient patient) {
        List<Medication> medications = controller.getPrescribedMedications(patient);
        if (medications.isEmpty()) {
            System.out.println("You have no prescribed medications.");
        } else {
            System.out.println("Your prescribed medications:");
            for (Medication medication : medications) {
                System.out.println(medication);
            }
        }
    }
}

