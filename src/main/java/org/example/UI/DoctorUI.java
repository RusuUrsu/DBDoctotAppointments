package org.example.UI;

import org.example.Controller.Controller;
import org.example.Exceptions.ValidationException;
import org.example.model.*;

import java.util.List;
import java.util.Scanner;

public class DoctorUI {

    private final Controller controller;

    public DoctorUI(Controller controller) {
        this.controller = controller;
    }

    public void start() {
        System.out.println("Welcome to DoctorUI: ");
        Doctor doctor = login();
        boolean active = doctor != null;
        Scanner scanner = new Scanner(System.in);

        while (active) {
            System.out.println("\nPlease select an option:");
            System.out.println("1. View and Edit Appointments");
            System.out.println("2. View Patients and Prescribe Medication");
            System.out.println("3. View and Edit Profile");
            System.out.println("4. Exit");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1 -> manageAppointments(doctor);
                case 2 -> managePatients(doctor);
                case 3 -> viewProfile(doctor);
                case 4 -> active = false;
                default -> System.out.println("Invalid option");
            }
        }
    }

    public Doctor login() {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("Enter your doctor ID to log in:");
            if (!sc.hasNextInt()) {
                sc.next();
                throw new ValidationException("Invalid ID format. Please enter a numeric value.");
            }
            int id = Integer.parseInt(sc.nextLine());
            Doctor doctor = controller.getDoctorById(id);
            if (doctor != null) {
                System.out.println("Login successful. Welcome, Dr. " + doctor.getFirstName() + " " + doctor.getLastName() + "!");
                return doctor;
            } else {
                throw new ValidationException("Invalid doctor ID. Please try again.");
            }
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private void manageAppointments(Doctor doctor) {
        Scanner scanner = new Scanner(System.in);
        List<Appointment> appointmentList = controller.getAppointmentsFromDoctor(doctor);

        if (appointmentList.isEmpty()) {
            System.out.println("You have no appointments.");
            return;
        }

        System.out.println("Here are your appointments:");
        for (int i = 0; i < appointmentList.size(); i++) {
            System.out.println((i + 1) + ". " + appointmentList.get(i));
        }

        System.out.println("Would you like to edit an appointment? (yes/no)");
        String choice = scanner.nextLine();
        if (choice.equalsIgnoreCase("yes")) {
            System.out.println("Enter the number of the appointment you want to edit:");
            if (!scanner.hasNextInt()) {
                scanner.next();
                System.out.println("Invalid input. Please enter a valid number.");
                return;
            }

            int appointmentIndex = scanner.nextInt() - 1;
            scanner.nextLine();

            if (appointmentIndex >= 0 && appointmentIndex < appointmentList.size()) {
                Appointment appointment = appointmentList.get(appointmentIndex);

                System.out.println("Enter new date and time (format: yyyy-MM-dd HH:mm, current: " + appointment.getDateTime() + "):");
                String newDateTime = scanner.nextLine();

                try {
                    boolean updated = controller.updateAppointmentDateTime(appointment, newDateTime);
                    if (updated) {
                        System.out.println("Appointment updated successfully.");
                    } else {
                        System.out.println("No changes were made to the appointment.");
                    }
                } catch (ValidationException e) {
                    System.out.println("Validation error: " + e.getMessage());
                }
            } else {
                System.out.println("Invalid appointment number.");
            }
        }

    }

    private void managePatients(Doctor doctor) {
        Scanner scanner = new Scanner(System.in);
        List<Appointment> appointmentList = controller.getAppointmentsFromDoctor(doctor);

        if (appointmentList.isEmpty()) {
            System.out.println("No patients have appointments with you.");
            return;
        }

        System.out.println("Here are the patients who have appointments with you:");
        for (Appointment appointment : appointmentList) {
            System.out.println(appointment.getPatient());
        }

        System.out.println("Would you like to prescribe medication to a patient? (yes/no)");
        String choice = scanner.nextLine();

        if (choice.equalsIgnoreCase("yes")) {
            try {
                System.out.println("Enter the ID of the patient to prescribe medication:");
                if (!scanner.hasNextInt()) {
                    scanner.next(); // Consumăm inputul invalid
                    throw new ValidationException("Invalid input. Please enter a numeric ID.");
                }

                int patientId = scanner.nextInt();
                scanner.nextLine();

                Patient patient = controller.getService().getPatientById(patientId);
                if (patient == null) {
                    throw new ValidationException("No patient found with the given ID.");
                }

                System.out.println("Enter medication name:");
                String medicationName = scanner.nextLine();

                System.out.println("Enter dosage instructions:");
                String dosage = scanner.nextLine();

                Medication medication = new Medication(medicationName, dosage, patient);
                controller.addMedication(medication);
                System.out.println("Medication prescribed successfully to "
                        + patient.getFirstName() + " " + patient.getLastName() + ".");

            } catch (ValidationException e) {
                System.out.println("Validation error: " + e.getMessage());
            }
        }

    }

    private void viewProfile(Doctor doctor) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nYour Profile:");
        System.out.println("First Name: " + doctor.getFirstName());
        System.out.println("Last Name: " + doctor.getLastName());
        System.out.println("Specialization: " + doctor.getSpecialization());

        System.out.println("\nWould you like to edit your profile? (yes/no)");
        String choice = scanner.nextLine();
        if (choice.equalsIgnoreCase("yes")) {
            System.out.println("Enter new first name (leave blank to keep current):");
            String newFirstName = scanner.nextLine();
            if (!newFirstName.isBlank()) doctor.setFirstName(newFirstName);

            System.out.println("Enter new last name (leave blank to keep current):");
            String newLastName = scanner.nextLine();
            if (!newLastName.isBlank()) doctor.setLastName(newLastName);

            controller.updateDoctor(doctor);
            System.out.println("Profile updated successfully.");
        }
    }
}

