package model;

public class Bill {
    private String appointmentNumber;
    private double treatmentCost;
    private double consultationFee;
    private double total;

    public Bill(String appointmentNumber, double treatmentCost, double consultationFee) {
        this.appointmentNumber = appointmentNumber;
        this.treatmentCost = treatmentCost;
        this.consultationFee = consultationFee;
        this.total = treatmentCost + consultationFee;
    }

    public String getAppointmentNumber() {
        return appointmentNumber;
    }

    public double getTreatmentCost() {
        return treatmentCost;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public double getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "----- RECEIPT -----\n" +
               "Appointment No: " + appointmentNumber + "\n" +
               "Treatment Cost: Rs. " + treatmentCost + "\n" +
               "Consultation Fee: Rs. " + consultationFee + "\n" +
               "TOTAL: Rs. " + total + "\n" +
               "--------------------";
    }
}