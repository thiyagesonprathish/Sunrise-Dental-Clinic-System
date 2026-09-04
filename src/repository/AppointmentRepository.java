package repository;

import model.Appointment;
import model.Patient;
import java.io.*;
import java.util.*;

public class AppointmentRepository {

    private static final String FILE_NAME = "appointments.txt";

    // Save one appointment by appending it to the file
    public void save(Appointment appointment) {
        try (FileWriter fw = new FileWriter(FILE_NAME, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {

            // Store fields separated by "|" so we can split them back later
            String line = appointment.getAppointmentNumber() + "|" +
                          appointment.getPatient().getPatientName() + "|" +
                          appointment.getPatient().getAddress() + "|" +
                          appointment.getPatient().getContactNumber() + "|" +
                          appointment.getDentistName() + "|" +
                          appointment.getTreatmentType() + "|" +
                          appointment.getAppointmentDate() + "|" +
                          appointment.getAppointmentTime();

            pw.println(line);

        } catch (IOException e) {
            System.out.println("Error saving appointment: " + e.getMessage());
        }
    }

    // Load all appointments from the file into a list
    public List<Appointment> findAll() {
        List<Appointment> appointments = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return appointments; // empty list if no file yet
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 8) {
                    Patient patient = new Patient(parts[1], parts[2], parts[3]);
                    Appointment appt = new Appointment(parts[0], patient, parts[4], parts[5], parts[6], parts[7]);
                    appointments.add(appt);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading appointments: " + e.getMessage());
        }

        return appointments;
    }

    // Find one appointment by its appointment number
    public Appointment findByAppointmentNumber(String appointmentNumber) {
        for (Appointment a : findAll()) {
            if (a.getAppointmentNumber().equals(appointmentNumber)) {
                return a;
            }
        }
        return null; // not found
    }

    // Check whether a dentist already has an appointment at this date/time
    public boolean existsConflict(String dentistName, String date, String time) {
        for (Appointment a : findAll()) {
            if (a.getDentistName().equalsIgnoreCase(dentistName) &&
                a.getAppointmentDate().equals(date) &&
                a.getAppointmentTime().equals(time)) {
                return true;
            }
        }
        return false;
    }
}