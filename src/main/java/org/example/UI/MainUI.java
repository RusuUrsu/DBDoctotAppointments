package org.example.UI;
import org.example.Controller.Controller;
import org.example.Exceptions.ValidationException;

import java.awt.*;
import java.util.Scanner;
import java.util.List;
import java.util.Scanner;

public class MainUI {
    private final Controller controller;
    private final AdminUI adminUI;
    private final DoctorUI doctorUI;
    private final PatientUI patientUI;

    public MainUI(Controller controller, AdminUI adminUI, DoctorUI doctorUI, PatientUI patientUI) {
        this.controller = controller;
        this.adminUI = adminUI;
        this.doctorUI = doctorUI;
        this.patientUI = patientUI;
    }

    public static <T> void displayList(List<T> items) {
        if (items == null || items.isEmpty()) {
            System.out.println("The list is empty.");
            return;
        }

        for (T item : items) {
            System.out.println(item.toString());
        }
    }

    public void start(){
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        try {
            System.out.println("Welcome to Doctor Appointments Management System");
            System.out.println("1. Login as Admin");
            System.out.println("2. Login as Doctor");
            System.out.println("3. Login as Patient");
            System.out.println("Anything else - exit program");

            if (!scanner.hasNextInt()) {
                scanner.next();
                throw new ValidationException("Invalid input! Please enter a valid numeric option.");
            }

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    adminUI.start();
                    break;
                case 2:
                    doctorUI.start();
                    break;
                case 3:
                    patientUI.start();
                    break;
                default:
                    running = false;
                    break;
            }
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        } finally {
            scanner.nextLine();
        }
    }

}

