package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import service.AppointmentService;
import repository.AppointmentRepository;

public class AppointmentServiceTest {

    @Test
    public void testRegisterAppointment_validData_returnsSuccess() {
        AppointmentRepository repo = new AppointmentRepository();
        AppointmentService service = new AppointmentService(repo);

        // Use a unique time each run so repeated test executions don't conflict
        String uniqueTime = "09:" + (System.currentTimeMillis() % 60);

        String result = service.registerAppointment(
                "Nimal Fernando", "12 Kandy Road", "0712223344",
                "Dr. Perera", "Filling", "2026-12-01", uniqueTime);

        assertTrue(result.startsWith("SUCCESS"));
    }

    @Test
    public void testRegisterAppointment_missingName_returnsError() {
        AppointmentRepository repo = new AppointmentRepository();
        AppointmentService service = new AppointmentService(repo);

        String result = service.registerAppointment(
                "", "12 Kandy Road", "0712223344",
                "Dr. Perera", "Filling", "2026-12-01", "09:30");

        assertEquals("ERROR: Patient name is required.", result);
    }

    @Test
    public void testRegisterAppointment_invalidContact_returnsError() {
        AppointmentRepository repo = new AppointmentRepository();
        AppointmentService service = new AppointmentService(repo);

        String result = service.registerAppointment(
                "Sunil Silva", "5 Main Street", "abc123",
                "Dr. Perera", "Filling", "2026-12-01", "10:00");

        assertEquals("ERROR: Contact number must be 9-10 digits.", result);
    }
}