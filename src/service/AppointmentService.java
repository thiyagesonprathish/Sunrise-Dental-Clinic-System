package service;

import model.Appointment;
import model.Patient;
import repository.AppointmentRepository;
import java.util.List;
import java.util.Random;

public class AppointmentService {

    private AppointmentRepository repository;

    public AppointmentService(AppointmentRepository repository) {
        this.repository = repository;
    }

    // Generates a simple unique appointment number, e.g. APT1023
    private String generateAppointmentNumber() {
        Random random = new Random();
        int number = 1000 + random.nextInt(9000); // random 4-digit number
        return "APT" + number;
    }

    // Registers a new appointment after validation and conflict check
    public String registerAppointment(String patientName, String address, String contactNumber,
                                       String dentistName, String treatmentType,
                                       String date, String time) {

        // Basic validation
        if (patientName == null || patientName.trim().isEmpty()) {
            return "ERROR: Patient name is required.";
        }
        if (address == null || address.trim().isEmpty()) {
            return "ERROR: Address is required.";
        }
        if (contactNumber == null || !contactNumber.matches("\\d{9,10}")) {
            return "ERROR: Contact number must be 9-10 digits.";
        }
        if (dentistName == null || dentistName.trim().isEmpty()) {
            return "ERROR: Dentist name is required.";
        }
        if (treatmentType == null || treatmentType.trim().isEmpty()) {
            return "ERROR: Treatment type is required.";
        }

        // Conflict check (FR4 - prevent double booking)
        if (repository.existsConflict(dentistName, date, time)) {
            return "ERROR: This dentist already has an appointment at that date/time.";
        }

        // All checks passed - create and save
        String appointmentNumber = generateAppointmentNumber();
        Patient patient = new Patient(patientName, address, contactNumber);
        Appointment appointment = new Appointment(appointmentNumber, patient, dentistName,
                                                   treatmentType, date, time);
        repository.save(appointment);

        return "SUCCESS: Appointment registered with number " + appointmentNumber;
    }

    // Search for an appointment
    public Appointment searchAppointment(String appointmentNumber) {
        return repository.findByAppointmentNumber(appointmentNumber);
    }

    // List all appointments (useful for a "view all" report)
    public List<Appointment> getAllAppointments() {
        return repository.findAll();
    }
}