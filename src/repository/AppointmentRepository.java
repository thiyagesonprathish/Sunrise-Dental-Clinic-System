package repository;

import model.Appointment;
import model.Patient;
import java.sql.*;
import java.util.*;

public class AppointmentRepository {

    private static final String DB_URL = "jdbc:sqlite:clinic.db";

    public AppointmentRepository() {
        createTableIfNotExists();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    // Creates the appointments table the first time the app runs
    private void createTableIfNotExists() {
            try {
        Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
        System.out.println("Driver class not found: " + e.getMessage());
        }
        String sql = "CREATE TABLE IF NOT EXISTS appointments (" +
                "appointment_number TEXT PRIMARY KEY," +
                "patient_name TEXT NOT NULL," +
                "address TEXT NOT NULL," +
                "contact_number TEXT NOT NULL," +
                "dentist_name TEXT NOT NULL," +
                "treatment_type TEXT NOT NULL," +
                "appointment_date TEXT NOT NULL," +
                "appointment_time TEXT NOT NULL" +
                ")";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    // Save one appointment into the database
    public void save(Appointment appointment) {
        String sql = "INSERT INTO appointments (appointment_number, patient_name, address, " +
                "contact_number, dentist_name, treatment_type, appointment_date, appointment_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, appointment.getAppointmentNumber());
            ps.setString(2, appointment.getPatient().getPatientName());
            ps.setString(3, appointment.getPatient().getAddress());
            ps.setString(4, appointment.getPatient().getContactNumber());
            ps.setString(5, appointment.getDentistName());
            ps.setString(6, appointment.getTreatmentType());
            ps.setString(7, appointment.getAppointmentDate());
            ps.setString(8, appointment.getAppointmentTime());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error saving appointment: " + e.getMessage());
        }
    }

    // Load all appointments from the database
    public List<Appointment> findAll() {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Patient patient = new Patient(
                        rs.getString("patient_name"),
                        rs.getString("address"),
                        rs.getString("contact_number"));

                Appointment appt = new Appointment(
                        rs.getString("appointment_number"),
                        patient,
                        rs.getString("dentist_name"),
                        rs.getString("treatment_type"),
                        rs.getString("appointment_date"),
                        rs.getString("appointment_time"));

                appointments.add(appt);
            }

        } catch (SQLException e) {
            System.out.println("Error reading appointments: " + e.getMessage());
        }

        return appointments;
    }

    // Find one appointment by its appointment number
    public Appointment findByAppointmentNumber(String appointmentNumber) {
        String sql = "SELECT * FROM appointments WHERE appointment_number = ?";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, appointmentNumber);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Patient patient = new Patient(
                        rs.getString("patient_name"),
                        rs.getString("address"),
                        rs.getString("contact_number"));

                return new Appointment(
                        rs.getString("appointment_number"),
                        patient,
                        rs.getString("dentist_name"),
                        rs.getString("treatment_type"),
                        rs.getString("appointment_date"),
                        rs.getString("appointment_time"));
            }

        } catch (SQLException e) {
            System.out.println("Error finding appointment: " + e.getMessage());
        }

        return null; // not found
    }

    // Check whether a dentist already has an appointment at this date/time
    public boolean existsConflict(String dentistName, String date, String time) {
        String sql = "SELECT COUNT(*) FROM appointments WHERE dentist_name = ? " +
                "AND appointment_date = ? AND appointment_time = ?";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dentistName);
            ps.setString(2, date);
            ps.setString(3, time);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error checking conflict: " + e.getMessage());
        }

        return false;
    }
}