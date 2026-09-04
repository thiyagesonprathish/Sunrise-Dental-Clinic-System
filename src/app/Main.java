package app;

import model.Appointment;
import model.Bill;
import model.User;
import repository.AppointmentRepository;
import service.AppointmentService;
import service.BillingService;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Hardcoded staff accounts for login (FR1)
        List<User> users = new ArrayList<>();
        users.add(new User("staff01", "pass123"));
        users.add(new User("admin", "admin123"));

        // --- LOGIN ---
        boolean loggedIn = false;
        int attempts = 0;

        while (!loggedIn && attempts < 3) {
            System.out.println("===== Sunrise Dental Clinic - Login =====");
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            for (User u : users) {
                if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                    loggedIn = true;
                    break;
                }
            }

            if (!loggedIn) {
                attempts++;
                System.out.println("Login failed. Please check your credentials.\n");
            }
        }

        if (!loggedIn) {
            System.out.println("Too many failed attempts. Exiting.");
            return;
        }

        System.out.println("Login successful!\n");

        // --- SET UP SERVICES ---
        AppointmentRepository repository = new AppointmentRepository();
        AppointmentService appointmentService = new AppointmentService(repository);
        BillingService billingService = new BillingService();

        // --- MAIN MENU LOOP ---
        boolean running = true;
        while (running) {
            System.out.println("\n===== MAIN MENU =====");
            System.out.println("1. Register New Appointment");
            System.out.println("2. Display Appointment Details");
            System.out.println("3. Calculate and Print Bill");
            System.out.println("4. Help");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Patient Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Address: ");
                    String address = scanner.nextLine();
                    System.out.print("Contact Number: ");
                    String contact = scanner.nextLine();
                    System.out.print("Dentist Name: ");
                    String dentist = scanner.nextLine();
                    System.out.print("Treatment Type: ");
                    String treatment = scanner.nextLine();
                    System.out.print("Appointment Date (YYYY-MM-DD): ");
                    String date = scanner.nextLine();
                    System.out.print("Appointment Time (HH:MM): ");
                    String time = scanner.nextLine();

                    String result = appointmentService.registerAppointment(
                            name, address, contact, dentist, treatment, date, time);
                    System.out.println(result);
                    break;

                case "2":
                    System.out.print("Enter Appointment Number: ");
                    String searchNumber = scanner.nextLine();
                    Appointment found = appointmentService.searchAppointment(searchNumber);
                    if (found != null) {
                        System.out.println(found);
                    } else {
                        System.out.println("ERROR: Appointment number was not found.");
                    }
                    break;

                case "3":
                    System.out.print("Enter Appointment Number: ");
                    String billNumber = scanner.nextLine();
                    Appointment appt = appointmentService.searchAppointment(billNumber);
                    if (appt == null) {
                        System.out.println("ERROR: Appointment number was not found.");
                    } else {
                        Bill bill = billingService.generateBill(billNumber, appt.getTreatmentType());
                        System.out.println(bill);
                    }
                    break;

                case "4":
                    System.out.println("\n----- HELP -----");
                    System.out.println("1. Register New Appointment: enter patient and appointment details.");
                    System.out.println("2. Display Appointment Details: search using the appointment number.");
                    System.out.println("3. Calculate and Print Bill: enter the appointment number to generate a receipt.");
                    System.out.println("4. Help: shows this message.");
                    System.out.println("5. Exit: safely closes the application.");
                    break;

                case "5":
                    System.out.println("Exiting system. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option. Please select 1-5.");
            }
        }

        scanner.close();
    }
}