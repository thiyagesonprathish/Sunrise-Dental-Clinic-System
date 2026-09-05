package app;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import model.Appointment;
import service.AppointmentService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class WebServer {

    private AppointmentService appointmentService;

    public WebServer(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    public void start() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);

        // GET /api/appointments/{appointmentNumber}
        server.createContext("/api/appointments", exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                handleSearch(exchange);
            } else {
                sendResponse(exchange, 405, "{\"error\":\"Method not allowed\"}");
            }
        });

        server.setExecutor(null); // default executor
        server.start();
        System.out.println("Web service started at http://localhost:8081/api/appointments/{appointmentNumber}");
    }

    private void handleSearch(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath(); // e.g. /api/appointments/APT1234
        String[] parts = path.split("/");
        String appointmentNumber = parts.length > 0 ? parts[parts.length - 1] : "";

        Appointment appt = appointmentService.searchAppointment(appointmentNumber);

        if (appt == null) {
            sendResponse(exchange, 404, "{\"error\":\"Appointment not found\"}");
        } else {
            String json = "{"
                    + "\"appointmentNumber\":\"" + appt.getAppointmentNumber() + "\","
                    + "\"patientName\":\"" + appt.getPatient().getPatientName() + "\","
                    + "\"dentistName\":\"" + appt.getDentistName() + "\","
                    + "\"treatmentType\":\"" + appt.getTreatmentType() + "\","
                    + "\"date\":\"" + appt.getAppointmentDate() + "\","
                    + "\"time\":\"" + appt.getAppointmentTime() + "\""
                    + "}";
            sendResponse(exchange, 200, json);
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String body) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, body.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(body.getBytes());
        os.close();
    }
}