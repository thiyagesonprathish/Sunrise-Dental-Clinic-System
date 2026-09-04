package model;

public class Appointment {
    private String appointmentNumber;
    private Patient patient;
    private String dentistName;
    private String treatmentType;
    private String appointmentDate;
    private String appointmentTime;

    public Appointment(String appointmentNumber, Patient patient, String dentistName,
                        String treatmentType, String appointmentDate, String appointmentTime) {
        this.appointmentNumber = appointmentNumber;
        this.patient = patient;
        this.dentistName = dentistName;
        this.treatmentType = treatmentType;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
    }

    public String getAppointmentNumber() {
        return appointmentNumber;
    }

    public Patient getPatient() {
        return patient;
    }

    public String getDentistName() {
        return dentistName;
    }

    public String getTreatmentType() {
        return treatmentType;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    @Override
    public String toString() {
        return "Appointment No: " + appointmentNumber +
               "\n" + patient.toString() +
               "\nDentist: " + dentistName +
               "\nTreatment: " + treatmentType +
               "\nDate/Time: " + appointmentDate + " " + appointmentTime;
    }
}