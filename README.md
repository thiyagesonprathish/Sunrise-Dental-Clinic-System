# Sunrise Dental Clinic - Appointment & Patient Management System

A console-based Java application for managing patient appointments, billing, and dentist scheduling at Sunrise Dental Clinic, developed for CIS6003 Advanced Programming.

## Features
- Staff login/authentication
- Register new appointments with validation and double-booking prevention
- Search appointments by appointment number
- Calculate and print bills/receipts
- Help section for new staff
- REST web service endpoint for appointment lookup

## Technology Stack
- Java (JDK 25, Eclipse Temurin)
- SQLite (via JDBC) for persistence
- Built-in com.sun.net.httpserver.HttpServer for the REST web service
- JUnit 5 for automated testing

## Project Structure
src/
  model/       - Domain classes (Patient, Appointment, Treatment, Bill, User)
  service/     - Business logic (AppointmentService, BillingService)
  repository/  - Data access layer (AppointmentRepository - SQLite/JDBC)
  app/         - Entry point and web server (Main, WebServer)
  test/        - JUnit 5 test cases
lib/           - Required jar dependencies (JUnit, SQLite JDBC driver, SLF4J)

## Prerequisites
- JDK 21 or later installed and available on PATH

## How to Compile
From the project root, run:
javac -cp "lib/*" -d bin (Get-ChildItem -Recurse -Filter *.java -Path src | ForEach-Object { $_.FullName })

## How to Run
java -cp "bin;lib/*" app.Main

Default staff login credentials:
- Username: staff01 / Password: pass123
- Username: admin / Password: admin123

## How to Run Automated Tests
Open src/test/AppointmentServiceTest.java in VS Code (with the Java Extension Pack installed) and use the "Run Test" CodeLens link above the class, or above each individual test method.

## Web Service
Once the application is running and you have logged in, a REST endpoint is available at:
GET http://localhost:8081/api/appointments/{appointmentNumber}

Example:
curl.exe http://localhost:8081/api/appointments/APT9233

Returns the appointment details as JSON.

## Database
The application uses SQLite (clinic.db), created automatically in the project root on first run. No manual database setup is required.