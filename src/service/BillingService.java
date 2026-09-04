package service;

import model.Bill;
import model.Treatment;
import java.util.ArrayList;
import java.util.List;

public class BillingService {

    private double consultationFee = 500.0; // fixed consultation fee
    private List<Treatment> treatmentCatalogue;

    public BillingService() {
        // Simple hardcoded treatment catalogue for now
        treatmentCatalogue = new ArrayList<>();
        treatmentCatalogue.add(new Treatment("Consultation", 500.0));
        treatmentCatalogue.add(new Treatment("Tooth Extraction", 3000.0));
        treatmentCatalogue.add(new Treatment("Root Canal", 8000.0));
        treatmentCatalogue.add(new Treatment("Teeth Cleaning", 2000.0));
        treatmentCatalogue.add(new Treatment("Filling", 4000.0));
    }

    // Look up the cost of a treatment type from the catalogue
    private double getTreatmentCost(String treatmentType) {
        for (Treatment t : treatmentCatalogue) {
            if (t.getTreatmentType().equalsIgnoreCase(treatmentType)) {
                return t.getCost();
            }
        }
        return 0.0; // not found - default to 0
    }

    // Generate a Bill for a given appointment number and treatment type
    public Bill generateBill(String appointmentNumber, String treatmentType) {
        double treatmentCost = getTreatmentCost(treatmentType);
        return new Bill(appointmentNumber, treatmentCost, consultationFee);
    }

    public List<Treatment> getTreatmentCatalogue() {
        return treatmentCatalogue;
    }
}