package model;

public class Patient {
    private String patientName;
    private String address;
    private String contactNumber;

    public Patient(String patientName, String address, String contactNumber) {
        this.patientName = patientName;
        this.address = address;
        this.contactNumber = contactNumber;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getAddress() {
        return address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    @Override
    public String toString() {
        return "Patient Name: " + patientName +
               ", Address: " + address +
               ", Contact: " + contactNumber;
    }
}