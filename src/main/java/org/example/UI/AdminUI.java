package org.example.UI;

import org.example.Controller.Controller;
import org.example.model.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class AdminUI {
    private final Controller controller;

    public AdminUI(Controller controller) {
        this.controller = controller;
    }

    public void start() {
        while (true) {
            System.out.println("\nWelcome to Admin UI");
            System.out.println("Choose an option:");
            System.out.println("1. Add Clinic");
            System.out.println("2. Add Doctor to a Clinic");
            System.out.println("3. Show All (Appointments/Doctors/Clinics/Patients)");
            System.out.println("4. Update Clinic Details");
            System.out.println("5. Update Doctor Details");
            System.out.println("6. Update Patient Details");
            System.out.println("7. Filter doctors by specialization");
            System.out.println("8. Filter appointments by date");
            System.out.println("9. Sort Doctor by Name and date");
            System.out.println("10. Sort Appointment ");
            System.out.println("11. Display future appointments");
            System.out.println("12. Search appointments after doctor and patient");
            System.out.println("0. Exit");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> addClinic();
                case 2 -> addDoctorToClinic();
                case 3 -> showAll();
                case 4 -> updateClinicDetails();
                case 5 -> updateDoctorDetails();
                case 6 -> updatePatientDetails();
                case 7 -> filterdoctorsbyspecialization();
                case 8 -> filterappointmentbydate();
                case 9 -> SortedAppointmentsByDoctorAndDate();
                case 10 -> SortedAppointmentsByDate();
                case 11 -> displayFutureAppointments();
                case 12 -> searchAppointmentsByDoctorAndPatient();
                case 0 -> {
                    System.out.println("Exiting Admin UI...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void searchAppointmentsByDoctorAndPatient() {
        System.out.println("Enter patient ID");
        Scanner scanner = new Scanner(System.in);
        int patientID = scanner.nextInt();
        System.out.println("Enter doctor ID");
        int doctorID = scanner.nextInt();
        controller.searchAppointment(patientID, doctorID).forEach(System.out::println);

    }

    // Add a new clinic
    public void addClinic() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Clinic name: ");
        String name = sc.nextLine();
        System.out.println("Enter Clinic address: ");
        String address = sc.nextLine();
        Clinic clinic = new Clinic(name, address);
        controller.addClinic(clinic);
        System.out.println("Clinic added successfully.");
    }

    // Add a doctor to a specific clinic
    public void addDoctorToClinic() {
        Scanner sc = new Scanner(System.in);

        // Enter doctor details
        System.out.println("Enter Doctor first name: ");
        String firstName = sc.nextLine();
        System.out.println("Enter Doctor last name: ");
        String lastName = sc.nextLine();
        System.out.println("Enter specialization name: ");
        String specializationName = sc.nextLine();
        System.out.println("Enter clinic ID: ");
        int clinicID = Integer.parseInt(sc.nextLine());
        Clinic docClinic = controller.searchClinicById(clinicID);
        Doctor doctor = new Doctor(docClinic,firstName, lastName, specializationName);
        controller.addDoctor(doctor);

        // Display clinics and select one
        System.out.println("Available Clinics:");
        controller.getService().getClinics().values().forEach(System.out::println);
        System.out.println("Enter Clinic ID to assign the doctor: ");
        int clinicId = sc.nextInt();
        Clinic clinic = controller.searchClinicById(clinicId);

        if (clinic != null) {
            clinic.addDoctor(doctor);
            System.out.println("Doctor added to the clinic successfully.");
        } else {
            System.out.println("Invalid Clinic ID. Operation failed.");
        }
    }

    // Show all objects (appointments, doctors, clinics, patients)
    public void showAll() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose a category to display:");
        System.out.println("1. Appointments");
        System.out.println("2. Doctors");
        System.out.println("3. Clinics");
        System.out.println("4. Patients");

        int choice = sc.nextInt();
        switch (choice) {
            case 1 -> controller.getService().getAppointments().values().forEach(System.out::println);
            case 2 -> controller.getService().getDoctors().values().forEach(System.out::println);
            case 3 -> controller.getService().getClinics().values().forEach(System.out::println);
            case 4 -> controller.getService().getPatients().values().forEach(System.out::println);
            default -> System.out.println("Invalid choice.");
        }
    }

    // Update clinic details
    public void updateClinicDetails() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Available Clinics:");
        controller.getService().getClinics().values().forEach(System.out::println);

        System.out.println("Enter Clinic ID to update: ");
        int clinicId = sc.nextInt();
        sc.nextLine(); // Consume newline

        Clinic clinic = controller.searchClinicById(clinicId);
        if (clinic != null) {
            System.out.println("Enter new Clinic name (leave blank to keep current): ");
            String newName = sc.nextLine();
            System.out.println("Enter new Clinic address (leave blank to keep current): ");
            String newAddress = sc.nextLine();

            if (!newName.isBlank()) clinic.setName(newName);
            if (!newAddress.isBlank()) clinic.setAddress(newAddress);

            controller.updateClinic(clinic);
            System.out.println("Clinic details updated successfully.");
        } else {
            System.out.println("Invalid Clinic ID.");
        }
    }

    // Update doctor details
    public void updateDoctorDetails() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Available Doctors:");
        controller.getAllDoctors().forEach(System.out::println);

        System.out.println("Enter Doctor ID to update: ");
        int doctorId = sc.nextInt();
        sc.nextLine(); // Consume newline

        Doctor doctor = controller.getDoctorById(doctorId);
        if (doctor != null) {
            System.out.println("Enter new first name (leave blank to keep current): ");
            String newFirstName = sc.nextLine();
            System.out.println("Enter new last name (leave blank to keep current): ");
            String newLastName = sc.nextLine();

            if (!newFirstName.isBlank()) doctor.setFirstName(newFirstName);
            if (!newLastName.isBlank()) doctor.setLastName(newLastName);

            controller.updateDoctor(doctor);
            System.out.println("Doctor details updated successfully.");
        } else {
            System.out.println("Invalid Doctor ID.");
        }
    }

    // Update patient details
    public void updatePatientDetails() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Available Patients:");
        controller.getService().getPatients().values().forEach(System.out::println);

        System.out.println("Enter Patient ID to update: ");
        int patientId = sc.nextInt();
        sc.nextLine(); // Consume newline

        Patient patient = controller.getService().getPatientById(patientId);
        if (patient != null) {
            System.out.println("Enter new first name (leave blank to keep current): ");
            String newFirstName = sc.nextLine();
            System.out.println("Enter new last name (leave blank to keep current): ");
            String newLastName = sc.nextLine();

            if (!newFirstName.isBlank()) patient.setFirstName(newFirstName);
            if (!newLastName.isBlank()) patient.setLastName(newLastName);

            controller.updatePatient(patient);
            System.out.println("Patient details updated successfully.");
        } else {
            System.out.println("Invalid Patient ID.");
        }
    }

    private void filterdoctorsbyspecialization() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter specialization name: ");
        String specializationName = sc.nextLine();
        List<Doctor> doctors = controller.getDoctorsBySpecialization(specializationName);
        if (doctors.isEmpty()) {
            System.out.println("No doctors found with specialization: " + specializationName);
        } else {
            System.out.println("Doctors with specialization '" + specializationName + "':");
            doctors.forEach(System.out::println);
        }
    }

    private void filterappointmentbydate() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter date(format: yyyy-MM-dd): ");
        String dateInput = scanner.nextLine();
        try {
            LocalDate date = LocalDate.parse(dateInput);
            List<Appointment> appointments = controller. getAppointmentsByDate(date);
            if (appointments.isEmpty()) {
                System.out.println("No appointments found for the date: " + date);
            } else {
                System.out.println("Appointments on " + date + ":");
                appointments.forEach(System.out::println);
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
        }

    }

    private void SortedAppointmentsByDoctorAndDate() {

        List<Appointment> sortedAppointments = controller.getSortedAppointmentsByDoctorAndDate();


        if (sortedAppointments.isEmpty()) {
            System.out.println("No appointments found.");
        } else {
            System.out.println("Appointments sorted by doctor and date:");
            sortedAppointments.forEach(appointment -> {
                System.out.println("Doctor: " + appointment.getDoctor().getLastName() + ", " + appointment.getDoctor().getFirstName() +
                        " | Date: " + appointment.getDateTime() +
                        " | Patient: " + appointment.getPatient().getLastName() + ", " + appointment.getPatient().getFirstName());
            });
        }
    }

    private void SortedAppointmentsByDate() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter sorting order (1 for ascending, 2 for descending): ");
        int choice = sc.nextInt();

        boolean ascending = (choice == 1);

        List<Appointment> sortedAppointments = controller.getSortedAppointmentsByDate(ascending);

        if (sortedAppointments.isEmpty()) {
            System.out.println("No appointments found.");
        } else {
            System.out.println("Appointments sorted by date:");
            sortedAppointments.forEach(System.out::println);
        }
    }

    private void displayFutureAppointments() {
        List<Appointment> futureAppointments = controller.getFutureAppointments();

        if (futureAppointments.isEmpty()) {
            System.out.println("No future appointments found.");
        } else {
            System.out.println("Future appointments:");
            futureAppointments.forEach(System.out::println);
        }
    }



}

